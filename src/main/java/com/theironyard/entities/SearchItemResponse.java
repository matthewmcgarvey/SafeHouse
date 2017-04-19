package com.theironyard.entities;

import java.util.List;

public class SearchItemResponse {
    public List<SearchItem> searchItems;
    public String errorMessage;
    public Boolean wasError;

    public SearchItemResponse(List<SearchItem> searchItems) {
        this.searchItems = searchItems;
        wasError = false;
    }

    public SearchItemResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        wasError = true;
    }
}
