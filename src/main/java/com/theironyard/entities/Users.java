package com.theironyard.entities;

import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public final class Users {
    @Autowired
    UserRepository userRepo;

    public void save(User user) throws DataIntegrityViolationException {
        userRepo.save(user);
    }

    public User findByName(String name) {
        return userRepo.findByName(name);
    }

    public User findOne(Integer id) {
        return userRepo.findOne(id);
    }
}
