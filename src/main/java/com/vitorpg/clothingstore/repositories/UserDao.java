package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.User;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDao extends  BaseDao<User> implements Dao<User> {

    @Override
    public User findById(Long id){
        String query = "select * from tb_user where id = ?";

        return super.queryOne(
                query,
                result -> buildEntity(result),
                statement -> {
                    try {
                        statement.setLong(1, id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    @Override
    public List<User> findAll() {
        String query = "select * from tb_user";

        return super.queryMany(
                query,
                result -> buildEntity(result)
        );
    }

    public User findFirstByEmail(String email) {
        String query = "select * from tb_user where email = ? limit 1";

        return super.queryOne(
                query,
                result -> buildEntity(result),
                statement -> {
                    try {
                        statement.setString(1, email);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    private User buildEntity (ResultSet result) {
        try {
            User user = new User();
            user.setId(result.getLong("id"));
            user.setName(result.getString("name"));
            user.setEmail(result.getString("email"));
            user.setPassword(result.getString("password"));
            return user;
        } catch (SQLException ex) {
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, User user) {
        try{
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(User user) {
        String sql = "insert into tb_user (name, email, password) value (?, ?, ?)";

        return super.execute(
                sql,
                statement -> buildStatement(statement, user)
        );
    }

    @Override
    public boolean update(Long id, User user) {
        String sql =
                """
                update table tb_user
                set name = ?, email = ?, password = ?
                where id = ?
                """;
        return super.execute(
                sql,
                statement -> {
                    try {
                        buildStatement(statement, user);
                        statement.setLong(4, id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_user where id = ?";
        return super.execute(
                sql,
                statement -> {
                    try {
                        statement.setLong(1, id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }
}
