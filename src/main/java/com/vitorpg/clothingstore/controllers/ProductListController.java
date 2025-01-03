package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.dtos.ProductFilter;
import com.vitorpg.clothingstore.events.ChangeSubSceneEvent;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.ProductStatus;
import com.vitorpg.clothingstore.services.*;
import com.vitorpg.clothingstore.utils.ImageUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.Optional;
import java.util.function.Function;


public class ProductListController {
    private ProductService productService = new ProductService();
    private CategoryService categoryService = new CategoryService();
    private SizeService sizeService = new SizeService();
    private ColorService colorService = new ColorService();
    private MaterialService materialService = new MaterialService();
    private StyleService styleService = new StyleService();

    private Long pageMaxCount = 10L;
    private Long pageOffset = 0L;
    private int productsTotalCount;
    private int pageCount;

    @FXML
    private Button btnAddProduct;

    @FXML
    public TextField txtName;

    @FXML
    private TableView<Product> tbProductList;

    @FXML
    private TableColumn<Product, Long> colProductId;

    @FXML
    private TableColumn<Product, Image> colProductImage;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    private ChoiceBox<Category> cbCategory;

    @FXML
    private ChoiceBox<Gender> cbGender;

    @FXML
    private ChoiceBox<Size> cbSize;

    @FXML
    private ChoiceBox<Color> cbColor;

    @FXML
    private ChoiceBox<Material> cbMaterial;

    @FXML
    private ChoiceBox<Style> cbStyle;

    @FXML
    private ChoiceBox<ProductStatus> cbStatus;

    @FXML
    private Pagination pagProductList;

    @FXML
    private TableColumn<Product, Void> colProductActions;

    @FXML
    private FlowPane flowFilters;

    private double productImageWidth = 154;
    private double productImageHeight = 136;
    private ProductFilter productFilter = new ProductFilter();

    private ObservableList<Product> productObservableList;

    @FXML
    public void initialize () {

        productObservableList = FXCollections
                .observableArrayList(productService.findPaginated(pageMaxCount, pageOffset));

        cbGender.setItems(FXCollections
                .observableArrayList(Gender.values()));
        cbGender.getItems().add(0, null);
        cbGender.setValue(cbGender.getItems().get(0));
        cbCategory.setItems(FXCollections
                .observableArrayList(categoryService.findAll()));
        cbCategory.getItems().add(0, null);
        cbCategory.setValue(cbCategory.getItems().get(0));
        cbMaterial.setItems(FXCollections
                .observableArrayList(materialService.findAll()));
        cbMaterial.getItems().add(0, null);
        cbMaterial.setValue(cbMaterial.getItems().get(0));
        cbColor.setItems(FXCollections
                .observableArrayList(colorService.findAll()));
        cbColor.getItems().add(0, null);
        cbColor.setValue(cbColor.getItems().get(0));
        cbSize.setItems(FXCollections
                .observableArrayList(sizeService.findAll()));
        cbSize.getItems().add(0, null);
        cbSize.setValue(cbSize.getItems().get(0));
        cbStyle.setItems(FXCollections
                .observableArrayList(styleService.findAll()));
        cbStyle.getItems().add(0, null);
        cbStyle.setValue(cbStyle.getItems().get(0));
        cbStatus.setItems(FXCollections
                .observableArrayList(ProductStatus.values()));
        cbStatus.getItems().add(0, null);
        cbStatus.setValue(cbStatus.getItems().get(0));

        flowFilters.getChildren().stream().filter(x -> x instanceof VBox)
            .forEach(x -> ((VBox) x).prefWidthProperty().bind(Bindings.createDoubleBinding(() -> {
                int maxColumns = Math.max((int) ((flowFilters.getWidth()) / (200.0 + flowFilters.getHgap())), 1);
                var prefWidth = ((flowFilters.getWidth()) / maxColumns) - flowFilters.getHgap();
                return prefWidth;
            }, flowFilters.widthProperty())));


        cbGender.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        cbCategory.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        cbMaterial.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        cbColor.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        cbSize.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        cbStyle.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        cbStatus.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            refreshFilter();
            loadProducts();
        });

        colProductId.setCellValueFactory(new PropertyValueFactory<Product, Long>("id"));
        colProductImage.setCellValueFactory(
                cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getImages().isEmpty() ? null :
                        getFirstImage(cellData.getValue())));
        colProductImage.setCellFactory(column -> new TableCell<Product, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    javafx.scene.image.Image fxImage = ImageUtils.bytesToJavaFXImage(item.getData());
                    imageView.setImage(fxImage);
                    imageView.setFitWidth(productImageWidth);
                    imageView.setPreserveRatio(true);

                    Rectangle clip = new Rectangle(
                            (int) productImageWidth,
                            (int) productImageHeight);
                    clip.setArcWidth(8);
                    clip.setArcHeight(8);
                    imageView.setClip(clip);

                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(imageView);
                    this.setAlignment(Pos.CENTER);
                    stackPane.setMaxSize(productImageWidth, productImageHeight);
                    stackPane.setMinSize(productImageWidth, productImageHeight);
                    stackPane.setAlignment(Pos.TOP_CENTER);

                    setGraphic(stackPane);
                }
            }
        });

        colProductName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        colProductPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        colProductActions.setCellFactory(new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
            @Override
            public TableCell<Product, Void> call (TableColumn<Product, Void> param) {
                return new TableCell<Product, Void>() {
                    private final Button btn_Alter = new Button("Alterar");
                    private final Button btn_Remove = new Button("Remover");


                    {
                        btn_Alter.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                        });

                        btn_Remove.setOnAction(event -> {
                            Product product = getTableView().getItems().get(getIndex());
                            boolean result = showAlert(Alert.AlertType.CONFIRMATION, "Remover Produto", "Você tem certeza de que deseja remover esse produto? ");
                            if (result)
                                productService.remove(product.getId());
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

        tbProductList.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            ScrollPane parentScene = (ScrollPane) tbProductList.getScene().lookup("#paneSubScene");

            if (parentScene != null) {
                double deltaY = event.getDeltaY();
                parentScene.setVvalue(parentScene.getVvalue() - deltaY / parentScene.getHeight());
                event.consume();
            }
        });

        tbProductList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_LAST_COLUMN);

        tbProductList.setItems(productObservableList);

        refreshFilter();
        refreshPagination();
        pagProductList.setPageFactory(pageNumber -> {
            pageOffset = ((long) pageNumber) * pageMaxCount;
            loadProducts();
            return new VBox();
        });
    }

    public boolean showAlert (Alert.AlertType alertType, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        var alertPane = alert.getDialogPane();
        alertPane.getStylesheets().add("/main-styles.css");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        var result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    private void refreshPagination () {
        productsTotalCount = (int) (long) productService.getTotalCountFiltered(productFilter);
        pageCount = (int) Math.ceil((double) productsTotalCount / pageMaxCount);
        pagProductList.setPageCount(pageCount);
        pagProductList.setCurrentPageIndex((int) (long) pageOffset);
    }

    private void refreshFilter () {
        if(!txtName.getText().isEmpty() && !txtName.getText().isBlank()) {
            productFilter.setName(Optional.of(txtName.getText().trim()));
        }

        productFilter.setGender(Optional.ofNullable(cbGender.getSelectionModel().getSelectedItem()));

        productFilter.setCategory(Optional.ofNullable(cbCategory.getSelectionModel().getSelectedItem()));

        productFilter.setSize(Optional.ofNullable(cbSize.getSelectionModel().getSelectedItem()));

        productFilter.setColor(Optional.ofNullable(cbColor.getSelectionModel().getSelectedItem()));

        productFilter.setMaterial(Optional.ofNullable(cbMaterial.getSelectionModel().getSelectedItem()));

        productFilter.setStyle(Optional.ofNullable(cbStyle.getSelectionModel().getSelectedItem()));

        productFilter.setStatus(Optional.ofNullable(cbStatus.getSelectionModel().getSelectedItem()));
    }

    public void loadProducts () {
        productObservableList = FXCollections
                .observableArrayList(productService.findPaginatedFiltered(pageMaxCount, pageOffset, productFilter));

        tbProductList.setItems(productObservableList);
        tbProductList.setFixedCellSize(156);
        tbProductList.prefHeightProperty().bind(tbProductList.fixedCellSizeProperty()
                .multiply(Math.min(productObservableList.size() + 5, pageMaxCount + 1)));
        tbProductList.setMinHeight(0);
    }

    public Image getFirstImage(Product product) {
        return product.getImages().getFirst();
    }

    @FXML
    public void goToAddProductScene (ActionEvent event) {
        btnAddProduct.fireEvent(new ChangeSubSceneEvent(ChangeSubSceneEvent.SUBSCENE_CHANGED, "add-product-view"));
    }
}