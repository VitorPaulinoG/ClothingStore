package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Color;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ColorDao implements Dao<Color> {

    @Override
    public Color findById(Long id) {
        String query = "select * from tb_color where id = ?";
        Color color = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                color = new Color();
                color.setId(result.getLong("id"));
                color.setName(result.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return color;
    }

    @Override
    public List<Color> findAll() {
        String query = "select * from tb_color";
        List<Color> colors = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Color color = new Color();
                color.setId(result.getLong("id"));
                color.setName(result.getString("name"));;
                colors.add(color);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return colors;
    }

    @Override
    public boolean save(Color color) {
        String sql = "insert into tb_color (name) value (?)";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, color.getName());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Color color) {
        String sql =
                """
                update table tb_color
                set name = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, color.getName());
            statement.setLong(2, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_color where id = ?";

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
