package com.theironyard.controllers;

import com.theironyard.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users/{userId}/houses/{houseId}/items")
@CrossOrigin
public class ItemsController {

    @Autowired
    private Users users;
    @Autowired
    private Items items;

    // get items from a user's house
    @RequestMapping(path = "", method = RequestMethod.GET)
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
    @RequestMapping(path = "", method = RequestMethod.POST)
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
    @RequestMapping(path = "/{itemId}", method = RequestMethod.DELETE)
    public void deleteItem(@PathVariable Integer userId,
                           @PathVariable Integer houseId,
                           @PathVariable Integer itemId) {
        items.deleteByHouseIdAndItem_Id(houseId, itemId);
    }

    // move item from one user's house to another house of that user
    @RequestMapping(path = "/{itemId}", method = RequestMethod.PATCH)
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
}
