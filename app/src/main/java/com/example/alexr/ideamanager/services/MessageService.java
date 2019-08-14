package com.example.alexr.ideamanager.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface MessageService {

    @GET
    Call<String> getMessages(@Url String altUrl);
}
