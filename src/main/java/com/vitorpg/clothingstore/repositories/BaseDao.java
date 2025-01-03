package com.vitorpg.clothingstore.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class BaseDao<T> {
    protected <U> U queryScalar (String sql, String scalarName, Class<U> returnType) {
        return queryScalar(sql, statement -> {}, scalarName, returnType);
    }
    protected <U> U queryScalar (String sql, Consumer<PreparedStatement> statementBuilder, String scalarName, Class<U> returnType) {
        U scalarValue = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statementBuilder.accept(statement);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                scalarValue = returnType.cast (result.getObject(scalarName));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return scalarValue;
    }

    protected T queryOne (String sql, Function<ResultSet, T> entityBuilder, Consumer<PreparedStatement> statementBuilder) {
        T entity = null;

        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statementBuilder.accept(statement);

            ResultSet result = statement.executeQuery();
            if (result.next()) {
                entity = entityBuilder.apply(result);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return entity;
    }

    protected List<T> queryMany (String sql, Function<ResultSet, T> entityBuilder, Consumer<PreparedStatement> statementBuilder) {
        List<T> entities = new ArrayList<>();
        try (Connection conn = DbConnection.getConnection()){
            PreparedStatement statement = conn.prepareStatement(sql);
            statementBuilder.accept(statement);

            ResultSet result = statement.executeQuery();
            while (result.next()) {
                entities.add(entityBuilder.apply(result));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return entities;
    }

    protected List<T> queryMany (String sql, Function<ResultSet, T> entityBuilder) {
        return queryMany(sql, entityBuilder, statement -> {});
    }

    protected boolean execute(String sql, Consumer<PreparedStatement> statementBuilder) {
        int affectedRows = 0;
        try (Connection conn = DbConnection.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statementBuilder.accept(statement);

            affectedRows = statement.executeUpdate();
            System.out.println(affectedRows);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return (affectedRows > 0);
    }
}
