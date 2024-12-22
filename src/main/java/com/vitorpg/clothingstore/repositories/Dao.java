package com.vitorpg.clothingstore.repositories;

import java.util.List;

public interface Dao<T> {
    T findById(Long id);
    List<T> findAll();
    boolean save(T entity);
    boolean update(Long id, T entity);
    boolean delete(Long id);
}
