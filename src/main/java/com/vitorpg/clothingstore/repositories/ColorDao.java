package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Color;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ColorDao extends BaseDao<Color> implements Dao<Color> {
    @Override
    public Color findById(Long id) {
        String query = "select * from tb_color where id = ?";

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
    public List<Color> findAll() {
        String query = "select * from tb_color";

        return super.queryMany(
                query,
                result -> buildEntity(result)
        );
    }

    private Color buildEntity (ResultSet result) {
        try {
            var color = new Color();
            color.setId(result.getLong("id"));
            color.setName(result.getString("name"));
            return color;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Color color) {
        try {
            statement.setString(1, color.getName());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Color color) {
        String sql = "insert into tb_color (name) value (?)";

        return super.execute(
            sql,
            statement -> buildStatement(statement, color)
        );
    }

    @Override
    public boolean update(Long id, Color color) {
        String sql =
                """
                update table tb_color
                set name = ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, color);
                    statement.setLong(2, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_color where id = ?";

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
