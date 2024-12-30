package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.interfaces.EntityNotFoundException;
import com.vitorpg.clothingstore.models.Size;
import com.vitorpg.clothingstore.repositories.SizeDao;

import java.util.List;

public class SizeService {

    private final SizeDao dao;
    public SizeService () {
        this.dao = new SizeDao();
    }

    public Size findById(Long id) {
        Size size = this.dao.findById(id);
        if(size == null)
            throw new EntityNotFoundException("Entity not found by id = " + id);
        return size;
    }

    public List<Size> findAllByCategory(Long categoryId) {
        return this.dao.findAllByCategory(categoryId);
    }

    public List<Size> findAll() {
        return this.dao.findAll();
    }

    public void addIntoCategory(Long sizeId, Long categoryId) {
        this.dao.addIntoCategory(sizeId, categoryId);
    }

    public void save(Size size, Long categoryId) {
        this.dao.save(size, categoryId);
    }

    public void update(Long id, Size size) {
        this.dao.update(id, size);
    }

    public void delete(Long id) {
        this.dao.delete(id);
    }

    public void removeFromCategory(Long sizeId, Long categoryId) {
        this.dao.removeFromCategory(sizeId, categoryId);
    }
}
