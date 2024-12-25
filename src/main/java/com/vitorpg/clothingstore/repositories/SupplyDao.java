package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Product;
import com.vitorpg.clothingstore.models.Supplier;
import com.vitorpg.clothingstore.models.Supply;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.*;
import java.util.List;

public class SupplyDao extends BaseDao<Supply> implements Dao<Supply> {
    @Override
    public Supply findById(Long id) {
        String query = "select * from tb_supply where id = ?";

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
    public List<Supply> findAll() {
        String query = "select * from tb_supply";

        return super.queryMany(
                query,
                result -> buildEntity(result)
        );
    }

    private Supply buildEntity (ResultSet result) {
        try {
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

            return supply;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Supply supply){
        try {
            statement.setDouble(1, supply.getDeliveryPrice());
            statement.setDouble(2, supply.getPrice());
            statement.setLong(3, supply.getProduct().getId());
            statement.setLong(4, supply.getSupplier().getId());
            statement.setDate(5, Date.valueOf(supply.getDate()));
            statement.setString(6, supply.getStatus());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Supply supply) {
        String sql =
            """
            insert into tb_supply (deliveryPrice, price, productId, supplierId, date, status)
            values (?, ?, ?, ?, ?, ?)
            """;

        return super.execute(
                sql,
                statement -> buildStatement(statement, supply)
        );
    }

    @Override
    public boolean update(Long id, Supply supply) {
        String sql =
            """
            update tb_supply
            set deliveryPrice = ?, price = ?, productId = ?, supplierId = ?, date = ?, status = ?
            where id = ?
            """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, supply);
                    statement.setLong(7, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_supply where id = ?";
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
