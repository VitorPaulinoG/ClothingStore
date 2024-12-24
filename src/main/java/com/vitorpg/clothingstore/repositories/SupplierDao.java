package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Supplier;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SupplierDao extends BaseDao<Supplier> implements Dao<Supplier> {
    @Override
    public Supplier findById(Long id) {
        String query = "select * from tb_supplier where id = ?";

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
    public List<Supplier> findAll() {
        String query = "select * from tb_supplier";

        return super.queryMany(
                query,
                result -> buildEntity(result)
        );
    }

    private Supplier buildEntity (ResultSet result){
        try {
            Supplier supplier = new Supplier();
            supplier.setId(result.getLong("id"));
            supplier.setName(result.getString("name"));
            return supplier;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Supplier supplier) {
        try {
            statement.setString(1, supplier.getName());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean save(Supplier supplier) {
        String sql = "insert into tb_supplier (name) value (?)";

        return super.execute(
            sql,
            statement -> buildStatement(statement, supplier)
        );
    }

    @Override
    public boolean update(Long id, Supplier supplier) {
        String sql =
                """
                update table tb_supplier
                set name = ?
                where id = ?
                """;

        return super.execute(
                sql,
                statement -> {
                    try {
                        buildStatement(statement, supplier);
                        statement.setLong(2, id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_supplier where id = ?";

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
