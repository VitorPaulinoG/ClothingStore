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
            setSizeType(SizeType.NUMBER);
        }});


        if (testInfo.getTags().contains("save")) {
            return;
        }

        save_Success();
    }

    @Test
    @DisplayName("save size")
    @Tag("save")
    void save_Success() {
        Size size = new Size();
        size.setValue("48");
        size.setSizeType(SizeType.NUMBER);
        SizeDao sizeDao = new SizeDao();
        assertTrue(sizeDao.save(size));
    }

    @Test
    @DisplayName("find size by id")
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
    void findById_Error() {
        SizeDao sizeDao = new SizeDao();
        Size size = sizeDao.findById(400L);
        assertNull(size);
    }

    @Test
    @DisplayName("find all sizes")
    void findAll_Success() {
        SizeDao sizeDao = new SizeDao();
        List<Size> sizes = sizeDao.findAll();
        assertNotNull(sizes);
        assertFalse(sizes.isEmpty());
        assertTrue(sizes.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("find by size type")
    void findAllBySizeType_Success() {
        SizeDao sizeDao = new SizeDao();
        List<Size> sizes = sizeDao.findAllBySizeType(SizeType.NUMBER);
        assertNotNull(sizes);
        assertFalse(sizes.isEmpty());
        assertTrue(sizes.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("update size")
    void update_Success() {
        SizeDao sizeDao = new SizeDao();
        Size size = sizeDao.findById(1L);
        size.setValue("40");
        assertTrue(sizeDao.update(1L, size));
    }

    @Test
    @DisplayName("not update non-existent size")
    void update_Error() {
        SizeDao sizeDao = new SizeDao();
        assertFalse(sizeDao.update(400L, new Size() {{
            setValue("40");
        }}));
    }

    @Test
    @DisplayName("delete size")
    void delete_Success() {
        SizeDao sizeDao = new SizeDao();
        assertTrue(sizeDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent size")
    void delete_Error() {
        SizeDao sizeDao = new SizeDao();
        assertFalse(sizeDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql =
                """
                TRUNCATE TABLE tb_Size RESTART IDENTITY CASCADE;
                TRUNCATE TABLE tb_Category RESTART IDENTITY CASCADE
                """;

        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}