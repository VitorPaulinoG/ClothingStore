package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Color;
import com.vitorpg.clothingstore.repositories.ColorDao;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

public class ColorService extends GenericEntityService<Color, ColorDao>{

    public ColorService() {
        super(new ColorDao());
    }
}
