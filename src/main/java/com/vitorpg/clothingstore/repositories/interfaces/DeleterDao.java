package com.vitorpg.clothingstore.repositories.interfaces;

public interface DeleterDao<T>{

    boolean delete(Long id);
}
