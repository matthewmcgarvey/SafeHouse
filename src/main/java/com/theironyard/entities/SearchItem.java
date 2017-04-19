package com.theironyard.entities;

import com.theironyard.api.AmazonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class SearchItem {

    private String title;

    private String brand;

    private String model;

    private String upc;

    private String imageUrl;

    private String asin;

    private SearchItem(String title, String brand, String model, String upc, String imageUrl, String asin) {
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.upc = upc;
        this.imageUrl = imageUrl;
        this.asin = asin;
    }

    public static Response lookUpItem(String keywords, String category, String page) {
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

                return new Response(searchItemResults);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return new Response("There is a problem with the search request. Invalid results.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new Response("An unexpected error occurred. " + amazonResults, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }
}



