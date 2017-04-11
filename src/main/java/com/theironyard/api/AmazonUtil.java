package com.theironyard.api;

import java.util.HashMap;
import java.util.Map;

public class AmazonUtil {

    private static final String AWS_ACCESS_KEY_ID = "ENTER KEY HERE";
    private static final String AWS_SECRET_KEY = "ENTER SECRET KEY HERE";
    private static final String ENDPOINT = "ecs.amazonaws.com";

    public static String lookupItem(String keywords, String searchCategory){

        SignedRequestsHelper helper;

        try {
            helper = SignedRequestsHelper.getInstance(ENDPOINT, AWS_ACCESS_KEY_ID, AWS_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        if (searchCategory.isEmpty()) {
            searchCategory = "All";
        }

        //Load search parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("Service", "AWSECommerceService");
        params.put("Version", "2009-03-31");
        params.put("SearchIndex", searchCategory);
        params.put("Operation", "ItemSearch");
        params.put("ResponseGroup", "Medium");
        params.put("Keywords", keywords); //Keyword(s) entered by user
        params.put("AssociateTag","safe0cd-20");

        return helper.sign(params);
    }

}
