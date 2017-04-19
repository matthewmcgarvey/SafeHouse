package com.theironyard.controllers;

import com.theironyard.entities.House;
import com.theironyard.entities.Response;
import com.theironyard.entities.User;
import com.theironyard.entities.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/users/{userId}/houses")
@CrossOrigin
public class HousesController {

    @Autowired
    private Users users;

    // add a new house
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<?> addHouse(@PathVariable Integer userId,
                                      @RequestBody Map<String, String> json) {
        String houseName = json.get("houseName");

        Response response = users.addHouse(userId, houseName);

        if (response.wasError) {
            return new ResponseEntity<>(response.errorMessage, response.status);
        } else {
            return new ResponseEntity<>(response.body, response.status);
        }
    }

    // get all of a user's houses
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getHouses(@PathVariable Integer userId) {
        User user = users.findOne(userId);
        if (user != null) {
            return new ResponseEntity<>(user.getHouses(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No houses found by that user.", HttpStatus.BAD_REQUEST);
        }
    }

    // get a user's house
    @RequestMapping(path = "/{houseId}", method = RequestMethod.GET)
    public ResponseEntity<?> getHouse(@PathVariable Integer userId,
                                      @PathVariable Integer houseId) {
        User user = users.findOne(userId);
        String errorMsg;
        if (user != null) {
            House house = user.getHouses().stream().filter(h -> h.getId() == houseId).findFirst().orElse(null);
            if (house != null) {
                return new ResponseEntity<>(house, HttpStatus.OK);
            } else {
                errorMsg = "Unable to find house by that id.";
            }
        } else {
            errorMsg = "Unable to find user by that id.";
        }
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }

    // delete a user's house along with any items the house had
    @RequestMapping(path = "/{houseId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteHouse(@PathVariable Integer userId,
                                         @PathVariable Integer houseId) {
        Boolean success = users.deleteHouse(userId, houseId);
        if (success) return new ResponseEntity<>("Success", HttpStatus.OK);
        else return new ResponseEntity<>("Unable to delete house.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // update a user's house's name
    @RequestMapping(path = "/{houseId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateHouseName(@PathVariable Integer userId,
                                             @PathVariable Integer houseId,
                                             @RequestBody Map<String, String> json) {
        String newName = json.get("newName");
        if (newName != null && !newName.isEmpty()) {
            users.updateHouseName(userId, houseId, newName);
            return new ResponseEntity<>("Success.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to update house name.", HttpStatus.BAD_REQUEST);
        }
    }
}
