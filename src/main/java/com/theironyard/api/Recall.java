package com.theironyard.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class Recall {
    public Integer recallNumber;
    public String recallURL;
    public String recallTitle;
    public String recallDescription;
    public String productName;
    public String productDescription;
    public String productModel;
    public String UPC;

    private StringBuffer url = new StringBuffer("https://www.saferproducts.gov/RestWebServices/Recall?");

    public List<Map<String, Object>> submit() {
        if (recallNumber != null) {
            url.append("RecallNumber=");
            url.append(recallNumber);
            url.append("&");
        }
        if (recallURL != null) {
            url.append("RecallUrl=");
            url.append(recallURL);
            url.append("&");
        }
        if (recallTitle != null) {
            url.append("RecallTitle=");
            url.append(recallTitle);
            url.append("&");
        }
        if (recallDescription != null) {
            url.append("RecallDescription=");
            url.append(recallDescription);
            url.append("&");
        }
        if (productName != null) {
            url.append("ProductName=");
            url.append(productName);
            url.append("&");
        }
        if (productDescription != null) {
            url.append("ProductDescription=");
            url.append(productDescription);
            url.append("&");
        }
        if (productModel != null) {
            url.append("ProductModel=");
            url.append(productModel);
            url.append("&");
        }
        if (UPC != null) {
            url.append("UPC=");
            url.append(UPC);
            url.append("&");
        }

        url.append("format=json"); // for json formatting
        String recallUrl = url.toString();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List> response = restTemplate.getForEntity(recallUrl, List.class);
        return response.getBody();
    }

}
