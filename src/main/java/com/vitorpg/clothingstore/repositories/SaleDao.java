package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.models.Sale;
import com.vitorpg.clothingstore.models.Supplier;
import com.vitorpg.clothingstore.models.User;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleDao implements Dao<Sale> {
    @Override
    public Sale findById(Long id) {
        String query =
            """
            select *
            from tb_sale
            where id = ?
            """;
        Sale sale = null;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            if (result.first()) {
                sale = new Sale();
                sale.setId(result.getLong("id"));
                sale.setAmount(result.getLong("amount"));
                sale.setDateTime(LocalDateTime.parse(result.getString("dateTime")));
                sale.setTotalPrice(result.getDouble("totalPrice"));
                sale.setProduct(
                    new Product(){{
                        setId(result.getLong("productId"));
                    }}
                );
                sale.setVendor(
                    new User() {{
                        setId(result.getLong("vendorId"));
                    }}
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sale;
    }

    @Override
    public List<Sale> findAll() {
        String query = "select * from tb_sale";
        List<Sale> sales = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Sale sale = new Sale();
                sale.setId(result.getLong("id"));
                sale.setAmount(result.getLong("amount"));
                sale.setDateTime(LocalDateTime.parse(result.getString("dateTime")));
                sale.setTotalPrice(result.getDouble("totalPrice"));
                sale.setProduct(
                        new Product(){{
                            setId(result.getLong("productId"));
                        }}
                );
                sale.setVendor(
                        new User() {{
                            setId(result.getLong("vendorId"));
                        }}
                );
                sales.add(sale);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sales;
    }

    @Override
    public boolean save(Sale sale) {
        String sql =
            """
            insert into tb_sale (amount, dateTime, totalPrice, productId, vendorId)
            value (?, ?, ?, ?, ?)
            """;

        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, sale.getAmount());
            statement.setTimestamp(2, Timestamp.valueOf(sale.getDateTime()));
            statement.setDouble(3, sale.getTotalPrice());
            statement.setLong(4, sale.getProduct().getId());
            statement.setLong(5, sale.getVendor().getId());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Sale sale) {
        String sql =
                """
                update table tb_sale
                set amount = ?, dateTime = ?, totalPrice = ?, productId = ?, vendorId = ?
                where id = ?
                """;

        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, sale.getAmount());
            statement.setTimestamp(2, Timestamp.valueOf(sale.getDateTime()));
            statement.setDouble(3, sale.getTotalPrice());
            statement.setLong(4, sale.getProduct().getId());
            statement.setLong(5, sale.getVendor().getId());
            statement.setLong(6, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_sale where id = ?";

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
