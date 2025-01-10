package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.exceptions.EntityNotFoundException;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

import java.util.List;

public abstract class GenericEntityService<T, D extends Dao<T>> {
    protected final D dao;

    public GenericEntityService(D dao) {
        this.dao = dao;
    }

    public T findById(Long id) {
        var entity = dao.findById(id);
        if (entity == null)
            throw new EntityNotFoundException("entity not found with id = " + id);
        return entity;
    }

    public List<T> findAll() {
        return dao.findAll();
    }

    public void save(T entity) {
        dao.save(entity);
    }

    public void update(Long id, T entity) {
        dao.update(id, entity);
    }

    public void delete(Long id) {
        dao.delete(id);
    }
}
