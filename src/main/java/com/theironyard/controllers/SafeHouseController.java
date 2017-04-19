package com.theironyard.controllers;

import com.theironyard.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
public class SafeHouseController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private Users users;
    @Autowired
    private Items items;

    // register a new user
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String password = json.get("password");
        String password2 = json.get("password2");

        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            return new ResponseEntity<>("Username and password must not be empty.", HttpStatus.BAD_REQUEST);
        }

        if (!password.equals(password2)) {
            return new ResponseEntity<>("Passwords do not match.", HttpStatus.BAD_REQUEST);
        }

        User user = new User(username, bCryptPasswordEncoder.encode(password));

        try {
            users.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        }
    }

    // view current users
    @RequestMapping(path = "/users/current", method = RequestMethod.GET)
    public ResponseEntity<?> currentUser() {
        Authentication u = SecurityContextHolder.getContext().getAuthentication();
        String name = u.getName();
        User user = users.findByName(name);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<Object>("Invalid token.", HttpStatus.BAD_REQUEST);
    }

    // get user
    @RequestMapping(path = "/users/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable Integer userId) {
        User user = users.findOne(userId);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to find user", HttpStatus.BAD_REQUEST);
    }

    // add a new house
    @RequestMapping(path = "/users/{userId}/houses", method = RequestMethod.POST)
    public ResponseEntity<?> addHouse(@PathVariable Integer userId,
                                   @RequestBody Map<String, String> json) {
        String houseName = json.get("houseName");

        User user = users.findOne(userId);
        if (user != null) {
            House house = new House(houseName);
            if (user.getHouses().size() == 0) {
                house.setDefaultHouse(true);
            }
            user.addHouse(house);
            users.save(user);
            return new ResponseEntity<>(house, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to find user", HttpStatus.BAD_REQUEST);
        }
    }

    // get all of a user's houses
    @RequestMapping(path = "/users/{userId}/houses", method = RequestMethod.GET)
    public ResponseEntity<?> getHouses(@PathVariable Integer userId) {
        User user = users.findOne(userId);
        if (user != null) {
            return new ResponseEntity<>(user.getHouses(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No houses found by that user.", HttpStatus.BAD_REQUEST);
        }
    }

    // get a user's house
    @RequestMapping(path = "/users/{userId}houses/{houseId}", method = RequestMethod.GET)
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
    @RequestMapping(path = "/users/{userId}/houses/{houseId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteHouse(@PathVariable Integer userId,
                            @PathVariable Integer houseId) {
        Boolean success = users.deleteHouse(userId, houseId);
        if (success) return new ResponseEntity<>("Success", HttpStatus.OK);
        else return new ResponseEntity<>("Unable to delete house.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // update a user's house's name
    @RequestMapping(path = "/users/{userId}/houses/{houseId}", method = RequestMethod.PATCH)
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

    // get items from a user's house
    @RequestMapping(path = "/users/{userId}/houses/{houseId}/items", method = RequestMethod.GET)
    public ResponseEntity<?> getItems(@PathVariable Integer userId,
                                      @PathVariable Integer houseId) {
        List<HouseHoldItem> houseItems = items.findByHouseId(houseId);
        if (houseItems != null) {
            return new ResponseEntity<>(houseItems, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to find items for that house.", HttpStatus.BAD_REQUEST);
        }
    }

    // add an item to a house
    @RequestMapping(path = "/users/{userId}/houses/{houseId}/items", method = RequestMethod.POST)
    public ResponseEntity<?> addItem(@PathVariable Integer userId,
                        @PathVariable Integer houseId,
                        @RequestBody Map<String, String> json) {
        String title = json.get("title");
        String brand = json.get("brand");
        String model = json.get("model");
        String upc = json.get("upc");
        String asin = json.get("asin");
        String imageUrl = json.get("imageUrl");

        Item item = items.findByAsin(asin);


        if (item == null) {
            item = new Item(title, brand, model, upc, asin, imageUrl);
        } else {
            HouseHoldItem hhitem = items.findHhItemByHouseIdAndItem_Id(houseId, item.getId());
            if (hhitem != null) return new ResponseEntity<>("Item already in house.", HttpStatus.BAD_REQUEST);
        }
        HouseHoldItem hhItem = new HouseHoldItem(houseId, item);
        items.save(hhItem);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // remove an item from a house
    @RequestMapping(path = "/users/{userId}/houses/{houseId}/items/{itemId}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Integer userId,
                           @PathVariable Integer houseId,
                           @PathVariable Integer itemId) {
        items.deleteByHouseIdAndItem_Id(houseId, itemId);
    }

    // move item from one user's house to another house of that user
    @RequestMapping(path = "/users/{userId}/houses/{houseId}/items/{itemId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> moveItem(@PathVariable Integer userId,
                         @PathVariable Integer houseId,
                         @PathVariable Integer itemId,
                         @RequestBody Map<String, String> json) {
        Integer toHouseId = Integer.valueOf(json.get("houseId"));
        Boolean success = false;
        if (toHouseId != null) {
            User user = users.findOne(userId);
            if (user != null) {
                List<House> houses = user
                        .getHouses()
                        .stream()
                        .filter(house -> (house.getId() == houseId) || (house.getId() == toHouseId))
                        .collect(Collectors.toList());
                if (houses.size() == 2) {
                    success = items.updateHhItemHouseId(itemId, houseId, toHouseId);
                }
            }
        }

        if (success) {
            return new ResponseEntity<>("Success.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error in moving item.", HttpStatus.BAD_REQUEST);
        }
    }

    // Search Amazon Product API
    @RequestMapping(path = "/items/{category}/{keywords}/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchItems(@PathVariable String keywords,
                                         @PathVariable String category,
                                         @PathVariable String page) {
        Response response = SearchItem.lookUpItem(keywords, category, page);
        if (response.wasError) {
            return new ResponseEntity<>(response.errorMessage, response.status);
        }
        return new ResponseEntity<>(response.body, HttpStatus.OK);
    }
}
