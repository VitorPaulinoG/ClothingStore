package com.vitorpg.clothingstore.controllers;

import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.services.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;

public class ProductListController {
    private ProductService productService = new ProductService();

    private Long pageMaxCount = 10L;
    private Long pageOffset = 0L;

    @FXML
    private TableView<Product> tb_productList;

    @FXML
    private TableColumn<Product, Long> tbc_productId;

    @FXML
    private TableColumn<Product, Image> tbc_productImage;

    @FXML
    private TableColumn<Product, String> tbc_productName;

    @FXML
    private TableColumn<Product, Double> tbc_productPrice;


    private ObservableList<Product> observableList;

    @FXML
    public void initialize () {
        observableList = FXCollections
                .observableArrayList(productService.findPaginated(pageMaxCount, pageOffset));
        observableList.add(new Product() {{
            setId(2L);
            setName("aaaa");
            setPrice(34.0);
        }});
        observableList.add(new Product() {{
            setId(3L);
            setName("bbbb");
            setPrice(34.0);
        }});
        observableList.add(new Product() {{
            setId(4L);
            setName("cccc");
            setPrice(40.0);
        }});


        tbc_productId.setCellValueFactory(new PropertyValueFactory<Product, Long>("id"));
        tbc_productId.setCellValueFactory(new PropertyValueFactory<Product, Long>("id"));
        tbc_productName.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        tbc_productPrice.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));

        tb_productList.setItems(observableList);

    }
}
