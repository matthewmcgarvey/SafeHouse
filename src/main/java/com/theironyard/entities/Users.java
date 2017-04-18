package com.theironyard.entities;

import com.theironyard.services.HouseRepository;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public final class Users {

    private UserRepository userRepo;
    private HouseRepository houseRepo;

    @Autowired
    Items items;

    @Autowired
    public Users(UserRepository userRepo, HouseRepository houseRepo) {
        this.userRepo = userRepo;
        this.houseRepo = houseRepo;
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

    public Boolean deleteHouse(Integer userId, Integer houseId) {
        User user = userRepo.findOne(userId);
        if (user != null) {
            House house = user.getHouses().stream().filter(h -> h.getId() == houseId).findFirst().orElse(null);
            if (house != null) {
                user.getHouses().remove(house);
                if (house.getDefaultHouse() && user.getHouses().size() >= 1) {
                    user.getHouses().get(0).setDefaultHouse(true);
                }
                userRepo.save(user);
                houseRepo.delete(houseId);
                items.deleteByHouseId(houseId); // remove the household items that were in the house
                return true;
            }
        }
        return false;
    }

    public void updateHouseName(Integer userId, Integer houseId, String newName) {
        User user = userRepo.findOne(userId);
        if (user != null && user.getHouses().size() > 0) {
            House house = user.getHouses().stream().filter(h -> h.getId() == houseId).findFirst().orElse(null);
            if (house != null && !house.getName().equals(newName)) {
                house.setName(newName);
                userRepo.save(user);
            }
        }
    }
}
