package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.exceptions.EntityNotFoundException;
import com.vitorpg.clothingstore.models.Size;
import com.vitorpg.clothingstore.models.enums.SizeType;
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

    public List<Size> findAllBySizeType(SizeType sizeType) {
        return this.dao.findAllBySizeType(sizeType);
    }

    public List<Size> findAll() {
        return this.dao.findAll();
    }

    public void save(Size size, Long categoryId) {
        this.dao.save(size);
    }

    public void update(Long id, Size size) {
        this.dao.update(id, size);
    }

    public void delete(Long id) {
        this.dao.delete(id);
    }
}
