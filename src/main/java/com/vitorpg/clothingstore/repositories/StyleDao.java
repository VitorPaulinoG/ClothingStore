package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Style;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StyleDao implements Dao<Style> {
    @Override
    public Style findById(Long id) {
        String query = "select * from tb_style where id = ?";
        Style style = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                style = new Style();
                style.setId(result.getLong("id"));
                style.setName(result.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return style;
    }

    @Override
    public List<Style> findAll() {
        String query = "select * from tb_style";
        List<Style> styles = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Style style = new Style();
                style.setId(result.getLong("id"));
                style.setName(result.getString("name"));;
                styles.add(style);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return styles;
    }

    @Override
    public boolean save(Style style) {
        String sql = "insert into tb_style (name) value (?)";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, style.getName());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Style style) {
        String sql =
                """
                update table tb_style
                set name = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, style.getName());
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
        String sql = "delete from tb_style where id = ?";

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
