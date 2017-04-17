package com.theironyard.entities;

import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

@Component
public final class Users {
    @Autowired
    private static UserRepository userRepo;

    public static void save(User user) throws DataIntegrityViolationException {
        userRepo.save(user);
    }

    public static User findByName(String name) {
        return userRepo.findByName(name);
    }

    public static User findOne(Integer userId) {
        return userRepo.findOne(userId);
    }

    public static void deleteHouse(Integer userId, Integer houseId) {
        User user = userRepo.findOne(userId);
        if (user != null) {
            Boolean success = user.getHouses().removeIf(house -> house.getId() == houseId);
            if (success) {
                Items.deleteByHouseId(houseId); // remove the household items that were in the house
            }
        }
    }
}
