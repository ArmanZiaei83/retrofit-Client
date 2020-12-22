package com.example.ohhttp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @PATCH("posts/{id}")
    Call<Post> updatePost(@Path("id") int id , @Body Post post);
}
