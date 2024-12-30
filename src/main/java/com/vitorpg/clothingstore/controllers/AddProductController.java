package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;
import com.vitorpg.clothingstore.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;


public class AddProductController {

    private ProductService productService = new ProductService();

    private CategoryService categoryService = new CategoryService();

    private SizeService sizeService = new SizeService();

    private ColorService colorService = new ColorService();

    private MaterialService materialService = new MaterialService();

    private StyleService styleService = new StyleService();

    private SupplierService supplierService = new SupplierService();



    @FXML
    private TextField txtProductName;

    @FXML
    private ComboBox<Category> cmbCategory;

    @FXML
    private Button btnAddCategory;

    @FXML
    private ComboBox<Material> cmbMaterial;

    @FXML
    private Button btnAddMaterial;

    @FXML
    private ComboBox<Style> cmbStyle;

    @FXML
    private Button btnAddStyle;

    @FXML
    private ComboBox<Color> cmbColor;

    @FXML
    private Button btnAddColor;

    @FXML
    private HBox hboxSizes;

    @FXML
    private HBox hboxGenders;

    @FXML
    private TextField txtProductCost;

    @FXML
    private TextField txtProductPrice;

    @FXML
    private Spinner<Integer> spnAmountInStock;

    @FXML
    private ComboBox<Supplier> cmbSupplier;

    @FXML
    private Button btnAddSupplier;

    @FXML
    private DatePicker dtSupplyDate;

    @FXML
    public RadioButton radGenderMale;

    @FXML
    public RadioButton radGenderFemale;

    @FXML
    public RadioButton radGenderUnissex;

    private ObservableList<Category> categoryObservableList;
    private ObservableList<Material> materialObservableList;
    private ObservableList<Style> styleObservableList;
    private ObservableList<Color> colorObservableList;
    private ObservableList<Supplier> supplierObservableList;

    @FXML
    public void initialize () {
        categoryObservableList = FXCollections
                .observableArrayList(categoryService.findAll());
        materialObservableList = FXCollections
                .observableArrayList(materialService.findAll());
        colorObservableList = FXCollections
                .observableArrayList(colorService.findAll());
        styleObservableList = FXCollections
                .observableArrayList(styleService.findAll());
        supplierObservableList = FXCollections
                .observableArrayList(supplierService.findAll());

        List<Size> sizes = sizeService.findAllByCategory(1L);
        ToggleGroup sizeToggleGroup = new ToggleGroup();
        for (Size size : sizes) {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setText(size.getValue());
            toggleButton.setMinWidth(52.0);
            toggleButton.setPrefWidth(52.0);
            toggleButton.setMaxWidth(52.0);
            toggleButton.setMinHeight(52.0);
            toggleButton.setPrefHeight(52.0);
            toggleButton.setMaxHeight(52.0);

            toggleButton.getStyleClass().addAll("toggle-btn-primary", "bg-rounded-8", "khand-font", "txt-white", "fs-24");
            toggleButton.setToggleGroup(sizeToggleGroup);
//            toggleButton.setOnAction(event -> sizeToggleGroup.selectToggle(toggleButton));
            hboxSizes.getChildren().add(toggleButton);
        }




        cmbMaterial.setItems(materialObservableList);
        cmbCategory.setItems(categoryObservableList);
        cmbStyle.setItems(styleObservableList);
        cmbColor.setItems(colorObservableList);
        cmbSupplier.setItems(supplierObservableList);

        cmbCategory.setOnAction(event -> {
            sizes.clear();
            sizes.addAll(sizeService.findAllByCategory(cmbCategory.getValue().getId()));
        });

        ToggleGroup genderRadioToggleGroup = new ToggleGroup();
        radGenderMale.setToggleGroup(genderRadioToggleGroup);
        radGenderFemale.setToggleGroup(genderRadioToggleGroup);
        radGenderUnissex.setToggleGroup(genderRadioToggleGroup);

        spnAmountInStock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 0, 1));
        spnAmountInStock.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*"))
                return change;
            return null;
        }));


        btnAddCategory.setOnAction(event -> {
            loadComboBoxMessageBox(cmbCategory, (Void) -> {
                Category category = new Category();
                category.setName(cmbCategory.getEditor().getText());
                categoryService.save(category);
                categoryObservableList = FXCollections
                        .observableArrayList(categoryService.findAll());
                cmbCategory.setItems(this.categoryObservableList);
            }, "Categoria");
        });

        btnAddColor.setOnAction(event -> {
            loadComboBoxMessageBox(cmbColor, (Void) -> {
                Color color = new Color();
                color.setName(cmbColor.getEditor().getText());
                colorService.save(color);
                colorObservableList = FXCollections
                        .observableArrayList(colorService.findAll());
                cmbColor.setItems(this.colorObservableList);
            }, "Cor");
        });

        btnAddStyle.setOnAction(event -> {
            loadComboBoxMessageBox(cmbStyle, (Void) -> {
                Style style = new Style();
                style.setName(cmbStyle.getEditor().getText());
                styleService.save(style);
                styleObservableList = FXCollections
                        .observableArrayList(styleService.findAll());
                cmbStyle.setItems(this.styleObservableList);
            }, "Estilo");
        });

        btnAddMaterial.setOnAction(event -> {
            loadComboBoxMessageBox(cmbMaterial, (Void) -> {
                Material material = new Material();
                material.setName(cmbMaterial.getEditor().getText());
                materialService.save(material);
                materialObservableList = FXCollections
                        .observableArrayList(materialService.findAll());
                cmbMaterial.setItems(this.materialObservableList);
            }, "Material");
        });

        btnAddSupplier.setOnAction(event -> {
            loadComboBoxMessageBox(cmbSupplier, (Void) -> {
                Supplier supplier = new Supplier();
                supplier.setName(cmbSupplier.getEditor().getText());
                supplierService.save(supplier);
                supplierObservableList = FXCollections
                        .observableArrayList(supplierService.findAll());
                cmbSupplier.setItems(this.supplierObservableList);
            }, "Fornecedor(a)");
        });
    }

    private <T, U extends Dao<T>> void loadComboBoxMessageBox (ComboBox<T> comboBox, Consumer<Void> confirmAction, String entityName) {
        if(comboBox.getItems().stream().anyMatch(x -> x.toString().equals(comboBox.getEditor().getText()))) {
            return;
        }

        String name = comboBox.getEditor().getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Caixa de Mensagem");
        alert.setHeaderText("Salvar " + entityName);
        alert.setContentText("Você tem certeza de que deseja salvar o(a) " + entityName.toLowerCase() + " \"" + name + "\"?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                confirmAction.accept(null);
            }
        });

    }


}
