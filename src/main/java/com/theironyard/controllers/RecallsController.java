package com.theironyard.controllers;

import com.theironyard.api.RecallAPI;
import com.theironyard.entities.Item;
import com.theironyard.entities.Items;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/recalls")
@CrossOrigin
public class RecallsController {

    @Autowired
    private Items items;

    // Get recall for an item
    @RequestMapping(path = "/{itemId}", method = RequestMethod.GET)
    public ResponseEntity<?> searchRecall(@PathVariable Integer itemId) {
        Item item = items.findItemById(itemId);
        return new ResponseEntity<>(RecallAPI.checkRecall(item), HttpStatus.OK);
    }
}
