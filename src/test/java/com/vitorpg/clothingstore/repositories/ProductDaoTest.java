package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.dtos.ProductFilter;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

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

    private void save (String productName, Long categoryId) {
        Product product = new Product();
        product.setName(productName);
        product.setAmount(35L);
        product.setPrice(50.0);
        product.setGender(Gender.MALE);
        product.setSize(new Size() {{ setId(1L);}});
        product.setCategory(new Category() {{ setId(categoryId); }});
        product.setStyle(new Style() {{ setId(1L);}});
        product.setColor(new Color() {{ setId(1L);}});
        product.setMaterial(new Material() {{ setId(1L);}});
        ProductDao productDao = new ProductDao(new ImageDao());
        productDao.save(product);
    }

    @Test
    @DisplayName("save product")
    @Tag("save")
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
    void findById_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(400L);
        assertNull(product);
    }

    @Test
    @DisplayName("find all products")
    void findAll_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        List<Product> products = productDao.findAll();
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("get total count of products")
    void getTotalCount_Success () {
        ProductDao productDao = new ProductDao(new ImageDao());
        Long maxCount = productDao.getTotalCount();
        assertTrue(maxCount > 0);
    }

    @Test
    @DisplayName("get total count of filtered products")
    void getTotalCountFiltered_Success () {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.save(new Category() {{
            setName("Calças");
            setSizeType(SizeType.NUMBER);
        }});

        for (int i = 0; i < 6; i++) {
            save("product 0" + i);
        }

        for (int i = 0; i < 4; i++) {
            save("product 0" + (6 + i), 2L);
        }

        ProductDao productDao = new ProductDao(new ImageDao());
        Long maxCount = productDao.getTotalCountFiltered(new ProductFilter() {{
                setCategory(Optional.of(new Category() {{
                    setId(2L);
                }}));
        }});
        assertTrue(maxCount == 4);
    }

    @Test
    @DisplayName("find products paginate ")
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
    @DisplayName("find products paginate with filters")
    void findPaginatedFiltered_Success() {
        CategoryDao categoryDao = new CategoryDao();
        categoryDao.save(new Category() {{
            setName("Calças");
            setSizeType(SizeType.NUMBER);
        }});

        for (int i = 0; i < 6; i++) {
            save("product 0" + i);
        }

        for (int i = 0; i < 4; i++) {
            save("product 0" + (6 + i), 2L);
        }


        ProductDao productDao = new ProductDao(new ImageDao());
        List<Product> products = productDao.findPaginatedFiltered(3L, 0L, new ProductFilter() {{
            setCategory(Optional.of(new Category() {{
                setId(2L);
            }}));
        }});
        assertEquals(3, products.stream().count());
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertTrue(products.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update product")
    void update_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(1L);
        product.setName("Calção Jeans Curta Delavê Rasgada");
        assertTrue(productDao.update(1L, product));
    }

    @Test
    @DisplayName("not update non-existent product")
    void update_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        Product product = productDao.findById(1L);
        product.setName("Calção Jeans Curta Delavê Rasgada");
        assertFalse(productDao.update(400L, product));
    }

    @Test
    @DisplayName("adjust product amount")
    void adjustAmount_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertTrue(productDao.adjustAmount(1L, 30L));
    }

    @Test
    @DisplayName("delete product")
    void delete_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertTrue(productDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent product")
    void delete_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertFalse(productDao.delete(400L));
    }

    @Test
    @DisplayName("remove product")
    void remove_Success() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertTrue(productDao.remove(1L));
    }

    @Test
    @DisplayName("not remove non-existent product")
    void remove_Error() {
        ProductDao productDao = new ProductDao(new ImageDao());
        assertFalse(productDao.remove(400L));
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