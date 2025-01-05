package com.vitorpg.clothingstore.repositories;

import com.vitorpg.clothingstore.dtos.ProductFilter;
import com.vitorpg.clothingstore.dtos.SaleFilter;
import com.vitorpg.clothingstore.models.*;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;
import com.vitorpg.clothingstore.repositories.interfaces.PaginatedDao;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class SaleDao extends BaseDao<Sale> implements Dao<Sale>, PaginatedDao<Sale> {
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
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd HH:mm:ss")
                    .optionalStart()
                    .appendPattern(".SSSSSS")
                    .optionalEnd()
                    .toFormatter();
            sale.setDateTime(LocalDateTime.parse(result.getString("dateTime").substring(0, 19), formatter));
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
            values (?, ?, ?, ?, ?)
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
                update tb_sale
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

    @Override
    public List<Sale> findPaginated(Long maxCount, Long offset) {
        String query =
                """
                select *
                from tb_sale
                limit ?
                offset ?
                """;

        return super.queryMany(
                query,
                result -> buildEntity(result),
                statement -> {
                    try {
                        statement.setLong(1, maxCount);
                        statement.setLong(2, offset);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    public List<Sale> findPaginatedFiltered(Long maxCount, Long offset, SaleFilter saleFilter) {
        List<Object> properties = new ArrayList<>();
        String query =
                """
                select
                s.* 
                """;
        if (saleFilter.getProductName().isPresent()) {
            query +=
                """
                from tb_sale s
                join tb_product p
                    on p.id = s.productId
                """;
        } else {
            query += " from tb_sale s";
        }

        query += " where 1=1";

        if (saleFilter.getAmountComparator().isPresent() && saleFilter.getAmount().isPresent()) {
            query += " and amount " + saleFilter.getAmountComparator().get() + " ?";
            properties.add(saleFilter.getAmount().get());
        }

        if (saleFilter.getBeforeDateTime().isPresent()) {
            query += " and dateTime <= ?";
            properties.add(saleFilter.getBeforeDateTime().get());

        }
        if (saleFilter.getAfterDateTime().isPresent()) {
            query += " and dateTime >= ?";
            properties.add(saleFilter.getAfterDateTime().get());
        }

        if (saleFilter.getProductName().isPresent()) {
            query += " and p.name like ?";
            properties.add("%" + saleFilter.getProductName().get() + "%");
        }

        query +=
                """
                order by dateTime DESC
                limit ?
                offset ?
                """;
        return super.queryMany(
                query,
                result -> buildEntity(result),
                statement -> {
                    try {
                        int i = 1;
                        for (var item : properties) {
                            statement.setObject(i, item);
                            i++;
                        }
                        statement.setLong(i, maxCount);
                        statement.setLong(i + 1, offset);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }

    public Long getTotalCountFiltered (SaleFilter saleFilter) {
        List<Object> properties = new ArrayList<>();
        String query =
                """
                select COUNT(s.*) as maxCount 
                """;
        if (saleFilter.getProductName().isPresent()) {
            query +=
                    """
                    from tb_sale s
                    join tb_product p
                        on p.id = s.productId
                    """;
        } else {
            query += " from tb_sale s";
        }

        query += " where 1=1";

        if (saleFilter.getAmountComparator().isPresent() && saleFilter.getAmount().isPresent()) {
            query += " and amount " + saleFilter.getAmountComparator().get() + " ?";
            properties.add(saleFilter.getAmount().get());
        }

        if (saleFilter.getBeforeDateTime().isPresent()) {
            query += " and dateTime <= ?";
            properties.add(saleFilter.getBeforeDateTime().get());

        }
        if (saleFilter.getAfterDateTime().isPresent()) {
            query += " and dateTime >= ?";
            properties.add(saleFilter.getAfterDateTime().get());
        }

        if (saleFilter.getProductName().isPresent()) {
            query += " and p.name like ?";
            properties.add("%" + saleFilter.getProductName().get() + "%");
        }

        return super.queryScalar(
                query,
                statement -> {
                    try {
                        int i = 1;
                        for (var item : properties) {
                            statement.setObject(i, item);
                            i++;
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                },
                "maxCount",
                Long.class
        );
    }

    @Override
    public Long getTotalCount() {
        String query =
                """
                select COUNT(*) as maxCount
                from tb_sale
                """;
        return super.queryScalar(
                query,
                "maxCount",
                Long.class
        );
    }
}
