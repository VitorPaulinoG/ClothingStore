package com.vitorpg.clothingstore.repositories.interfaces;

public interface UpdaterDao<T> {
    boolean update(Long id, T entity);
}
