package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.App;
import com.vitorpg.clothingstore.dtos.ProductFilter;
import com.vitorpg.clothingstore.dtos.SaleFilter;
import com.vitorpg.clothingstore.events.ChangeSubSceneEvent;
import com.vitorpg.clothingstore.events.RefreshPageEvent;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.ProductStatus;
import com.vitorpg.clothingstore.services.*;
import com.vitorpg.clothingstore.utils.ImageUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;


public class SaleListController {
    private ProductService productService = new ProductService();
    private SaleService saleService = new SaleService();

    private Long pageMaxCount = 10L;
    private Long pageOffset = 0L;
    private int saleTotalCount;
    private int pageCount;

    @FXML
    private Button btnRegisterSale;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<Sale> tbSaleList;

    @FXML
    private TableColumn<Sale, LocalDate> colDate;

    @FXML
    private TableColumn<Sale, String> colProductName;

    @FXML
    private TableColumn<Sale, Integer> colAmount;

    @FXML
    private TableColumn<Sale, Double> colUnitValue;

    @FXML
    private TableColumn<Sale, Double> colTotalValue;

    @FXML
    private TableColumn<Sale, Void> colSaleActions;

    @FXML
    private DatePicker dtBefore;

    @FXML
    private DatePicker dtAfter;

    @FXML
    private Spinner<Integer> spnAmount;

    @FXML
    private Pagination pagSaleList;

    @FXML
    private FlowPane flowFilters;

    @FXML
    private ChoiceBox<String> cbAmount;

    private SaleFilter saleFilter = new SaleFilter();

    private ObservableList<Sale> saleObservableList;

    @FXML
    public void initialize () {
        configureFiltersData();
        configureFiltersEvents();
        configureTableColumns();

        tbSaleList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);
        flowFilters.getChildren().stream().filter(x -> x instanceof VBox)
            .forEach(x -> ((VBox) x).prefWidthProperty().bind(Bindings.createDoubleBinding(() -> {
                int maxColumns = Math.max((int) ((flowFilters.getWidth()) / (200.0 + flowFilters.getHgap())), 1);
                var prefWidth = ((flowFilters.getWidth()) / maxColumns) - flowFilters.getHgap();
                return prefWidth;
            }, flowFilters.widthProperty())));
        setBasicActions ();
        refreshAll();
        pagSaleList.setPageFactory(pageNumber -> {
            pageOffset = ((long) pageNumber) * pageMaxCount;
            loadProducts();
            return new VBox();
        });

    }

    private void refreshAll () {
        refreshFilter();
        refreshPagination();
        loadProducts();
    }

    private void refreshPagination () {
        saleTotalCount = (int) (long) saleService.getTotalCountFiltered(saleFilter);
        pageCount = (int) Math.ceil((double) saleTotalCount / pageMaxCount);
        pagSaleList.setPageCount(pageCount);
        pagSaleList.setCurrentPageIndex((int) (long) pageOffset);
    }

    private void refreshFilter () {
        if(txtName.getText().isEmpty() && txtName.getText().isBlank())
            saleFilter.setProductName(Optional.ofNullable(null));
        else
            saleFilter.setProductName(Optional.ofNullable(txtName.getText().trim()));

        saleFilter.setBeforeDateTime(
                Optional.ofNullable(dtBefore.getValue())
                        .map(date -> date.atTime(LocalTime.MAX))
        );

        saleFilter.setAfterDateTime(
                Optional.ofNullable(dtAfter.getValue())
                        .map(date -> date.atTime(LocalTime.MIN))
        );

        saleFilter.setAmountComparator(
                Optional.ofNullable(cbAmount.getSelectionModel().getSelectedItem())
        );

        saleFilter.setAmount(
                Optional.ofNullable(spnAmount.getValue())
                        .map(val -> (long) val)
        );
    }

    public void loadProducts () {
        saleObservableList = FXCollections
                .observableArrayList(saleService.findPaginatedFiltered(pageMaxCount, pageOffset, saleFilter));

        tbSaleList.setItems(saleObservableList);
        tbSaleList.setFixedCellSize(76);
        tbSaleList.prefHeightProperty().bind(tbSaleList.fixedCellSizeProperty()
                .multiply(Math.min(saleObservableList.size() + 5, pageMaxCount + 1)));
        tbSaleList.setMinHeight(0);
    }

    private void configureFiltersEvents() {
        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                refreshAll();
            }
        });
        cbAmount.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshAll();
        });
        spnAmount.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (saleFilter.getAmountComparator().isPresent())
                refreshAll();
        });
        dtBefore.valueProperty().addListener((obs, oldValue, newValue) -> {
            refreshAll();
        });
        dtAfter.valueProperty().addListener((obs, oldValue, newValue) -> {
            refreshAll();
        });
    }

    private void configureFiltersData() {
        cbAmount.setItems(FXCollections.observableArrayList(List.of(">=", ">", "=", "<", "<=")));
        cbAmount.getItems().add(0, null);

        spnAmount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        spnAmount.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*"))
                return change;
            return null;
        }));
    }

    private void configureTableColumns() {
        colDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateTime().toLocalDate()));
        colAmount.setCellValueFactory(new PropertyValueFactory<Sale, Integer>("amount"));
        colProductName.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(productService.findById(cellData.getValue().getProduct().getId()).getName()));
        colUnitValue.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(productService.findById(cellData.getValue().getProduct().getId()).getPrice()));
        colTotalValue.setCellValueFactory(new PropertyValueFactory<Sale, Double>("totalPrice"));
    }

    private void setBasicActions () {
        colSaleActions.setCellFactory(new Callback<TableColumn<Sale, Void>, TableCell<Sale, Void>>() {
            @Override
            public TableCell<Sale, Void> call (TableColumn<Sale, Void> param) {
                return new TableCell<Sale, Void>() {
                    private final Button btn_Alter = new Button("Alterar");
                    private final Button btn_Remove = new Button("Remover");


                    {
                        btn_Alter.setOnAction(event -> {
                            Sale sale = getTableView().getItems().get(getIndex());
                            showAlterSaleView(event, sale);
                        });

                        btn_Remove.setOnAction(event -> {
                            Sale sale = getTableView().getItems().get(getIndex());
                            showAlert(Alert.AlertType.CONFIRMATION, "Remover Venda",
                                    "Você tem certeza de que deseja remover esta venda?",
                                event1 -> {
                                    if(event1 == ButtonType.OK) {
                                        saleService.delete(sale.getId());
                                        refreshAll();
                                    }
                                });
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            btn_Alter.setTextAlignment(TextAlignment.CENTER);
                            btn_Alter.setPrefWidth(166.0);
                            btn_Alter.getStyleClass().addAll("b-primary", "rounded-left-28", "btn-group-outline",
                                    "btn-group-outline-start", "fs-24", "khand-medium-font");

                            btn_Remove.setTextAlignment(TextAlignment.CENTER);
                            btn_Remove.setPrefWidth(166.0);
                            btn_Remove.getStyleClass().addAll("b-primary", "rounded-right-28", "btn-group-outline",
                                    "btn-group-outline-end", "fs-24", "khand-medium-font");

                            HBox hBox = new HBox(0);
                            hBox.setAlignment(Pos.CENTER);
                            hBox.getChildren().addAll(btn_Alter, btn_Remove);

                            setGraphic(hBox);
                        }
                    }
                };
            }
        });
    }

    private void showAlert (Alert.AlertType alertType, String headerText, String contentText, Consumer<ButtonType> alertEvent) {
        Alert alert = new Alert(alertType);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait().ifPresent(alertEvent);
    }

    public void showRegisterSaleView(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("register-sale-view.fxml"));
            loader.setController(new RegisterSaleController() {{ setParentScene(btnRegisterSale.getScene());}});
            Scene scene = new Scene(loader.load(), 726, 365);
            cbAmount.getScene().addEventHandler(RefreshPageEvent.REFRESH_PAGE_REQUESTED, event1 -> {
                refreshAll();
            });
            stage.setTitle("ClothingStore - Cadastrar Venda");
            stage.setScene(scene);
            stage.setMaxWidth(726);
            stage.setMinWidth(726);
            stage.setMinHeight(365);
            stage.setMaxHeight(365);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void showAlterSaleView(ActionEvent event, Sale sale) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("alter-sale-view.fxml"));
            loader.setController(new AlterSaleController() {{
                setParentScene(btnRegisterSale.getScene());
                setSale(sale);
            }});
            Scene scene = new Scene(loader.load(), 726, 365);
            cbAmount.getScene().addEventHandler(RefreshPageEvent.REFRESH_PAGE_REQUESTED, event1 -> {
                refreshAll();
            });
            stage.setTitle("ClothingStore - Cadastrar Venda");
            stage.setScene(scene);
            stage.setMaxWidth(726);
            stage.setMinWidth(726);
            stage.setMinHeight(365);
            stage.setMaxHeight(365);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}