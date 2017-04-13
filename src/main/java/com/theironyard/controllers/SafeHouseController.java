package com.theironyard.controllers;


import com.theironyard.entities.House;
import com.theironyard.entities.Item;
import com.theironyard.entities.User;
import com.theironyard.services.HouseRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.api.AmazonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin
@RestController
public class SafeHouseController {

    @Autowired
    private UserRepository users;
    @Autowired
    private HouseRepository houses;

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

        User user = new User(username, password);

        try {
            users.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Problem", HttpStatus.BAD_REQUEST);
        }
    }

    // get user
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        User user = users.findOne(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to find user", HttpStatus.BAD_REQUEST);
    }

    // user login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String password = json.get("password");

        System.out.println(username + ": " + password);

        if ((username != null && !username.isEmpty()) && 
                (password != null && !password.isEmpty())) {
            User user = users.findOneByName(username);
            if (user != null) {
                if (user.verifyPassword(password)) {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("Bad login.", HttpStatus.UNAUTHORIZED);
    }

    // add a new house
    @RequestMapping(path = "/houses", method = RequestMethod.POST)
    public ResponseEntity addHouse(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String houseName = json.get("houseName");

        User user = users.findOneByName(username);
        if (user != null) {
            House house = new House(houseName, user);
            houses.save(house);
            return new ResponseEntity<>(house, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Unable to find user", HttpStatus.BAD_REQUEST);
        }
    }

    // get house
    @RequestMapping(path = "/houses/{houseId}", method = RequestMethod.GET)
    public ResponseEntity<?> getHouse(@PathVariable Integer houseId) {
        House house = houses.findOne(houseId);

        if (house != null) {
            return new ResponseEntity<>(house, HttpStatus.OK);
        }
        return new ResponseEntity<>("Unable to find house", HttpStatus.BAD_REQUEST);
    }

    // remove a house
    @RequestMapping(path = "/houses/{houseId}", method = RequestMethod.DELETE)
    public void deleteHouse(@PathVariable Integer houseId) {
        houses.delete(houseId);
    }

    // add item to house Todo
    @RequestMapping(path = "/item", method = RequestMethod.POST)
    public void addItem(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String houseName = json.get("houseName");
        String itemName = json.get("itemName");

        House house = houses.findOneByNameAndUser_Name(houseName, username);
        System.out.println(house.getName());
        house.addItem(new Item(itemName));
        houses.save(house);
    }

    // remove item from house Todo
    @RequestMapping(path = "/item/{itemId}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Integer itemId) {
    }

    //Search Amazon Product API ToDo
    @RequestMapping(path = "/items/{category}/{keywords}", method = RequestMethod.GET)
    public ResponseEntity<?> searchItems(@PathVariable String keywords, @PathVariable String category) throws Exception {
        return AmazonUtil.lookupItem(keywords, category);
    }
}
