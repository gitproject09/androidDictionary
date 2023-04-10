package com.sopan.mydictionary;

import com.sopan.mydictionary.Models.ApiResponse;

public interface OnFetchDataListener {
    void onFetchData(ApiResponse apiResponse, String message);

    void onError(String message);
}
