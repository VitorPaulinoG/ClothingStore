package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.events.ChangeSubSceneEvent;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;
import com.vitorpg.clothingstore.services.*;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;


public class AlterProductController {

    public Product product;
    private Supply supply;

    private ProductService productService = new ProductService();

    private CategoryService categoryService = new CategoryService();

    private SizeService sizeService = new SizeService();

    private ColorService colorService = new ColorService();

    private MaterialService materialService = new MaterialService();

    private StyleService styleService = new StyleService();

    private SupplierService supplierService = new SupplierService();

    private SupplyService supplyService = new SupplyService();

    private ImageService imageService = new ImageService();

    private int imageListCount = 0;

    private List<Long> removedImages = new ArrayList<>();
    private List<Image> addedImages = new ArrayList<>();

    private StackPane spSelectedImage;

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
    private ToggleGroup genderRadioToggleGroup;

    @FXML
    private RadioButton radGenderMale;

    @FXML
    private RadioButton radGenderFemale;

    @FXML
    private RadioButton radGenderUnisex;

    @FXML
    private FlowPane flowPriceAndStock;

    @FXML
    private FlowPane flowProductProps;

    @FXML
    private StackPane spAddImage;

    @FXML
    private FlowPane flowImageList;

    @FXML
    private StackPane spImageDisplay;

    @FXML
    private ImageView imgImageDisplay;

    @FXML
    private Button btnAlterProduct;

    private ObservableList<Category> categoryObservableList;
    private ObservableList<Material> materialObservableList;
    private ObservableList<Style> styleObservableList;
    private ObservableList<Color> colorObservableList;
    private ObservableList<Supplier> supplierObservableList;
    private ToggleGroup sizeToggleGroup;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @FXML
    public void initialize () {
        flowPriceAndStock.getChildren().stream().filter(x -> x instanceof VBox)
            .forEach(x -> ((VBox) x).prefWidthProperty().bind(Bindings.createDoubleBinding(() -> {
                int maxColumns = Math.max((int) ((flowPriceAndStock.getWidth()) / (250.0 + flowPriceAndStock.getHgap())), 1);
                var prefWidth = ((flowPriceAndStock.getWidth()) / maxColumns) - flowPriceAndStock.getHgap();
                return prefWidth;
            }, flowPriceAndStock.widthProperty())));

        flowProductProps.getChildren().stream().filter(x -> x instanceof VBox)
            .forEach(x -> ((VBox) x).prefWidthProperty().bind(Bindings.createDoubleBinding(() -> {
                int maxColumns = Math.max((int) ((flowProductProps.getWidth()) / (250.0 + flowProductProps.getHgap())), 1);
                var prefWidth = ((flowProductProps.getWidth()) / maxColumns) - flowProductProps.getHgap();
                return prefWidth;
            }, flowProductProps.widthProperty())));

        configureFields();
        configureImageList();
    }

    private void configureFields() {
        configureComboBoxes();
        configureRadioButtons();

        txtProductPrice.setText(String.format(Locale.US, "%.2f", product.getPrice()));
        txtProductPrice.end();
        txtProductPrice.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isContentChange() && change.getText().matches("\\d*|\\.")) {
                if(txtProductPrice.getText().contains(".") && change.getText().contains("."))
                    return null;
                return change;
            }
            return null;
        }));

        txtProductCost.setText(String.format(Locale.US, "%.2f", supply.getPrice()));
        txtProductCost.end();
        txtProductCost.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isContentChange() && change.getText().matches("\\d*|\\.")) {
                if(txtProductCost.getText().contains(".") && change.getText().contains("."))
                    return null;
                return change;
            }
            return null;
        }));

        spnAmountInStock.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, (int) (long) product.getAmount(), 1));
        spnAmountInStock.getEditor().setTextFormatter(new TextFormatter<>(change -> {
            if (change.getText().matches("\\d*"))
                return change;
            return null;
        }));

        cmbCategory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                hboxSizes.getChildren().clear();
                loadSizeBottons(newValue.getSizeType());
            }
        });
        cmbCategory.setOnAction(event -> {
            Category selected = cmbCategory.getSelectionModel().getSelectedItem();
            loadSizeBottons(selected.getSizeType());
        });

    }

    private void configureComboBoxes() {
        configureComboBoxesData();

        cmbCategory.setConverter(new StringConverter<Category>() {
            @Override
            public String toString(Category category) {
                return category == null ? "" : category.getName();
            }

            @Override
            public Category fromString(String string) {
                return cmbCategory.getItems().stream()
                        .filter(category -> category.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cmbColor.setConverter(new StringConverter<Color>() {
            @Override
            public String toString(Color color) {
                return color == null ? "" : color.getName();
            }

            @Override
            public Color fromString(String string) {
                return cmbColor.getItems().stream()
                        .filter(color -> color.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cmbStyle.setConverter(new StringConverter<Style>() {
            @Override
            public String toString(Style style) {
                return style == null ? "" : style.getName();
            }

            @Override
            public Style fromString(String string) {
                return cmbStyle.getItems().stream()
                        .filter(style -> style.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cmbMaterial.setConverter(new StringConverter<Material>() {
            @Override
            public String toString(Material material) {
                return material == null ? "" : material.getName();
            }

            @Override
            public Material fromString(String string) {
                return cmbMaterial.getItems().stream()
                        .filter(material -> material.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        cmbSupplier.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                return supplier == null ? "" : supplier.getName();
            }

            @Override
            public Supplier fromString(String string) {
                return cmbSupplier.getItems().stream()
                        .filter(supplier -> supplier.getName().equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        configureComboBoxesButtons();
    }

    private void configureComboBoxesData() {
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

        supply = supplyService.findByProductId(product.getId());

        cmbMaterial.setItems(materialObservableList);
        cmbMaterial.setValue(cmbMaterial.getItems().stream().filter(x -> x.getId() == product.getMaterial().getId()).findFirst().get());
        cmbCategory.setItems(categoryObservableList);
        cmbCategory.setValue(cmbCategory.getItems().stream().filter(x -> x.getId() == product.getCategory().getId()).findFirst().get());
        loadSizeBottons(cmbCategory.getValue().getSizeType());
        sizeToggleGroup.selectToggle(sizeToggleGroup.getToggles().stream()
                .filter(x -> ((Size)x.getUserData()).getId() == product.getSize().getId()).findFirst().get());
        cmbStyle.setItems(styleObservableList);
        cmbStyle.setValue(cmbStyle.getItems().stream().filter(x -> x.getId() == product.getStyle().getId()).findFirst().get());
        cmbColor.setItems(colorObservableList);
        cmbColor.setValue(cmbColor.getItems().stream().filter(x -> x.getId() == product.getColor().getId()).findFirst().get());
        cmbSupplier.setItems(supplierObservableList);
        cmbSupplier.setValue(cmbSupplier.getItems().stream().filter(x -> x.getId() == supply.getSupplier().getId()).findFirst().get());
        dtSupplyDate.setValue(supply.getDate());
    }

    private void configureComboBoxesButtons () {
        btnAddCategory.setOnAction(event -> {
            confirmAddCategory();
        });
        btnAddColor.setOnAction(event -> {
            loadMessageBox(cmbColor, (Void) -> {
                Color color = new Color();
                color.setName(cmbColor.getEditor().getText());
                colorService.save(color);
                colorObservableList = FXCollections
                        .observableArrayList(colorService.findAll());
                cmbColor.setItems(this.colorObservableList);
            }, "Cor");
        });
        btnAddStyle.setOnAction(event -> {
            loadMessageBox(cmbStyle, (Void) -> {
                Style style = new Style();
                style.setName(cmbStyle.getEditor().getText());
                styleService.save(style);
                styleObservableList = FXCollections
                        .observableArrayList(styleService.findAll());
                cmbStyle.setItems(this.styleObservableList);
            }, "Estilo");
        });
        btnAddMaterial.setOnAction(event -> {
            loadMessageBox(cmbMaterial, (Void) -> {
                Material material = new Material();
                material.setName(cmbMaterial.getEditor().getText());
                materialService.save(material);
                materialObservableList = FXCollections
                        .observableArrayList(materialService.findAll());
                cmbMaterial.setItems(this.materialObservableList);
            }, "Material");
        });
        btnAddSupplier.setOnAction(event -> {
            loadMessageBox(cmbSupplier, (Void) -> {
                Supplier supplier = new Supplier();
                supplier.setName(cmbSupplier.getEditor().getText());
                supplierService.save(supplier);
                supplierObservableList = FXCollections
                        .observableArrayList(supplierService.findAll());
                cmbSupplier.setItems(this.supplierObservableList);
            }, "Fornecedor(a)");
        });
    }

    private void configureRadioButtons () {
        genderRadioToggleGroup = new ToggleGroup();
        radGenderMale.setToggleGroup(genderRadioToggleGroup);
        radGenderMale.setUserData(Gender.MALE);
        radGenderFemale.setToggleGroup(genderRadioToggleGroup);
        radGenderFemale.setUserData(Gender.FEMALE);
        radGenderUnisex.setToggleGroup(genderRadioToggleGroup);
        radGenderUnisex.setUserData(Gender.UNISEX);
        txtProductName.setText(product.getName());

        genderRadioToggleGroup.selectToggle(genderRadioToggleGroup.getToggles().stream()
                .filter(x -> ((Gender)x.getUserData()) == product.getGender()).findFirst().get());
    }

    private void configureImageList() {

        spSelectedImage = new StackPane();
        Rectangle imageDisplayClip = new Rectangle(spImageDisplay.getPrefWidth(), spImageDisplay.getPrefHeight());
        imageDisplayClip.setArcWidth(12);
        imageDisplayClip.setArcHeight(12);
        imgImageDisplay.setClip(imageDisplayClip);

        EventHandler<MouseEvent> entered = event -> {
            SVGPath svgPath = new SVGPath();
            svgPath.setContent("M7.72656 21.3688C7.17656 21.3688 6.70573 21.1729 6.31406 20.7813C5.9224 20.3896 5.72656 19.9188 5.72656 19.3688V6.36877H4.72656V4.36877H9.72656V3.36877H15.7266V4.36877H20.7266V6.36877H19.7266V19.3688C19.7266 19.9188 19.5307 20.3896 19.1391 20.7813C18.7474 21.1729 18.2766 21.3688 17.7266 21.3688H7.72656ZM17.7266 6.36877H7.72656V19.3688H17.7266V6.36877ZM9.72656 17.3688H11.7266V8.36877H9.72656V17.3688ZM13.7266 17.3688H15.7266V8.36877H13.7266V17.3688Z");
            svgPath.getStyleClass().addAll("fill-primary");
            StackPane svgStackPane = new StackPane();
            svgStackPane.setId("trashImageIcon");
            svgStackPane.setAlignment(Pos.CENTER);
            svgStackPane.setMaxWidth(25);
            svgStackPane.setPrefWidth(25);
            svgStackPane.setMaxHeight(25);
            svgStackPane.setPrefHeight(25);

            svgStackPane.getChildren().add(svgPath);

            StackPane stackPane = new StackPane();
            stackPane.setMaxWidth(80);
            stackPane.setPrefWidth(80);
            stackPane.setMaxHeight(80);
            stackPane.setPrefHeight(80);
            stackPane.setAlignment(Pos.BOTTOM_RIGHT);
            stackPane.getChildren().add(svgStackPane);
            svgStackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> {
                Image image = ((Image) spSelectedImage.getUserData());

                if(!(addedImages.stream().anyMatch(x -> x.getId() == image.getId()))) {
                    removedImages.add(image.getId());
                }

                flowImageList.getChildren().remove(spSelectedImage);
                addedImages.removeIf(x -> x.getId() == image.getId());
                imageListCount--;
            });
            spSelectedImage.getChildren().add(stackPane);
        };

        EventHandler<MouseEvent> exited = event -> {
            spSelectedImage.getChildren().removeIf(x -> x instanceof StackPane);
        };

        loadImages(entered, exited);

        spAddImage.setOnMouseClicked(event -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser
                    .ExtensionFilter("Image Files (*.jpg, *.png, *.gif, *.bmp)", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp");
            fileChooser.getExtensionFilters().add(extensionFilter);

            File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile == null)
                return;

            try (InputStream inputStream = new FileInputStream(selectedFile)){
                ImageView imageView = new ImageView();
                imageView.setImage(new javafx.scene.image.Image(inputStream));
                imageView.setFitWidth(90);
                imageView.setFitHeight(90);

                StackPane imageAdded = new StackPane();
                imageAdded.setMaxWidth(80);
                imageAdded.setPrefWidth(80);
                imageAdded.setMinWidth(80);
                imageAdded.setMaxHeight(80);
                imageAdded.setPrefHeight(80);
                imageAdded.setMinHeight(80);
                imageAdded.getStyleClass().addAll("bg-rounded-8", "bg-light");
                imageAdded.setAlignment(Pos.CENTER);
                Image image = new Image() {{
                    setData(Files.readAllBytes(selectedFile.toPath()));
                    setFormat(Files.probeContentType(selectedFile.toPath()).split("/")[1]);
                }};

                imageAdded.setUserData(image);

                addedImages.add(image);

                Rectangle clip = new Rectangle(80 - 4, 80 - 4);
                clip.setArcWidth(12);
                clip.setArcHeight(12);
                clip.setLayoutY(7);
                clip.setLayoutX(7);
                imageView.setClip(clip);

                imageAdded.getChildren().add(imageView);
                imageAdded.setOnMouseClicked(event1 -> {
                    spSelectedImage.getStyleClass().removeAll("b-primary", "b-2", "rounded-8");
                    spSelectedImage.removeEventHandler(MouseEvent.MOUSE_ENTERED, entered);
                    spSelectedImage.removeEventHandler(MouseEvent.MOUSE_EXITED, exited);

                    spSelectedImage = imageAdded;
                    imgImageDisplay.setImage(imageView.getImage());

                    spSelectedImage.getStyleClass().addAll("b-primary", "b-2", "rounded-8");
                    spSelectedImage.addEventHandler(MouseEvent.MOUSE_ENTERED, entered);
                    spSelectedImage.addEventHandler(MouseEvent.MOUSE_EXITED, exited);
                });
                flowImageList.getChildren().add(imageListCount++, imageAdded);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    private void loadImages (EventHandler<MouseEvent> entered, EventHandler<MouseEvent> exited) {
        var images = product.getImages();
        for (var image : images) {
            ImageView imageView = new ImageView();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(image.getData());
            imageView.setImage(new javafx.scene.image.Image(inputStream));
            imageView.setFitWidth(90);
            imageView.setFitHeight(90);

            StackPane imageAdded = new StackPane();
            imageAdded.setMaxWidth(80);
            imageAdded.setPrefWidth(80);
            imageAdded.setMinWidth(80);
            imageAdded.setMaxHeight(80);
            imageAdded.setPrefHeight(80);
            imageAdded.setMinHeight(80);
            imageAdded.getStyleClass().addAll("bg-rounded-8", "bg-light");
            imageAdded.setAlignment(Pos.CENTER);
            imageAdded.setUserData(image);

            Rectangle clip = new Rectangle(80 - 4, 80 - 4);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            clip.setLayoutY(7);
            clip.setLayoutX(7);
            imageView.setClip(clip);

            imageAdded.getChildren().add(imageView);
            imageAdded.setOnMouseClicked(event1 -> {
                spSelectedImage.getStyleClass().removeAll("b-primary", "b-2", "rounded-8");
                spSelectedImage.removeEventHandler(MouseEvent.MOUSE_ENTERED, entered);
                spSelectedImage.removeEventHandler(MouseEvent.MOUSE_EXITED, exited);

                spSelectedImage = imageAdded;
                imgImageDisplay.setImage(imageView.getImage());

                spSelectedImage.getStyleClass().addAll("b-primary", "b-2", "rounded-8");
                spSelectedImage.addEventHandler(MouseEvent.MOUSE_ENTERED, entered);
                spSelectedImage.addEventHandler(MouseEvent.MOUSE_EXITED, exited);
            });
            flowImageList.getChildren().add(imageListCount++, imageAdded);
        }
    }

    private void loadSizeBottons (SizeType sizeType) {
        ArrayList<Size> sizes = new ArrayList<>(sizeService.findAllBySizeType(sizeType));

        sizeToggleGroup = new ToggleGroup();
        for (Size size : sizes) {
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setText(size.getValue());
            toggleButton.setMinWidth(52.0);
            toggleButton.setPrefWidth(52.0);
            toggleButton.setMaxWidth(52.0);
            toggleButton.setMinHeight(52.0);
            toggleButton.setPrefHeight(52.0);
            toggleButton.setMaxHeight(52.0);
            toggleButton.setUserData(size);

            toggleButton.getStyleClass().addAll("toggle-btn-primary", "bg-rounded-8", "khand-font", "txt-white", "fs-18", "p-0");
            toggleButton.setToggleGroup(sizeToggleGroup);
            hboxSizes.getChildren().add(toggleButton);
        }
    }

    private <T, U extends Dao<T>> void loadMessageBox(ComboBox<T> comboBox, Consumer<Void> confirmAction, String entityName) {
        if( comboBox.getEditor().getText().isEmpty() || comboBox.getEditor().getText().isBlank() ||
            comboBox.getItems().stream().anyMatch(x -> x.toString().equals(comboBox.getEditor().getText())))
            return;

        String name = comboBox.getEditor().getText().trim();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alert.setTitle("Caixa de Mensagem");
        alert.setHeaderText("Salvar " + entityName);
        alert.setContentText("Você tem certeza de que deseja salvar o(a) " + entityName.toLowerCase() + " \"" + name + "\"?");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                confirmAction.accept(null);
            }
        });

    }

    private <T, U extends Dao<T>, V> void confirmAddCategory () {
        if(cmbCategory.getEditor().getText().isEmpty() || cmbCategory.getEditor().getText().isBlank() ||
                cmbCategory.getItems().stream().anyMatch(x -> x.toString().equals(cmbCategory.getEditor().getText())))
            return;

        String name = cmbCategory.getEditor().getText().trim();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alertPane.setPrefWidth(400);
        alertPane.setMaxWidth(400);
        alertPane.setMinWidth(400);
        alert.setTitle("Caixa de Mensagem");
        alert.setHeaderText("Salvar Categoria");

        ChoiceBox<SizeType> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(FXCollections.observableArrayList(SizeType.values()));
        choiceBox.setPrefWidth(150);
        choiceBox.setMaxWidth(150);
        choiceBox.setMinWidth(150);
        choiceBox.setPrefHeight(40);
        choiceBox.setMaxHeight(40);
        choiceBox.setMinHeight(40);
        choiceBox.getStyleClass().addAll("fs-18");
        VBox content = new VBox();
        content.getChildren().addAll(
            new Label("Você tem certeza de que deseja salvar a categoria \"" + name + "\"?") {{
                getStyleClass().addAll("fs-18", "txt-gray");
                setWrapText(true);
            }},
                new Label("Tipo de Tamanho"), choiceBox);
        alert.getDialogPane().setContent(content);

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Category category = new Category();
                category.setName(cmbCategory.getEditor().getText());
                category.setSizeType(choiceBox.getValue());
                categoryService.save(category);
                categoryObservableList = FXCollections
                        .observableArrayList(categoryService.findAll());
                cmbCategory.setItems(this.categoryObservableList);
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alert.setTitle("Caixa de Mensagem");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    public void alterProduct(ActionEvent event) {
        try {
            var productName = txtProductName.getText().trim();
            var category = cmbCategory.getSelectionModel().getSelectedItem();
            var material = cmbMaterial.getSelectionModel().getSelectedItem();
            var style = cmbStyle.getSelectionModel().getSelectedItem();
            var color = cmbColor.getSelectionModel().getSelectedItem();
            var size = (Size) sizeToggleGroup.getSelectedToggle().getUserData();
            var gender = (Gender) ((RadioButton) genderRadioToggleGroup.getSelectedToggle()).getUserData();
            var productCost = Double.parseDouble(txtProductCost.getText());
            var productPrice = Double.parseDouble(txtProductPrice.getText());
            var amountInStock = spnAmountInStock.getValue();
            var supplier = cmbSupplier.getSelectionModel().getSelectedItem();
            var supplyDate = dtSupplyDate.getValue();
            //var images = flowImageList.getChildren().stream().map(x -> (Image)x.getUserData()).toList();

            if(productName.isBlank() || productName.isEmpty() || category == null || material == null || style == null ||
                color == null || size == null || gender == null || productCost < 0 || productPrice < 0 || supplier == null ||
                supplyDate == null) {
                showAlert(Alert.AlertType.WARNING, "Campos Ausentes", "Preencha todos os campos antes!");
                return;
            }

            if(!product.getName().equals(productName))
                product.setName(productName);
            if(product.getCategory().getId() != category.getId())
                product.setCategory(category);
            if(product.getMaterial().getId() != material.getId())
                product.setMaterial(material);
            if(product.getStyle().getId() != style.getId())
                product.setStyle(style);
            if(product.getColor().getId() != color.getId())
                product.setColor(color);
            if(product.getSize().getId() != size.getId())
                product.setSize(size);
            if(product.getGender() != gender)
                product.setGender(gender);
            if(product.getPrice() != productPrice)
                product.setPrice(productPrice);
            if(product.getAmount() != (long) amountInStock)
                product.setAmount((long)amountInStock);

            productService.update(product.getId(), product);

            if(supply.getPrice() != productCost)
                supply.setPrice(productCost);
            if(supply.getDate() != supplyDate)
                supply.setDate(supplyDate);
            if(supply.getSupplier() != supplier)
                supply.setSupplier(supplier);
            supplyService.update(supply.getId(), supply);

            removedImages.forEach(x -> imageService.delete(x));
            addedImages.forEach(x -> {
                imageService.addToProduct(x, product.getId());
            });

            showAlert(Alert.AlertType.INFORMATION, "Produto Alterado", "O produto " + product.getName() +
                    " foi alterado com sucesso!");
            goToProductList();
        } catch (Exception ex) {
            showAlert(Alert.AlertType.WARNING, "Campos Ausentes", "Preencha todos os campos!");
        }
    }

    private void goToProductList () {
        btnAlterProduct.fireEvent(new ChangeSubSceneEvent(ChangeSubSceneEvent.SUBSCENE_CHANGED, "product-list-view"));
    }
}
