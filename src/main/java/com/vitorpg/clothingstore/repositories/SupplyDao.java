package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.models.Supplier;
import com.vitorpg.clothingstore.models.Supply;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplyDao implements Dao<Supply> {
    @Override
    public Supply findById(Long id) {
        String query = "select * from tb_supply where id = ?";
        Supply supply = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                supply = new Supply();
                supply.setId(result.getLong("id"));
                supply.setDeliveryPrice(result.getDouble("deliveryPrice"));
                supply.setPrice(result.getDouble("price"));
                supply.setProduct(
                    new Product() {{
                        setId(result.getLong("productId"));
                    }}
                );
                supply.setSupplier(
                    new Supplier() {{
                        setId(result.getLong("supplierId"));
                    }}
                );
                supply.setDate(result.getDate("date").toLocalDate());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return supply;
    }

    @Override
    public List<Supply> findAll() {
        String query = "select * from tb_supply";
        List<Supply> supplies = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Supply supply = new Supply();
                supply.setId(result.getLong("id"));
                supply.setDeliveryPrice(result.getDouble("deliveryPrice"));
                supply.setPrice(result.getDouble("price"));
                supply.setProduct(
                        new Product() {{
                            setId(result.getLong("productId"));
                        }}
                );
                supply.setSupplier(
                        new Supplier() {{
                            setId(result.getLong("supplierId"));
                        }}
                );
                supply.setDate(result.getDate("date").toLocalDate());

                supplies.add(supply);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return supplies;
    }


    @Override
    public boolean save(Supply supply) {
        String sql =
            """
            insert into tb_supply (deliveryPrice, price, productId, supplierId, date, status)
            value (?, ?, ?, ?, ?, ?)
            """;
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, supply.getDeliveryPrice());
            statement.setDouble(2, supply.getPrice());
            statement.setLong(3, supply.getProduct().getId());
            statement.setLong(4, supply.getSupplier().getId());
            statement.setDate(5, Date.valueOf(supply.getDate()));
            statement.setString(6, supply.getStatus());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Supply supply) {
        String sql =
                """
                update table tb_supply
                set deliveryPrice = ?, price = ?, productId = ?, supplierId = ?, date = ?, status = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setDouble(1, supply.getDeliveryPrice());
            statement.setDouble(2, supply.getPrice());
            statement.setLong(3, supply.getProduct().getId());
            statement.setLong(4, supply.getSupplier().getId());
            statement.setDate(5, Date.valueOf(supply.getDate()));
            statement.setString(6, supply.getStatus());
            statement.setLong(7, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_supply where id = ?";

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
