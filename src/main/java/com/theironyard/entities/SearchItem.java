package com.theironyard.entities;

import com.theironyard.api.AmazonUtil;
import org.springframework.http.ResponseEntity;


public class SearchItem {

    public String title;

    public String brand;

    public String model;

    public int upc;

    public String imageUrl;

    public SearchItem(String title, String brand, String model, int upc, String imageUrl) {
        this.title = title;
        this.brand = brand;
        this.model = model;
        this.upc = upc;
        this.imageUrl = imageUrl;
    }

    public static ResponseEntity<?> lookUpItem(String keywords, String category, String page){
        return AmazonUtil.lookUpItem(keywords, category, page);
    }


}