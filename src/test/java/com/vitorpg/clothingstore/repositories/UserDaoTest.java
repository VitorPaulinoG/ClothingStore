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
    @Tag("useEntity")
    void createEntityPreviously () {
        save_Success();
    }

    @Test
    @Order(1)
    void save_Success() {
        User user = new User();
        user.setName("Fulano");
        user.setEmail("fulano@gmail.com");
        user.setPassword("1234125345");

        UserDao userDao = new UserDao();
        assertTrue(userDao.save(user), "user was saved");
    }

    @Test
    @Tag("useEntity")
    @Order(2)
    void findById_Success() {
        UserDao userDao = new UserDao();
        User user = userDao.findById(1L);
        assertNotNull(user, "user with id = 1 was found");
        assertNotNull(user.getId(), "user.id is not null");
        assertNotNull(user.getName(), "user.name is not null");
        assertNotNull(user.getEmail(), "user.email is not null");
        assertNotNull(user.getPassword(), "user.password is not null");
    }

    @Test
    @Tag("useEntity")
    @Order(3)
    void findById_Error() {
        UserDao userDao = new UserDao();
        assertNull(userDao.findById(400L), "user with id = 400 not found");
    }

    @Test
    @Tag("useEntity")
    @Order(4)
    void findAll_Success() {
        UserDao userDao = new UserDao();
        List<User> users = userDao.findAll();
        assertNotNull(users, "users not found");
        assertFalse(users.isEmpty(), "users list is not empty");
        assertTrue(users.stream().allMatch(x -> x != null), "no user is null");
    }

    @Test
    @Tag("useEntity")
    @Order(5)
    void findFirstByEmail_Success() {
        UserDao userDao = new UserDao();
        User user = userDao.findFirstByEmail("fulano@gmail.com");
        assertNotNull(user, "user with email = fulano@gmail.com was found");
        assertNotNull(user.getId(), "user.id is not null");
        assertNotNull(user.getName(), "user.name is not null");
        assertNotNull(user.getEmail(), "user.email is not null");
        assertNotNull(user.getPassword(), "user.password is not null");
    }

    @Test
    @Tag("useEntity")
    @Order(6)
    void findFirstByEmail_Error() {
        UserDao userDao = new UserDao();
        User user = userDao.findFirstByEmail("anonymous@gmail.com");
        assertNull(user, "user with email = anonymous@gmail.com not found");
    }

    @Test
    @Tag("useEntity")
    @Order(7)
    void update_Success() {
        UserDao userDao = new UserDao();
        User oldUser = userDao.findById(1L);
        oldUser.setEmail("fulanodetal@gmail.com");

        assertTrue(userDao.update(1L, oldUser), "user with id = 1 updated");
    }

    @Test
    @Tag("useEntity")
    @Order(8)
    void update_Error() {
        UserDao userDao = new UserDao();
        User oldUser = userDao.findById(1L);
        oldUser.setEmail("fulanodetal@gmail.com");

        assertFalse(userDao.update(400L, new User() {{
            setName("algumacoisa");
            setEmail("algumacoisa@gmail.com");
            setPassword("13e32r353");
        }}), "user with id = 400 not updated");
    }

    @Test
    @Tag("useEntity")
    @Order(9)
    void delete_Success() {
        UserDao userDao = new UserDao();
        assertTrue(userDao.delete(1L), "user with id = 1 deleted");
    }

    @Test
    @Tag("useEntity")
    @Order(10)
    void delete_Error() {
        UserDao userDao = new UserDao();
        assertFalse(userDao.delete(400L), "user with id = 400 not deleted");
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