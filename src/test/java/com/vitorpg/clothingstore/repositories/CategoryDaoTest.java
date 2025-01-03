package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Category;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDaoTest {

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
    @DisplayName("save category")
    @Tag("save")
    void save_Success() {
        Category category = new Category();
        category.setName("Calças");
        category.setSizeType(SizeType.NUMBER);
        CategoryDao categoryDao = new CategoryDao();
        assertTrue(categoryDao.save(category));
    }

    @Test
    @DisplayName("find category by id")
    void findById_Success() {
        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(1L);
        assertNotNull(category);
        assertNotNull(category.getId());
        assertNotNull(category.getName());
    }

    @Test
    @DisplayName("not find category by id")
    void findById_Error() {
        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(400L);
        assertNull(category);
    }

    @Test
    @DisplayName("find all categories")
    void findAll_Success() {
        CategoryDao categoryDao = new CategoryDao();
        List<Category> categories = categoryDao.findAll();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertTrue(categories.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update category")
    void update_Success() {
        CategoryDao categoryDao = new CategoryDao();
        Category category = categoryDao.findById(1L);
        category.setName("Calções");
        assertTrue(categoryDao.update(1L, category));
    }

    @Test
    @DisplayName("not update non-existent category")
    void update_Error() {
        CategoryDao categoryDao = new CategoryDao();
        assertFalse(categoryDao.update(400L, new Category() {{
            setName("Calções");
        }}));
    }

    @Test
    @DisplayName("delete category")
    void delete_Success() {
        CategoryDao categoryDao = new CategoryDao();
        assertTrue(categoryDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent category")
    void delete_Error() {
        CategoryDao categoryDao = new CategoryDao();
        assertFalse(categoryDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = "TRUNCATE TABLE tb_Category RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}