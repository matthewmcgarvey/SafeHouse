package com.theironyard.api;

import com.theironyard.entities.Item;
import com.theironyard.entities.Recall;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class RecallAPI {
    public Integer recallNumber;
    public String recallURL;
    public String recallTitle;
    public String recallDescription;
    public String productName;
    public String productDescription;
    public String productModel;
    public String UPC;

    private StringBuffer url = new StringBuffer("https://www.saferproducts.gov/RestWebServices/Recall?");

    public JSONArray submit() {
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
        ResponseEntity<String> response = restTemplate.getForEntity(recallUrl, String.class);
        return new JSONArray(response.getBody());
    }

    public static List<Recall> checkRecall(Item item) {
        JSONArray recallResults;

        RecallAPI recallItem = new RecallAPI();
        if ((!item.getModel().equals("N/A")) && (!item.getBrand().equals("N/A"))) {
            recallItem.productName = item.getBrand() + " " + item.getModel();
        } else {
            recallItem.productName = item.getTitle();
        }

        recallResults = recallItem.submit();

        if (recallResults.length() > 0) {

            List<Recall> recallItems = new ArrayList<>();

            for (int i = 0; i < recallResults.length(); i++) {
                JSONObject recall = (JSONObject) recallResults.get(i);

                Integer recallId;
                Integer recallNumb;
                String recallDate;
                String recallDescription;
                String url;
                String title;

                recallId = recall.getInt("RecallID");
                recallNumb = recall.getInt("RecallNumber");
                recallDate = recall.getString("RecallDate");
                recallDescription = recall.getString("Description");
                url = recall.getString("URL");
                title = recall.getString("Title");


                Recall recallItemResult = new Recall(recallId, recallNumb, recallDate, recallDescription, url, title);
                recallItems.add(recallItemResult);
            }
            return recallItems;
        }
        return null;
    }

}
