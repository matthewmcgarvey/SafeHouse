package com.theironyard.entities;

import com.theironyard.api.AmazonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class SearchItem {

    public String title;

    public String brand;

    public String model;

    public String upc;

    public String imageUrl;

    public String asin;

    public SearchItem(String title, String brand, String model, String upc, String imageUrl, String asin) {
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.upc = upc;
        this.imageUrl = imageUrl;
        this.asin = asin;
    }

    public static ResponseEntity<?> lookUpItem(String keywords, String category, String page) {
        String amazonResults = AmazonUtil.lookUpItem(keywords, category, page);
        System.out.println(amazonResults);

        try {

            while (amazonResults.startsWith("{")) {
                JSONObject json = new JSONObject(amazonResults);
                JSONArray itemArray = json.getJSONObject("ItemSearchResponse")
                        .getJSONObject("Items")
                        .getJSONArray("Item");

                List<SearchItem> searchItemResults = new ArrayList<>();

                for (int i = 0; i < itemArray.length(); i++) {

                    JSONObject item = (JSONObject) itemArray.get(i);
                    String brand;
                    String model;
                    String upc;
                    String imageUrl;
                    String asin;

                    String title = item.getJSONObject("ItemAttributes").getString("Title");

                    try {
                        asin = item.getString("ASIN");
                    } catch (Exception asinTypeInt) {
                        Integer asinInt = item.getInt("ASIN");
                        asin = asinInt.toString();
                    }


                    try {
                        brand = item.getJSONObject("ItemAttributes").getString("Brand");
                    } catch (Exception noBrand) {
                        brand = "N/A";
                    }

                    try {
                        model = item.getJSONObject("ItemAttributes").getString("Model");
                    } catch (Exception noModel) {
                        model = "N/A";
                    }

                    try {
                        BigInteger upcInt = item.getJSONObject("ItemAttributes").getBigInteger("UPC");
                        upc = upcInt.toString();
                    } catch (Exception noUpc) {
                        upc = "000000000000";
                    }

                    try {
                        imageUrl = item.getJSONObject("LargeImage").getString("URL");
                    } catch (Exception noLrgImg) {
                        try {
                            imageUrl = item.getJSONObject("ImageSets")
                                    .getJSONObject("ImageSet")
                                    .getJSONObject("LargeImage")
                                    .getString("URL");

                        } catch (Exception noImgSetObject) {
                            imageUrl = item.getJSONObject("ImageSets")
                                    .getJSONArray("ImageSet")
                                    .getJSONObject(0)
                                    .getJSONObject("LargeImage")
                                    .getString("URL");
                        }
                    }

                    SearchItem searchItem = new SearchItem(title, brand, model, upc, imageUrl, asin);
                    searchItemResults.add(searchItem);
                }

                return new ResponseEntity<>(searchItemResults, HttpStatus.OK);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("There is a problem with the search request. Invalid results.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("An unexpected error occurred. " + amazonResults, HttpStatus.EXPECTATION_FAILED);
    }
}



