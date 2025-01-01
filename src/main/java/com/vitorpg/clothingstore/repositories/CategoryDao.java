package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Category;
import com.vitorpg.clothingstore.models.enums.SizeType;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CategoryDao extends BaseDao<Category> implements Dao<Category> {
    @Override
    public Category findById(Long id) {
        String query = "select * from tb_category where id = ?";

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
    public List<Category> findAll() {
        String query = "select * from tb_category";

        return super.queryMany(
            query,
            result -> buildEntity(result)
        );
    }

    private Category buildEntity (ResultSet result) {
        try {
            Category category = new Category();
            category.setId(result.getLong("id"));
            category.setName(result.getString("name"));
            category.setSizeType(SizeType.valueOf(result.getString("sizeType")));
            return category;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Category category) {
        try {
            statement.setString(1, category.getName());
            statement.setString(2, category.getSizeType().name());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Category category) {
        String sql = "insert into tb_category (name, sizeType) values (?, ?)";

        return super.execute(
            sql,
            statement -> buildStatement(statement, category)
        );
    }

    @Override
    public boolean update(Long id, Category category) {
        String sql =
                """
                update tb_category
                set name = ?, sizeType = ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, category);
                    statement.setLong(3 , id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_category where id = ?";

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
