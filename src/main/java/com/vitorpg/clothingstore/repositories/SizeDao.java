package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.models.Size;
import com.vitorpg.clothingstore.models.enums.SizeType;
import com.vitorpg.clothingstore.repositories.interfaces.RemovableDao;
import com.vitorpg.clothingstore.repositories.interfaces.FinderDao;
import com.vitorpg.clothingstore.repositories.interfaces.UpdaterDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SizeDao extends BaseDao<Size> implements FinderDao<Size>, UpdaterDao<Size>, RemovableDao {
    @Override
    public Size findById(Long id) {
        String query = "select * from tb_size where id = ?";

        return super.queryOne(
            query,
            result -> buildEntity(result),
            statement -> {
                try{
                    statement.setLong(1, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public List<Size> findAll() {
        String query = "select * from tb_size";

        return super.queryMany(
            query,
            result -> buildEntity(result)
        );
    }

    private Size buildEntity (ResultSet result) {
        try {
            Size size = new Size();
            size.setId(result.getLong("id"));
            size.setValue(result.getString("value"));
            size.setSizeType(SizeType.valueOf(result.getString("sizeType")));
            return size;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void buildStatement(PreparedStatement statement, Size size) {
        try {
            statement.setString(1, size.getValue());
            statement.setString(2, size.getSizeType().name());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean addIntoCategory(Long sizeId, Long categoryId) {
        String sql = "insert into tb_categorySize (categoryId, sizeId) values (?, ?)";

        return super.execute(
                sql,
                statement -> {
                    try {
                        statement.setLong(1, categoryId);
                        statement.setLong(2, sizeId);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    public boolean save(Size size, Long categoryId) {
        String sql =
                """
                with newSize as (
                    insert into tb_size (value, sizeType)
                    values (?, ?)
                    returning id
                )
                insert into tb_categorySize (categoryId, sizeId)
                select ?, id
                from newSize
                """;
        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, size);
                    statement.setLong(3, categoryId);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean update(Long id, Size size) {
        String sql =
                """
                update tb_size
                set value = ?, sizeType = ?
                where id = ?
                """;

        return super.execute(
            sql,
            statement -> {
                try {
                    buildStatement(statement, size);
                    statement.setLong(3, id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }

    @Override
    public boolean delete(Long id) {
        String sql = "delete from tb_size where id = ?";

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

    public boolean removeFromCategory(Long sizeId, Long categoryId) {
        String sql = "delete from tb_categorySize where sizeId = ? and categoryId = ?";

        return super.execute(
            sql,
            statement -> {
                try {
                    statement.setLong(1, sizeId);
                    statement.setLong(2, categoryId);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        );
    }
}
