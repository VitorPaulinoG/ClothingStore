package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class SaleDao extends BaseDao<Sale> implements Dao<Sale> {
    @Override
    public Sale findById(Long id) {
        String query =
            """
            select *
            from tb_sale
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
    public List<Sale> findAll() {
        String query = "select * from tb_sale";

        return super.queryMany(
            query,
            result -> buildEntity(result)
        );
    }

    private Sale buildEntity (ResultSet result) {
        try {
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
            return sale;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Sale sale) {
        try {
            statement.setLong(1, sale.getAmount());
            statement.setTimestamp(2, Timestamp.valueOf(sale.getDateTime()));
            statement.setDouble(3, sale.getTotalPrice());
            statement.setLong(4, sale.getProduct().getId());
            statement.setLong(5, sale.getVendor().getId());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Sale sale) {
        String sql =
            """
            insert into tb_sale (amount, dateTime, totalPrice, productId, vendorId)
            value (?, ?, ?, ?, ?)
            """;

        return super.execute(
                sql,
                statement -> buildStatement(statement, sale)
        );
    }

    @Override
    public boolean update(Long id, Sale sale) {
        String sql =
                """
                update table tb_sale
                set amount = ?, dateTime = ?, totalPrice = ?, productId = ?, vendorId = ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, sale);
                    statement.setLong(6, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_sale where id = ?";

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
