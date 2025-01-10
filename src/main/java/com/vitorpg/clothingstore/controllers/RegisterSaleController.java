package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.dtos.ProductFilter;
import com.vitorpg.clothingstore.events.RefreshPageEvent;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.services.ProductService;
import com.vitorpg.clothingstore.services.SaleService;
import com.vitorpg.clothingstore.services.SessionManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class RegisterSaleController {
    private ProductService productService = new ProductService();
    private SaleService saleService = new SaleService();

    @FXML
    private ComboBox<String> cmbProductName;

    @FXML
    private Spinner<Integer> spnAmount;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtTotalPrice;

    @FXML
    private Button btnRegisterSale;

    private Product selectedProduct;

    private List<Product> products;

    private Scene parentScene;

    public void setParentScene(Scene scene) {
        this.parentScene = scene;
    }

    @FXML
    public void initialize () {
        spnAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        spnAmount.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*"))
                return change;
            return null;
        }));
        spnAmount.valueProperty().addListener((obs, oldValue, newValue) -> {
            updateTotalPrice();
        });

        cmbProductName.getEditor().setOnKeyTyped(event -> {
            cmbProductName.getSelectionModel().clearSelection();
            if(event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.BACK_SPACE) {
                return;
            }

            var productName = cmbProductName.getEditor().getText();

            products = productService.findPaginatedFiltered(10L, 0L,
                    new ProductFilter() {{ setName(Optional.ofNullable(productName));}});

            cmbProductName.setItems(FXCollections.observableArrayList(products.stream().map(x -> x.getName()).toList()));

            cmbProductName.show();
        });

        cmbProductName.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue != null && !newValue.isBlank() && !newValue.isEmpty()) {
                selectedProduct = products.stream().filter(x -> x.getName().equals(newValue)).findFirst().get();
                System.out.println(selectedProduct.getName());

                spnAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, (int) (long) selectedProduct.getAmount(), 0, 1));
                txtUnitPrice.setText(String.format(Locale.US, "%.2f", selectedProduct.getPrice()));
                updateTotalPrice();
            } else {
                spnAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
            }
        });

    }

    private void updateTotalPrice () {
        try {
            var unitPrice = Double.parseDouble(txtUnitPrice.getText());
            var amount = spnAmount.getValue();

            txtTotalPrice.setText(String.format(Locale.US, "%.2f", unitPrice * amount));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void registerSale(ActionEvent event) {
        var amount = (long) spnAmount.getValue();
        if(amount <= 0)
            return;

        AtomicBoolean hasToContinue = new AtomicBoolean(false);
        showAlert(Alert.AlertType.CONFIRMATION, "Cadastrar Venda", "Você tem certeza de que deseja cadastrar essa venda?",
            x -> {
                if(x == ButtonType.OK) {
                    hasToContinue.set(true);
                }
            });

        if(!hasToContinue.get())
            return;
        Sale sale = new Sale();
        sale.setAmount(amount);
        sale.setTotalPrice(Double.parseDouble(txtTotalPrice.getText()));
        sale.setDateTime(LocalDateTime.now());
        sale.setVendor(SessionManager.getInstance().getUser());
        sale.setProduct(selectedProduct);

        saleService.save(sale);
        productService.adjustAmount(selectedProduct.getId(), -sale.getAmount());
        Event.fireEvent(parentScene, new RefreshPageEvent(RefreshPageEvent.REFRESH_PAGE_REQUESTED));
        Stage stage = (Stage) cmbProductName.getScene().getWindow();
        stage.close();
    }

    private void showAlert (Alert.AlertType alertType, String headerText, String contentText, Consumer<ButtonType> alertEvent) {
        Alert alert = new Alert(alertType);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait().ifPresent(alertEvent);
    }
}
