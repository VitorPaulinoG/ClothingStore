package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Color;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ColorDaoTest {

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
    @DisplayName("save color")
    @Tag("save")
    @Order(1)
    void save_Success() {
        Color color = new Color();
        color.setName("Laranja");

        ColorDao colorDao = new ColorDao();
        assertTrue(colorDao.save(color));
    }

    @Test
    @DisplayName("find color by id")
    @Order(2)
    void findById_Success() {
        ColorDao colorDao = new ColorDao();
        Color color = colorDao.findById(1L);
        assertNotNull(color);
        assertNotNull(color.getId());
        assertNotNull(color.getName());
    }

    @Test
    @DisplayName("not find color by id")
    @Order(3)
    void findById_Error() {
        ColorDao colorDao = new ColorDao();
        Color color = colorDao.findById(400L);
        assertNull(color);
    }

    @Test
    @DisplayName("find all colors")
    @Order(4)
    void findAll_Success() {
        ColorDao colorDao = new ColorDao();
        List<Color> colors = colorDao.findAll();
        assertNotNull(colors);
        assertFalse(colors.isEmpty());
        assertTrue(colors.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update color")
    @Order(5)
    void update_Success() {
        ColorDao colorDao = new ColorDao();
        Color color = colorDao.findById(1L);
        color.setName("Orange");
        assertTrue(colorDao.update(1L, color));
    }

    @Test
    @DisplayName("not update non-existent color")
    @Order(6)
    void update_Error() {
        ColorDao colorDao = new ColorDao();
        assertTrue(colorDao.update(400L, new Color() {{
            setName("Orange");
        }}));
    }

    @Test
    @DisplayName("delete color")
    @Order(7)
    void delete_Success() {
        ColorDao colorDao = new ColorDao();
        assertTrue(colorDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent color")
    @Order(8)
    void delete_Error() {
        ColorDao colorDao = new ColorDao();
        assertFalse(colorDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = "TRUNCATE TABLE tb_Color RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}