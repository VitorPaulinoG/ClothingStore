package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.models.User;
import com.vitorpg.clothingstore.repositories.UserDao;

public class UserService extends GenericEntityService<User, UserDao>{
    public UserService() {
        super(new UserDao());
    }
}
