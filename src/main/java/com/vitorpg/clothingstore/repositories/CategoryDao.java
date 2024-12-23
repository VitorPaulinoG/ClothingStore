package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Category;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao implements Dao<Category> {
    @Override
    public Category findById(Long id) {
        String query = "select * from tb_category where id = ?";
        Category category = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                category = new Category();
                category.setId(result.getLong("id"));
                category.setName(result.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return category;
    }

    @Override
    public List<Category> findAll() {
        String query = "select * from tb_category";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Category category = new Category();
                category.setId(result.getLong("id"));
                category.setName(result.getString("name"));;
                categories.add(category);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    @Override
    public boolean save(Category category) {
        String sql = "insert into tb_category (name) value (?)";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category.getName());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Category category) {
        String sql =
                """
                update table tb_category
                set name = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, category.getName());
            statement.setLong(2 , id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_category where id = ?";

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
