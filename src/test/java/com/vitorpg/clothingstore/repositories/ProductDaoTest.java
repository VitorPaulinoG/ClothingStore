package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDaoTest {

    @BeforeAll
    static void setTestDbConnection () {
        DbConnection.loadConfig("test");
    }

    @BeforeEach
    void createEntityBeforeTest (TestInfo testInfo) {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.save(new Category() {{
            setName("Calções");
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

        if (testInfo.getTags().contains("save")) {
            return;
        }

        save_Success();


    }

    private void save (String productName) {
        Product product = new Product();
        product.setName(productName);
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
    }

    @Test
    @DisplayName("save product")
    @Tag("save")
    @Order(1)
    void save_Success() {
        Product product = new Product();
        product.setName("Bermuda Jeans Curta Delavê Rasgada");
        product.setAmount(35L);
        product.setPrice(50.0);
        product.setGender(Gender.MALE);
        product.setSize(new Size() {{ setId(1L);}});
        product.setCategory(new Category() {{ setId(1L); }});
        product.setStyle(new Style() {{ setId(1L);}});
        product.setColor(new Color() {{ setId(1L);}});
        product.setMaterial(new Material() {{ setId(1L);}});
        ProductDao productDao = new ProductDao(new ImageDao());
        assertTrue(productDao.save(product));
    }

    @Test
    @DisplayName("find product by id")
    @Order(2)
    void findById_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(1L);
        assertNotNull(product);
        assertNotNull(product.getId());
        assertNotNull(product.getName());
        assertNotNull(product.getAmount());
        assertNotNull(product.getPrice());
        assertNotNull(product.getGender());
        assertNotNull(product.getCategory());
        assertNotNull(product.getMaterial());
        assertNotNull(product.getSize());
        assertNotNull(product.getStyle());
    }

    @Test
    @DisplayName("not find product by id")
    @Order(3)
    void findById_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(400L);
        assertNull(product);
    }

    @Test
    @DisplayName("find all products")
    @Order(4)
    void findAll_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("get total count of products")
    @Order(5)
    void getMaxCount_Success () {
        ProductDao productDao = new ProductDao(new ImageDao());
        Long maxCount = productDao.getMaxCount();
        assertTrue(maxCount > 0);
    }

    @Test
    @DisplayName("find products paginate ")
    @Order(6)
    void findPaginated_Success() {
        for (int i = 0; i < 10; i++) {
            save("product 0" + i);
        }


        ProductDao productDao = new ProductDao(new ImageDao());
        List<Product> products = productDao.findPaginated(3L, 0L);
        assertEquals(3, products.stream().count());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update product")
    @Order(7)
    void update_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(1L);
        product.setName("Calção Jeans Curta Delavê Rasgada");
        assertTrue(productDao.update(1L, product));
    }

    @Test
    @DisplayName("not update non-existent product")
    @Order(8)
    void update_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(1L);
        product.setName("Calção Jeans Curta Delavê Rasgada");
        assertFalse(productDao.update(400L, product));
    }

    @Test
    @DisplayName("adjust product amount")
    @Order(9)
    void adjustAmount_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertTrue(productDao.adjustAmount(1L, 30L));
    }

    @Test
    @DisplayName("delete product")
    @Order(10)
    void delete_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertTrue(productDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent product")
    @Order(11)
    void delete_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertFalse(productDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = """   
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