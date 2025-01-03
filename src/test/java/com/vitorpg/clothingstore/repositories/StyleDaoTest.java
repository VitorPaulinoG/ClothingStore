package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Style;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StyleDaoTest {
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
    @DisplayName("save style")
    @Tag("save")
    void save_Success() {
        Style style = new Style();
        style.setName("Moda Praia");
        StyleDao styleDao = new StyleDao();
        assertTrue(styleDao.save(style));
    }

    @Test
    @DisplayName("find style by id")
    void findById_Success() {
        StyleDao styleDao = new StyleDao();
        Style style = styleDao.findById(1L);
        assertNotNull(style);
        assertNotNull(style.getId());
        assertNotNull(style.getName());
    }

    @Test
    @DisplayName("not find style by id")
    void findById_Error() {
        StyleDao styleDao = new StyleDao();
        Style style = styleDao.findById(400L);
        assertNull(style);
    }

    @Test
    @DisplayName("find all styles")
    void findAll_Success() {
        StyleDao styleDao = new StyleDao();
        List<Style> styles = styleDao.findAll();
        assertNotNull(styles);
        assertFalse(styles.isEmpty());
        assertTrue(styles.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update style")
    void update_Success() {
        StyleDao styleDao = new StyleDao();
        Style style = styleDao.findById(1L);
        style.setName("Moda Fitness");
        assertTrue(styleDao.update(1L, style));
    }

    @Test
    @DisplayName("not update non-existent style")
    void update_Error() {
        StyleDao styleDao = new StyleDao();
        assertFalse(styleDao.update(400L, new Style() {{
            setName("Moda Fitness");
        }}));
    }

    @Test
    @DisplayName("delete style")
    void delete_Success() {
        StyleDao styleDao = new StyleDao();
        assertTrue(styleDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent style")
    void delete_Error() {
        StyleDao styleDao = new StyleDao();
        assertFalse(styleDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = "TRUNCATE TABLE tb_Style RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}