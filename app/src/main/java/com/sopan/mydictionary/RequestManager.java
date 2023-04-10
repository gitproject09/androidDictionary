package com.sopan.mydictionary;

import android.content.Context;
import android.widget.Toast;

import com.sopan.mydictionary.Models.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RequestManager {
    Context context;

    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.dictionaryapi.dev/api/v2/").addConverterFactory(GsonConverterFactory.create()).build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getWordMeaning(OnFetchDataListener listener, String word) {
        CallDictionary callDictionary = retrofit.create(CallDictionary.class);
        Call<List<ApiResponse>> call = callDictionary.callMeanings(word);

        try {
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<List<ApiResponse>> call, Response<List<ApiResponse>> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(context, "Error !!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    listener.onFetchData(response.body().get(0), response.message());
                }

                @Override
                public void onFailure(Call<List<ApiResponse>> call, Throwable t) {
                    listener.onError("Request Failed !");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "An Error Occurred !", Toast.LENGTH_SHORT).show();
        }
    }

    public interface CallDictionary {
        @GET("entries/en/{word}")
        Call<List<ApiResponse>> callMeanings(@Path("word") String word);
    }
}
