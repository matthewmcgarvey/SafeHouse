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

    @RequestMapping(path = "/add-user", method = RequestMethod.POST)
    public void addUser(@RequestBody Map<String, String> json) {
        System.out.println(json);
    }
}
