package com.theironyard.controllers;


import com.theironyard.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addUser(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
    // user login
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public void login(@RequestBody Map<String, String> json) {
        System.out.println(json);
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
