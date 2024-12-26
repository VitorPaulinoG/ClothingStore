package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Supplier;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SupplierDaoTest {

    @BeforeAll
    static void setTestDbConnection () {
        DbConnection.loadConfig("test");
    }

    @BeforeEach
    void createEntityBeforeTest (TestInfo testInfo) {
        if (testInfo.getTags().contains("save")) {
            return;
        }

        save_Success();
    }

    @Test
    @DisplayName("save supplier")
    @Tag("save")
    @Order(1)
    void save_Success() {
        Supplier supplier = new Supplier();
        supplier.setName("Riachuelo");
        SupplierDao supplierDao = new SupplierDao();
        assertTrue(supplierDao.save(supplier));
    }

    @Test
    @DisplayName("find supplier by id")
    @Order(2)
    void findById_Success() {
        SupplierDao supplierDao = new SupplierDao();
        Supplier supplier = supplierDao.findById(1L);
        assertNotNull(supplier);
        assertNotNull(supplier.getId());
        assertNotNull(supplier.getName());
    }

    @Test
    @DisplayName("not find user by id")
    @Order(3)
    void findById_Error() {
        SupplierDao supplierDao = new SupplierDao();
        Supplier supplier = supplierDao.findById(400L);
        assertNull(supplier);
    }

    @Test
    @DisplayName("find all suppliers")
    @Order(4)
    void findAll_Success() {
        SupplierDao supplierDao = new SupplierDao();
        List<Supplier> suppliers = supplierDao.findAll();
        assertNotNull(suppliers);
        assertFalse(suppliers.isEmpty());
        assertTrue(suppliers.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update supplier")
    @Order(5)
    void update_Success() {
        SupplierDao supplierDao = new SupplierDao();
        Supplier supplier = supplierDao.findById(1L);
        supplier.setName("C&A");
        assertTrue(supplierDao.update(1L, supplier));
    }

    @Test
    @DisplayName("not update non-existent supplier")
    @Order(6)
    void update_Error() {
        SupplierDao supplierDao = new SupplierDao();
        assertFalse(supplierDao.update(400L, new Supplier() {{
            setName("C&A");
        }}));
    }

    @Test
    @DisplayName("delete supplier")
    @Order(7)
    void delete_Success() {
        SupplierDao supplierDao = new SupplierDao();
        assertTrue(supplierDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent supplier")
    @Order(8)
    void delete_Error() {
        SupplierDao supplierDao = new SupplierDao();
        assertFalse(supplierDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = "TRUNCATE TABLE tb_Supplier RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}