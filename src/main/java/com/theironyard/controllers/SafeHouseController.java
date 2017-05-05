package com.theironyard.controllers;

import com.theironyard.api.RecallAPI;
import com.theironyard.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
public class SafeHouseController {

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

    @RequestMapping(path = "/ping", method = RequestMethod.GET)
    public void wakeUp() {}
}
