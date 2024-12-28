package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.models.Image;
import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.services.ProductService;
import com.vitorpg.clothingstore.utils.ImageUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.util.ArrayList;


public class ProductListController {
    private ProductService productService = new ProductService();

    private Long pageMaxCount = 10L;
    private Long pageOffset = 0L;

    @FXML
    private TableView<Product> tb_productList;

    @FXML
    private TableColumn<Product, Long> colProductId;

    @FXML
    private TableColumn<Product, Image> colProductImage;

    @FXML
    private TableColumn<Product, String> colProductName;

    @FXML
    private TableColumn<Product, Double> colProductPrice;

    @FXML
    private TableColumn<Product, Void> colProductActions;

    private double productImageWidth = 154;
    private double productImageHeight = 136;

    private ObservableList<Product> observableList;

    @FXML
    public void initialize () {
        observableList = FXCollections
                .observableArrayList(productService.findPaginated(pageMaxCount, pageOffset));
        observableList.add(new Product() {{
            setId(5L);
            setName("sadasd");
            setPrice(3443.3);
            setImages(new ArrayList<>());
        }});

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

        tb_productList.addEventFilter(javafx.scene.input.ScrollEvent.SCROLL, event -> {
            ScrollPane paneSubScene = (ScrollPane) tb_productList.getScene().lookup("#paneSubScene");

            if (paneSubScene != null) {
                double deltaY = event.getDeltaY();
                paneSubScene.setVvalue(paneSubScene.getVvalue() - deltaY / paneSubScene.getHeight());
                event.consume();
            }
        });


        tb_productList.setFixedCellSize(156);
        tb_productList.prefHeightProperty().bind(tb_productList.fixedCellSizeProperty().multiply(Math.min(observableList.size() + 5, pageMaxCount)));

        tb_productList.setMinHeight(0);
//        tb_productList.setMaxHeight();
        tb_productList.setItems(observableList);

    }



    public Image getFirstImage(Product product) {
        return product.getImages().getFirst();
    }
}
