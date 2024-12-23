package com.vitorpg.clothingstore.repositories.interfaces;

public interface SimpleSaverDao<T>{
    boolean save(T entity);
}
