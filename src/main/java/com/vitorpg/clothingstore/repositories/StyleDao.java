package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Style;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StyleDao extends BaseDao<Style> implements Dao<Style> {
    @Override
    public Style findById(Long id) {
        String query = "select * from tb_style where id = ?";

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
    public List<Style> findAll() {
        String query = "select * from tb_style";

        return super.queryMany(
            query,
            result -> buildEntity(result)
        );
    }

    private Style buildEntity (ResultSet result) {
        try {
            Style style = new Style();
            style.setId(result.getLong("id"));
            style.setName(result.getString("name"));
            return style;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Style style) {
        try {
            statement.setString(1, style.getName());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Style style) {
        String sql = "insert into tb_style (name) values (?)";

        return super.execute(
            sql,
            statement -> buildStatement(statement, style)
        );
    }

    @Override
    public boolean update(Long id, Style style) {
        String sql =
            """
            update tb_style
            set name = ?
            where id = ?
            """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, style);
                    statement.setLong(2, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_style where id = ?";

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
