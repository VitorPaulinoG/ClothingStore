package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User>{

    @Override
    public User findById(Long id) {
        String query = "select * from tb_user where id = ?";
        User user = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }

    @Override
    public List<User> findAll() {
        String query = "select * from tb_user";
        List<User> users = new ArrayList<User>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                User user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));

                users.add(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return users;
    }

    public User findFirstByEmail(String email) {
        String query = "select * from tb_user where email = ? limit 1";
        User user = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                user = new User();
                user.setId(result.getLong("id"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return user;
    }
    @Override
    public boolean save(User user) {
        String sql = "insert into tb_user (name, email, password) value (?, ?, ?)";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);

    }

    @Override
    public boolean update(Long id, User entity) {
        String sql =
                """
                update table tb_user
                set name = ?, email = ?, password = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getEmail());
            statement.setString(3, entity.getPassword());
            statement.setLong(4, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_user where id = ?";

        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return  (affectedRows > 0);
    }
}
