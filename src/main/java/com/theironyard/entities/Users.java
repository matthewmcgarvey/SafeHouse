package com.theironyard.entities;

import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public final class Users {

    private UserRepository userRepo;

    @Autowired
    Items items;

    @Autowired
    public Users(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public void save(User user) throws DataIntegrityViolationException {
        userRepo.save(user);
    }

    public User findByName(String name) {
        return userRepo.findByName(name);
    }

    public User findOne(Integer userId) {
        return userRepo.findOne(userId);
    }

    public void deleteHouse(Integer userId, Integer houseId) {
        User user = userRepo.findOne(userId);
        if (user != null) {
            Boolean success = user.getHouses().removeIf(house -> house.getId() == houseId);
            if (success) {
                items.deleteByHouseId(houseId); // remove the household items that were in the house
            }
        }
    }
}
