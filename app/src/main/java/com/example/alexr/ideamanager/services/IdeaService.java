package com.example.alexr.ideamanager.services;

import com.example.alexr.ideamanager.models.Idea;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface IdeaService {

    @GET("ideas")
    Call<List<Idea>> getIdeas(@QueryMap HashMap<String, String> filters);

    @GET("ideas/{id}")
    Call<Idea> getIdea(@Path("id") int id);

    @POST("ideas")
    Call<Idea> createIdea(@Body Idea newIdea);

    @FormUrlEncoded
    @PUT("ideas/{id}")
    Call<Idea> updateIdea(
            @Path("id") int idea,
            @Field("name") String name,
            @Field("description") String desc,
            @Field("status") String status,
            @Field("owner") String owner
    );

    @DELETE("ideas/{id}")
    Call<Void> deleteIdea(@Path("id") int id);
}
