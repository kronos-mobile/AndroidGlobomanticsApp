package com.example.alexr.ideamanager.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MessageService {

    @GET("messages")
    Call<String> getMessages();
}
