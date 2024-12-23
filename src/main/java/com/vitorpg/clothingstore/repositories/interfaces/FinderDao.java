package com.vitorpg.clothingstore.repositories.interfaces;

import java.util.List;

public interface FinderDao<T> {
    T findById(Long id);
    List<T> findAll();
}
