package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Material;
import com.vitorpg.clothingstore.repositories.MaterialDao;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

public class MaterialService extends GenericEntityService<Material, MaterialDao>{

    public MaterialService() {
        super(new MaterialDao());
    }
}
