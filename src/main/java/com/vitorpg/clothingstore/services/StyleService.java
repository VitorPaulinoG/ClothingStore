package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.Style;
import com.vitorpg.clothingstore.repositories.StyleDao;
import com.vitorpg.clothingstore.repositories.interfaces.Dao;

public class StyleService extends GenericEntityService<Style, StyleDao> {

    public StyleService() {
        super(new StyleDao());
    }
}
