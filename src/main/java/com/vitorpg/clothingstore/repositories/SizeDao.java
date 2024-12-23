package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Size;
import com.vitorpg.clothingstore.models.enums.SizeType;
import com.vitorpg.clothingstore.repositories.interfaces.RemovableDao;
import com.vitorpg.clothingstore.repositories.interfaces.FinderDao;
import com.vitorpg.clothingstore.repositories.interfaces.UpdaterDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SizeDao implements FinderDao<Size>, UpdaterDao<Size>, RemovableDao {
    @Override
    public Size findById(Long id) {
        String query = "select * from tb_size where id = ?";
        Size size = null;
        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, id);

            ResultSet result = statement.executeQuery();
            if(result.first()) {
                size = new Size();
                size.setId(result.getLong("id"));
                size.setValue(result.getString("value"));
                size.setSizeType(SizeType.valueOf(result.getString("sizeType")));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return size;
    }

    @Override
    public List<Size> findAll() {
        String query = "select * from tb_size";
        List<Size> sizes = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Size size = new Size();
                size.setId(result.getLong("id"));
                size.setValue(result.getString("value"));
                size.setSizeType(SizeType.valueOf(result.getString("sizeType")));
                sizes.add(size);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return sizes;
    }

    public boolean addIntoCategory(Long sizeId, Long categoryId) {
        String sql = "insert into tb_categorySize (categoryId, sizeId) values (?, ?)";

        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, categoryId);
            statement.setLong(2, sizeId);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    public boolean save(Size size, Long categoryId) {
        String sql =
                """
                with newSize as (
                    insert into tb_size (value, sizeType)
                    value (?, ?)
                    returning id
                )
                insert into tb_categorySize (categoryId, sizeId)
                select ?, id
                from newSize
                """;

        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, size.getValue());
            statement.setString(2, size.getSizeType().name());
            statement.setLong(3, categoryId);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    @Override
    public boolean update(Long id, Size size) {
        String sql =
                """
                update table tb_size
                set value = ?, sizeType = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, size.getValue());
            statement.setString(2, size.getSizeType().name());
            statement.setLong(3, id);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return  (affectedRows > 0);
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_size where id = ?";

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

    public boolean removeOfCategory(Long sizeId, Long categoryId) {
        String sql = "delete from tb_categorySize where sizeId = ? and categoryId = ?";
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, sizeId);
            statement.setLong(2, categoryId);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }
}
