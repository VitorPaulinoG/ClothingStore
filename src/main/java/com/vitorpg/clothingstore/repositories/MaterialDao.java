package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Material;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MaterialDao implements Dao<Material> {
    @Override
    public Material findById(Long id) {
        String query = "select * from tb_material where id = ?";
        Material material = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                material = new Material();
                material.setId(result.getLong("id"));
                material.setName(result.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return material;
    }

    @Override
    public List<Material> findAll() {
        String query = "select * from tb_material";
        List<Material> materials = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Material material = new Material();
                material.setId(result.getLong("id"));
                material.setName(result.getString("name"));;
                materials.add(material);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return materials;
    }

    @Override
    public boolean save(Material material) {
        String sql = "insert into tb_material (name) value (?)";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, material.getName());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Material material) {
        String sql =
                """
                update table tb_material
                set name = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, material.getName());
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
        String sql = "delete from tb_material where id = ?";

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
