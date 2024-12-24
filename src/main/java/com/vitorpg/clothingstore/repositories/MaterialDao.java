package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Material;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MaterialDao extends BaseDao<Material> implements Dao<Material> {
    @Override
    public Material findById(Long id) {
        String query = "select * from tb_material where id = ?";

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
    public List<Material> findAll() {
        String query = "select * from tb_material";

        return super.queryMany(
            query,
            result -> buildEntity(result)
        );
    }

    private Material buildEntity (ResultSet result) {
        try {
            Material material = new Material();
            material.setId(result.getLong("id"));
            material.setName(result.getString("name"));
            return material;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Material material) {
        try {
            statement.setString(1, material.getName());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Material material) {
        String sql = "insert into tb_material (name) value (?)";

        return super.execute(
            sql,
            statement -> buildStatement(statement, material)
        );
    }

    @Override
    public boolean update(Long id, Material material) {
        String sql =
                """
                update table tb_material
                set name = ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, material);
                    statement.setLong(2, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_material where id = ?";

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
