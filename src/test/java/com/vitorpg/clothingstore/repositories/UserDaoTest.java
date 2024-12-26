package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

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
    @Tag("save")
    @DisplayName("save user")
    @Order(1)
    void save_Success() {
        User user = new User();
        user.setName("Fulano");
        user.setEmail("fulano@gmail.com");
        user.setPassword("1234125345");

        UserDao userDao = new UserDao();
        assertTrue(userDao.save(user));
    }

    @Test
    @DisplayName("find user by id")
    @Order(2)
    void findById_Success() {
        UserDao userDao = new UserDao();
        User user = userDao.findById(1L);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
    }

    @Test
    @DisplayName("not find user by id")
    @Order(3)
    void findById_Error() {
        UserDao userDao = new UserDao();
        assertNull(userDao.findById(400L));
    }

    @Test
    @DisplayName("find all users")
    @Order(4)
    void findAll_Success() {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertTrue(users.stream().allMatch(x -> x != null));
    }

    @Test
    @DisplayName("find user by email")
    @Order(5)
    void findFirstByEmail_Success() {
        UserDao userDao = new UserDao();
        User user = userDao.findFirstByEmail("fulano@gmail.com");
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getName());
        assertNotNull(user.getEmail());
        assertNotNull(user.getPassword());
    }

    @Test
    @DisplayName("not find non-existent user with email")
    @Order(6)
    void findFirstByEmail_Error() {
        UserDao userDao = new UserDao();
        User user = userDao.findFirstByEmail("anonymous@gmail.com");
        assertNull(user);
    }

    @Test
    @DisplayName("update user")
    @Order(7)
    void update_Success() {
        UserDao userDao = new UserDao();
        User oldUser = userDao.findById(1L);
        oldUser.setEmail("fulanodetal@gmail.com");

        assertTrue(userDao.update(1L, oldUser));
    }

    @Test
    @DisplayName("not update non-existent user")
    @Order(8)
    void update_Error() {
        UserDao userDao = new UserDao();
        User oldUser = userDao.findById(1L);
        oldUser.setEmail("fulanodetal@gmail.com");

        assertFalse(userDao.update(400L, new User() {{
            setName("algumacoisa");
            setEmail("algumacoisa@gmail.com");
            setPassword("13e32r353");
        }}));
    }

    @Test
    @DisplayName("delete user")
    @Order(9)
    void delete_Success() {
        UserDao userDao = new UserDao();
        assertTrue(userDao.delete(1L));
    }

    @Test
    @DisplayName("not delete non-existent user")
    @Order(10)
    void delete_Error() {
        UserDao userDao = new UserDao();
        assertFalse(userDao.delete(400L));
    }

    @AfterEach
    void restoreTestDb () {
        String sql = "TRUNCATE TABLE tb_User RESTART IDENTITY CASCADE";
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}