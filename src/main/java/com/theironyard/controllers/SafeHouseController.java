package com.theironyard.controllers;


import com.theironyard.entities.User;
import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.util.Map;

@RestController
public class SafeHouseController {

    @Autowired
    UserRepository users;

    // register a new user
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String password = json.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if ((username != null && !username.isEmpty()) && (password != null && !password.isEmpty())) {
            if (users.findOneByName(username) == null) {
                User user = new User(username, password);
                users.save(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Invalid username or password.", HttpStatus.BAD_REQUEST);
    }
    
    // user login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody Map<String, String> json) {
        String username = json.get("username");
        String password = json.get("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        if ((username != null && !username.isEmpty()) && (password != null && !password.isEmpty())) {
            User user = users.findOneByName(username);
            if (user != null) {
                if (user.verifyPassword(password)) {
                    return new ResponseEntity<>(user, HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>("Bad login.", HttpStatus.UNAUTHORIZED);

    }
    // user logout
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // get user
    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public void getUser(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // get house
    @RequestMapping(path = "/house", method = RequestMethod.GET)
    public void getHouse(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // add a new house
    @RequestMapping(path = "/house", method = RequestMethod.POST)
    public void addHouse(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // remove a house
    @RequestMapping(path = "/house", method = RequestMethod.DELETE)
    public void deleteHouse(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // add item to house
    @RequestMapping(path = "/item", method = RequestMethod.POST)
    public void addItem(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // remove item from house
    @RequestMapping(path = "/item", method = RequestMethod.DELETE)
    public void deleteItem(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // get items from search
    @RequestMapping(path = "/items", method = RequestMethod.GET)
    public void getItems(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
}
