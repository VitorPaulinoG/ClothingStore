package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplyDaoTest {

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

        Supplier supplier = new Supplier();
        supplier.setName("Riachuelo");
        SupplierDao supplierDao = new SupplierDao();
        supplierDao.save(supplier);

        if (testInfo.getTags().contains("save")) {
            return;
        }

        save_Success();
    }


    @Test
    @DisplayName("save supply")
    @Tag("save")
    void save_Success() {
        Supply supply = new Supply();
        supply.setPrice(25.0);
        supply.setDate(LocalDate.now());
        supply.setProduct(new Product() {{ setId(1L);}});
        supply.setSupplier(new Supplier() {{ setId(1L);}});
        supply.setStatus("ENTREGUE");
        SupplyDao supplyDao = new SupplyDao();
        assertTrue(supplyDao.save(supply));
    }

    @Test
    @DisplayName("find supply by id")
    void findById_Success() {
        SupplyDao supplyDao = new SupplyDao();
        Supply supply = supplyDao.findById(1L);
        assertNotNull(supply);
        assertNotNull(supply.getId());
        assertNotNull(supply.getPrice());
        assertNotNull(supply.getDate());
        assertNotNull(supply.getProduct());
        assertNotNull(supply.getSupplier());
        assertNotNull(supply.getStatus());
    }

    @Test
    @DisplayName("not find supply by id")
    void findById_Error() {
        SupplyDao supplyDao = new SupplyDao();
        Supply supply = supplyDao.findById(400L);
        assertNull(supply);
    }

    @Test
    @DisplayName("find all supplies")
    void findAll_Success() {
        SupplyDao supplyDao = new SupplyDao();
        List<Supply> supplies = supplyDao.findAll();
        assertNotNull(supplies);
        assertFalse(supplies.isEmpty());
        assertTrue(supplies.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update supply")
    void update_Success() {
        SupplyDao supplyDao = new SupplyDao();
        Supply supply = supplyDao.findById(1L);
        assertTrue(supplyDao.update(1L, supply));
    }

    @Test
    @DisplayName("not update non-existent supply")
    void update_Error() {
        SupplyDao supplyDao = new SupplyDao();
        Supply supply = supplyDao.findById(1L);
        assertFalse(supplyDao.update(400L, supply));
    }

    @Test
    @DisplayName("delete supply")
    void delete_Success() {
        SupplyDao supplyDao = new SupplyDao();
        assertTrue(supplyDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent supply")
    void delete_Error() {
        SupplyDao supplyDao = new SupplyDao();
        assertFalse(supplyDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = """   
            TRUNCATE TABLE tb_Supply RESTART IDENTITY CASCADE;
            TRUNCATE TABLE tb_Supplier RESTART IDENTITY CASCADE;
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