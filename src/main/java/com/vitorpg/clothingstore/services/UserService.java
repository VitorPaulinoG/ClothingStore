package com.vitorpg.clothingstore.services;

import com.vitorpg.clothingstore.exceptions.EntityNotFoundException;
import com.vitorpg.clothingstore.models.User;
import com.vitorpg.clothingstore.repositories.UserDao;

public class UserService extends GenericEntityService<User, UserDao>{
    public UserService() {
        super(new UserDao());
    }

    @Override
    public void save(User entity) {
        entity.setPassword(PasswordHashing.hashPassword(entity.getPassword()));
        super.save(entity);
    }

    public User findFirstByEmail(String email) {
        return this.dao.findFirstByEmail(email);
    }

    public boolean authenticate (String email, String password) {
        User user = findFirstByEmail(email);
        if(user == null)
            throw new EntityNotFoundException("Nenhum usuário encontrado com o email " + email + "!");
        return PasswordHashing.verifyPassword(password, user.getPassword());
    }
}
