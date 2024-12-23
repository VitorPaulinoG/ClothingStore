package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.repositories.interfaces.RemovableDao;
import com.vitorpg.clothingstore.repositories.interfaces.UpdaterDao;
import javafx.scene.image.Image;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ImageDao implements RemovableDao, UpdaterDao<byte[]> {

    public List<byte[]> getAllByProductId(Long productId) {
        String query =
             """
             select *
             from tb_image
             where productId = ?
             """;
        List<byte[]> images = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setLong(1, productId);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                images.add(result.getBytes("data"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return images;
    }

    public boolean addToProduct(byte[] image, Long productId) {
        String sql =
            """
            insert into tb_image (data, productId)
            value (?, ?)
            """;

        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBytes(1, image);
            statement.setLong(2, productId);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }

    public boolean update(Long id, byte[] imageData) {
        String sql =
                """
                update table tb_image
                set data = ?
                where id = ?
                """;
        int affectedRows = 0;
        try(Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setBytes(1, imageData);
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
        String sql = "delete from tb_image where id = ?";

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
