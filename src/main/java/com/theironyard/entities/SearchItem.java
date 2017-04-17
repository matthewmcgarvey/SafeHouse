package com.theironyard.entities;

import com.theironyard.api.AmazonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class SearchItem {

    public String title;

    public String brand;

    public String model;

    public BigInteger upc;

    public String imageUrl;

    public String asin;

    public SearchItem(String title, String brand, String model, BigInteger upc, String imageUrl, String asin) {
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.upc = upc;
        this.imageUrl = imageUrl;
        this.asin = asin;
    }

    public static ResponseEntity<?> lookUpItem(String keywords, String category, String page) {
        String amazonResults = AmazonUtil.lookUpItem(keywords, category, page);

        try {

            URL url = new URL(amazonResults);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String strTemp;

            while (null != (strTemp = br.readLine())) {
                JSONObject xmlJSONObj = XML.toJSONObject(strTemp);
                JSONArray itemArray = xmlJSONObj.getJSONObject("ItemSearchResponse")
                        .getJSONObject("Items")
                        .getJSONArray("Item");

                List<SearchItem> searchItemResults = new ArrayList<>();

                for (int i = 0; i < itemArray.length(); i++) {

                    JSONObject item = (JSONObject) itemArray.get(i);
                    String brand;
                    String model;
                    BigInteger upc;
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
                        brand = "Brand not available";
                    }

                    try {
                        model = item.getJSONObject("ItemAttributes").getString("Model");
                    } catch (Exception noModel) {
                        model = "Model not available";
                    }

                    try {
                        upc = item.getJSONObject("ItemAttributes").getBigInteger("UPC");
                    } catch (Exception noUpc) {
                        upc = BigInteger.valueOf(0);
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

                    SearchItem searchItem = new SearchItem(title, brand , model, upc, imageUrl, asin);
                    searchItemResults.add(searchItem);
                }

                return new ResponseEntity<>(searchItemResults, HttpStatus.OK);
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            return new ResponseEntity<>("Problem with the search request", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("An unexpected error occurred", HttpStatus.EXPECTATION_FAILED);
    }









//        return AmazonUtil.lookUpItem(keywords, category, page);
    }



