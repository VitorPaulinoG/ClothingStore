package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Category;
import com.vitorpg.clothingstore.models.Size;
import com.vitorpg.clothingstore.models.enums.SizeType;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SizeDaoTest {

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


        if (testInfo.getTags().contains("save")) {
            return;
        }

        save_Success();
    }

    @Test
    @DisplayName("save size")
    @Tag("save")
    @Order(1)
    void save_Success() {
        Size size = new Size();
        size.setValue("48");
        size.setSizeType(SizeType.NUMBER);
        SizeDao sizeDao = new SizeDao();
        assertTrue(sizeDao.save(size, 1L));
    }

    @Test
    @DisplayName("find size by id")
    @Order(2)
    void findById_Success() {
        SizeDao sizeDao = new SizeDao();
        Size size = sizeDao.findById(1L);
        assertNotNull(size);
        assertNotNull(size.getId());
        assertNotNull(size.getValue());
        assertNotNull(size.getSizeType());
    }

    @Test
    @DisplayName("not find size by id")
    @Order(3)
    void findById_Error() {
        SizeDao sizeDao = new SizeDao();
        Size size = sizeDao.findById(400L);
        assertNull(size);
    }

    @Test
    @DisplayName("find all sizes")
    @Order(4)
    void findAll_Success() {
        SizeDao sizeDao = new SizeDao();
        List<Size> sizes = sizeDao.findAll();
        assertNotNull(sizes);
        assertFalse(sizes.isEmpty());
        assertTrue(sizes.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("add size into category of id")
    @Order(5)
    void addIntoCategory_Success() {
        SizeDao sizeDao = new SizeDao();
        Category category = new Category();
        category.setName("Bermudas");
        new CategoryDao().save(category);
        
        assertTrue(sizeDao.addIntoCategory(1L, 2L));
    }

    @Test
    @DisplayName("add non-existent size into category of id")
    @Order(6)
    void addIntoCategory_Error() {
        SizeDao sizeDao = new SizeDao();
        assertFalse(sizeDao.addIntoCategory(400L, 1L));
    }

    @Test
    @DisplayName("update size")
    @Order(7)
    void update_Success() {
        SizeDao sizeDao = new SizeDao();
        Size size = sizeDao.findById(1L);
        size.setValue("40");
        assertTrue(sizeDao.update(1L, size));
    }

    @Test
    @DisplayName("not update non-existent size")
    @Order(8)
    void update_Error() {
        SizeDao sizeDao = new SizeDao();
        assertFalse(sizeDao.update(400L, new Size() {{
            setValue("40");
        }}));
    }

    @Test
    @DisplayName("delete size")
    @Order(9)
    void delete_Success() {
        SizeDao sizeDao = new SizeDao();
        assertTrue(sizeDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent size")
    @Order(10)
    void delete_Error() {
        SizeDao sizeDao = new SizeDao();
        assertFalse(sizeDao.delete(400L));
    }

    @Test
    @DisplayName("remove size from category")
    @Order(11)
    void removeFromCategory_Success() {
        addIntoCategory_Success();
        SizeDao sizeDao = new SizeDao();
        assertTrue(sizeDao.removeFromCategory(1L, 1L));
    }

    @Test
    @DisplayName("remove non-existent size from category")
    @Order(12)
    void removeFromCategory_Error() {
        SizeDao sizeDao = new SizeDao();
        assertFalse(sizeDao.removeFromCategory(400L, 1L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql1 = "TRUNCATE TABLE tb_CategorySize";
        String sql2 = "TRUNCATE TABLE tb_Size RESTART IDENTITY CASCADE";
        String sql3 = "TRUNCATE TABLE tb_Category RESTART IDENTITY CASCADE";

        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement1 = conn.prepareStatement(sql1);
            PreparedStatement statement2 = conn.prepareStatement(sql2);
            PreparedStatement statement3 = conn.prepareStatement(sql3);
            statement1.executeUpdate();
            statement2.executeUpdate();
            statement3.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}