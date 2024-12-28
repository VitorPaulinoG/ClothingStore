package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;
import com.vitorpg.clothingstore.repositories.interfaces.PaginatedDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductDao extends BaseDao<Product> implements Dao<Product>, PaginatedDao<Product> {
    private ImageDao imageDao;

    public ProductDao (ImageDao imageDao) {
        this.imageDao = imageDao;
    }

    @Override
    public Product findById(Long id) {
        String query =
                """
                select *
                from tb_product
                where id = ?
                """;

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
    public List<Product> findAll() {
        String query =
                """
                select *
                from tb_product
                """;

        return super.queryMany(
            query,
            result ->buildEntity(result)
        );
    }

    @Override
    public List<Product> findPaginated(Long maxCount, Long offset) {
        String query =
                """
                select *
                from tb_product
                limit ?
                offset ?
                """;

        return super.queryMany(
            query,
            result -> buildEntity(result),
            statement -> {
                try {
                    statement.setLong(1, maxCount);
                    statement.setLong(2, offset);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    public Long getMaxCount () {
        String query =
                """
                select COUNT(*) as maxCount
                from tb_Product
                """;
        return super.queryScalar(
                query,
                result -> buildEntity(result),
                "maxCount",
                Long.class
        );
    }

    private Product buildEntity (ResultSet result) {
        try {
            Product product = new Product();
            product.setId(result.getLong("id"));
            product.setName(result.getString("name"));
            product.setGender(Gender.valueOf(result.getString("gender")));
            product.setAmount(result.getLong("amount"));
            product.setPrice(result.getDouble("price"));
            product.setCategory(
                new Category(){{
                    setId(result.getLong("categoryId"));
                }}
            );
            product.setSize(
                new Size(){{
                    setId(result.getLong("sizeId"));
                }}
            );
            product.setStyle(
                new Style(){{
                    setId(result.getLong("styleId"));
                }}
            );
            product.setColor(
                new Color() {{
                    setId(result.getLong("colorId"));
                }}
            );
            product.setMaterial(
                new Material() {{
                    setId(result.getLong("materialId"));
                }}
            );
            product.setImages(imageDao.getAllByProductId(product.getId()));
            return product;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Product product) {
        try {
            statement.setString(1, product.getName());
            statement.setString(2, product.getGender().name());
            statement.setLong(3, product.getAmount());
            statement.setDouble(4, product.getPrice());
            statement.setLong(5, product.getCategory().getId());
            statement.setLong(6, product.getStyle().getId());
            statement.setLong(7, product.getSize().getId());
            statement.setLong(8, product.getColor().getId());
            statement.setLong(9, product.getMaterial().getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public boolean save(Product product) {
        String sql =
            """
            insert into tb_product (name, gender, amount, price, categoryId, styleId, sizeId, colorId, materialId)
            values (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        return super.execute(
            sql,
            statement -> buildStatement(statement, product)
        );
    }

    @Override
    public boolean update(Long id, Product product) {
        String sql =
                """
                update tb_product
                set name = ?, gender = ?, amount = ?, price = ?, categoryId = ?,
                styleId = ?, sizeId = ?, colorId = ?, materialId = ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, product);
                    statement.setLong(10, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    public boolean adjustAmount(Long id, Long amount) {
        String sql =
                """
                update tb_product
                set amount = amount + ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    statement.setLong(1, amount);
                    statement.setLong(2, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_product where id = ?";

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
