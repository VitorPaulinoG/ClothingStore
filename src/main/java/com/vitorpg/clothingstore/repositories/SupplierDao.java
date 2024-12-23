package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Supplier;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao implements Dao<Supplier> {
    @Override
    public Supplier findById(Long id) {
        String query = "select * from tb_supplier where id = ?";
        Supplier supplier = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet result = statement.executeQuery();
            if (result.first()) {
                supplier = new Supplier();
                supplier.setId(result.getLong("id"));
                supplier.setName(result.getString("name"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return supplier;
    }

    @Override
    public List<Supplier> findAll() {
        String query = "select * from tb_supplier";
        List<Supplier> suppliers = new ArrayList<>();

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Supplier supplier = new Supplier();
                supplier.setId(result.getLong("id"));
                supplier.setName(result.getString("name"));;
                suppliers.add(supplier);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return suppliers;
    }

    @Override
    public boolean save(Supplier supplier) {
        String sql = "insert into tb_supplier (name) value (?)";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, supplier.getName());

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Supplier supplier) {
        String sql =
                """
                update table tb_supplier
                set name = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, supplier.getName());
            statement.setLong(2, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_supplier where id = ?";

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
