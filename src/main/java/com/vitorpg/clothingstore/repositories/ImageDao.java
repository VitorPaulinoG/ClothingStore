package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.repositories.interfaces.RemovableDao;
import com.vitorpg.clothingstore.repositories.interfaces.UpdaterDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ImageDao extends BaseDao<byte[]> implements RemovableDao, UpdaterDao<byte[]> {
    public byte[] findById (Long id) {
        String query =
                """
                select *
                from tb_image
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
    public List<byte[]> getAllByProductId(Long productId) {
        String query =
             """
             select *
             from tb_image
             where productId = ?
             """;

        return super.queryMany(
            query,
            result -> buildEntity(result),
            statement -> {
                try {
                    statement.setLong(1, productId);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    private byte[] buildEntity (ResultSet result) {
        try {
            return result.getBytes("data");
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, byte[] data) {
        try {
            statement.setBytes(1, data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addToProduct(byte[] imageData, Long productId) {
        String sql =
            """
            insert into tb_image (data, productId)
            values (?, ?)
            """;
        return super.execute(
                sql,
                statement -> {
                    try {
                        buildStatement(statement, imageData);
                        statement.setLong(2, productId);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    public boolean update(Long id, byte[] imageData) {
        String sql =
                """
                update tb_image
                set data = ?
                where id = ?
                """;

        return super.execute(
                sql,
                statement -> {
                    try {
                        buildStatement(statement, imageData);
                        statement.setLong(2, id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_image where id = ?";

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
