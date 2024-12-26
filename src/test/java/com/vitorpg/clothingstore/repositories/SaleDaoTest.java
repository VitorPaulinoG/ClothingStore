package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SaleDaoTest {

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
        sizeDao.save(size, 1L);

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

        User user = new User();
        user.setName("Fulano");
        user.setEmail("fulano@gmail.com");
        user.setPassword("1432wqaff3t");
        UserDao userDao = new UserDao();
        userDao.save(user);

        if (testInfo.getTags().contains("save")) {
            return;
        }

        save_Success();
    }

    @Test
    @DisplayName("save sale")
    @Tag("save")
    @Order(1)
    void save_Success() {
        Sale sale = new Sale();
        sale.setAmount(2L);
        sale.setTotalPrice(40.0);
        sale.setDateTime(LocalDateTime.now());
        sale.setProduct(new Product() {{ setId(1L);}});
        sale.setVendor(new User() {{ setId(1L);}});
        SaleDao saleDao = new SaleDao();
        assertTrue(saleDao.save(sale));
    }

    @Test
    @DisplayName("find sale by id")
    @Order(2)
    void findById_Success() {
        SaleDao saleDao = new SaleDao();
        Sale sale = saleDao.findById(1L);
        assertNotNull(sale);
        assertNotNull(sale.getId());
        assertNotNull(sale.getAmount());
        assertNotNull(sale.getTotalPrice());
        assertNotNull(sale.getDateTime());
        assertNotNull(sale.getProduct());
        assertNotNull(sale.getVendor());
    }

    @Test
    @DisplayName("not find non-existent sale by id")
    @Order(3)
    void findById_Error() {
        SaleDao saleDao = new SaleDao();
        Sale sale = saleDao.findById(400L);
        assertNull(sale);
    }

    @Test
    @DisplayName("find all sales")
    @Order(4)
    void findAll_Success() {
        SaleDao saleDao = new SaleDao();
        List<Sale> sales = saleDao.findAll();
        assertNotNull(sales);
        assertFalse(sales.isEmpty());
        assertTrue(sales.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update sale")
    @Order(5)
    void update_Success() {
        SaleDao saleDao = new SaleDao();
        Sale sale = saleDao.findById(1L);
        sale.setTotalPrice(50.0);
        assertTrue(saleDao.update(1L, sale));
    }

    @Test
    @DisplayName("not update non-existent sale")
    @Order(6)
    void update_Error() {
        SaleDao saleDao = new SaleDao();
        Sale sale = saleDao.findById(1L);
        sale.setTotalPrice(50.0);
        assertFalse(saleDao.update(400L, sale));
    }

    @Test
    @DisplayName("delete sale")
    @Order(7)
    void delete_Success() {
        SaleDao saleDao = new SaleDao();
        assertTrue(saleDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent sale")
    @Order(8)
    void delete_Error() {
        SaleDao saleDao = new SaleDao();
        assertFalse(saleDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = """   
            TRUNCATE TABLE tb_Sale RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_User RESTART IDENTITY CASCADE;
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