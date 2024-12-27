package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Image;
import com.vitorpg.clothingstore.repositories.interfaces.RemovableDao;
import com.vitorpg.clothingstore.repositories.interfaces.UpdaterDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ImageDao extends BaseDao<Image> implements RemovableDao, UpdaterDao<Image> {
    public Image findById (Long id) {
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
    public List<Image> getAllByProductId(Long productId) {
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

    private Image buildEntity (ResultSet result) {
        try {
            Image image = new Image();
            image.setId(result.getLong("id"));
            image.setData(result.getBytes("data"));
            image.setFormat(result.getString("format"));
            image.setProductId(result.getLong("productId"));
            return image;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Image image) {
        try {
            statement.setBytes(1, image.getData());
            statement.setString(2, image.getFormat());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addToProduct(Image image, Long productId) {
        String sql =
            """
            insert into tb_image (data, format, productId)
            values (?, ?, ?)
            """;
        return super.execute(
                sql,
                statement -> {
                    try {
                        buildStatement(statement, image);
                        statement.setLong(3, productId);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    public boolean update(Long id, Image image) {
        String sql =
                """
                update tb_image
                set data = ?, format = ?
                where id = ?
                """;

        return super.execute(
                sql,
                statement -> {
                    try {
                        buildStatement(statement, image);
                        statement.setLong(3, id);
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
