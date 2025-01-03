package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ImageDaoTest {

    @BeforeAll
    static void setTestDbConnection () {
        DbConnection.loadConfig("test");
    }

    @BeforeEach
    void createEntityBeforeTest (TestInfo testInfo) {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.save(new Category() {{
            setName("Calções");
            setSizeType(SizeType.NUMBER);
        }});

        Size size = new Size();
        size.setValue("48");
        size.setSizeType(SizeType.NUMBER);
        SizeDao sizeDao = new SizeDao();
        sizeDao.save(size);

        Style style = new Style();
        style.setName("Moda Praia");
        StyleDao styleDao = new StyleDao();
        styleDao.save(style);

        Color color = new Color();
        color.setName("Laranja");
        ColorDao colorDao = new ColorDao();
        colorDao.save(color);

        Material material = new Material();
        material.setName("Jeans");
        MaterialDao materialDao = new MaterialDao();
        materialDao.save(material);

        Product product = new Product();
        product.setName("TESTE");
        product.setAmount(35L);
        product.setPrice(50.0);
        product.setGender(Gender.MALE);
        product.setSize(new Size() {{ setId(1L);}});
        product.setCategory(new Category() {{ setId(1L); }});
        product.setStyle(new Style() {{ setId(1L);}});
        product.setColor(new Color() {{ setId(1L);}});
        product.setMaterial(new Material() {{ setId(1L);}});
        ProductDao productDao = new ProductDao(new ImageDao());
        productDao.save(product);

        if (testInfo.getTags().contains("save")) {
            return;
        }

        addToProduct_Success();
    }

    @Test
    @DisplayName("add image to product")
    @Tag("save")
    void addToProduct_Success() {
        try {
            Image image = new Image();
            File file = new File("src/main/resources/com/vitorpg/clothingstore/images/login-bg.png");

            image.setData(Files.readAllBytes(file.toPath()));
            image.setFormat(Files.probeContentType(file.toPath()).split("/")[1]);

            ImageDao imageDao = new ImageDao();
            assertTrue(imageDao.addToProduct(image, 1L));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Test
    @DisplayName("find image by id")
    void findById_Success () {
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.findById(1L);
        assertNotNull(image);
    }

    @Test
    @DisplayName("not find non-existent image by id")
    void findById_Error () {
        ImageDao imageDao = new ImageDao();
        Image image = imageDao.findById(400L);
        assertNull(image);
    }

    @Test
    @DisplayName("get images of product")
    void getAllByProductId_Success() {
        ImageDao imageDao = new ImageDao();
        List<Image> images = imageDao.getAllByProductId(1L);
        assertNotNull(images);
        assertFalse(images.isEmpty());
        assertTrue(images.stream().allMatch(x -> x != null));
    }


    @Test
    @DisplayName("update image")
    void update_Success() {
        try {
            File file = new File("src/main/resources/com/vitorpg/clothingstore/images/jason-leung-UMncYEfO9-U-unsplash.jpg");
            Image image = new Image();
            image.setData(Files.readAllBytes(file.toPath()));
            image.setFormat(Files.probeContentType(file.toPath()).split("/")[1]);

            ImageDao imageDao = new ImageDao();

            assertTrue(imageDao.update(1L, image));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("not update non-existent image")
    void update_Error() {
        try {
            File file = new File("src/main/resources/images/jason-leung-UMncYEfO9-U-unsplash.jpg");
            Image image = new Image();
            image.setData(Files.readAllBytes(file.toPath()));
            image.setFormat(Files.probeContentType(file.toPath()).split("/")[1]);
            ImageDao imageDao = new ImageDao();

            assertTrue(imageDao.update(400L, image));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Test
    @DisplayName("delete image")
    void delete_Success() {
        ImageDao imageDao = new ImageDao();
        assertTrue(imageDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent image")
    void delete_Error() {
        ImageDao imageDao = new ImageDao();
        assertFalse(imageDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = """   
            TRUNCATE TABLE tb_Image RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Product RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Material RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Color RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Style RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Size RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Category RESTART IDENTITY CASCADE;
        """;

        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}