package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.models.enums.Gender;
import com.vitorpg.clothingstore.models.enums.SizeType;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;
import com.vitorpg.clothingstore.repositories.interfaces.PaginatedDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDao implements Dao<Product>, PaginatedDao<Product> {
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
                where p.id = ?
                """;
        Product product = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                product = new Product();
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return product;
    }

    @Override
    public List<Product> findAll() {
        String query =
                """
                select *
                from tb_product
                """;
        List<Product> products = new ArrayList<Product>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
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
                products.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return products;
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
        List<Product> products = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, maxCount);
            statement.setLong(2, offset);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
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

                products.add(product);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return products;
    }
    @Override
    public boolean save(Product product) {
        String sql =
            """
            insert into tb_product (name, gender, amount, price, categoryId, styleId, sizeId, colorId, materialId)
            value (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getGender().name());
            statement.setLong(3, product.getAmount());
            statement.setDouble(4, product.getPrice());
            statement.setLong(5, product.getCategory().getId());
            statement.setLong(6, product.getStyle().getId());
            statement.setLong(7, product.getSize().getId());
            statement.setLong(8, product.getColor().getId());
            statement.setLong(9, product.getMaterial().getId());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Product product) {
        String sql =
                """
                update table tb_product
                set name = ?, gender = ?, amount = ?, price = ?, categoryId = ?,
                styleId = ?, sizeId = ?, colorId = ?, materialId = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getGender().name());
            statement.setLong(3, product.getAmount());
            statement.setDouble(4, product.getPrice());
            statement.setLong(5, product.getCategory().getId());
            statement.setLong(6, product.getStyle().getId());
            statement.setLong(7, product.getSize().getId());
            statement.setLong(8, product.getColor().getId());
            statement.setLong(9, product.getMaterial().getId());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    public boolean adjustAmount(Long id, Long amount) {
        String sql =
                """
                update table tb_product
                set amount = amount + ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, amount);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_product where id = ?";

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
