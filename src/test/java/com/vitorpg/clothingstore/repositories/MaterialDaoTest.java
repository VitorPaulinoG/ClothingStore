package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Material;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialDaoTest {

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
    @DisplayName("save material")
    @Tag("save")
    void save_Success() {
        Material material = new Material();
        material.setName("Jeans");
        MaterialDao materialDao = new MaterialDao();
        assertTrue(materialDao.save(material));
    }

    @Test
    @DisplayName("find material by id")
    void findById_Success() {
        MaterialDao materialDao = new MaterialDao();
        Material material = materialDao.findById(1L);
        assertNotNull(material);
        assertNotNull(material.getId());
        assertNotNull(material.getName());
    }

    @Test
    @DisplayName("not find material by id")
    void findById_Error() {
        MaterialDao materialDao = new MaterialDao();
        Material material = materialDao.findById(400L);
        assertNull(material);
    }

    @Test
    @DisplayName("find all materials")
    void findAll_Success() {
        MaterialDao materialDao = new MaterialDao();
        List<Material> materials = materialDao.findAll();
        assertNotNull(materials);
        assertFalse(materials.isEmpty());
        assertTrue(materials.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update material")
    void update_Success() {
        MaterialDao materialDao = new MaterialDao();
        Material material = materialDao.findById(1L);
        material.setName("Malha");
        assertTrue(materialDao.update(1L, material));
    }

    @Test
    @DisplayName("not update non-existent material")
    void update_Error() {
        MaterialDao materialDao = new MaterialDao();
        assertFalse(materialDao.update(400L, new Material() {{
            setName("Malha");
        }}));
    }

    @Test
    @DisplayName("delete material")
    void delete_Success() {
        MaterialDao materialDao = new MaterialDao();
        assertTrue(materialDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent material")
    void delete_Error() {
        MaterialDao materialDao = new MaterialDao();
        assertFalse(materialDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = "TRUNCATE TABLE tb_Material RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}