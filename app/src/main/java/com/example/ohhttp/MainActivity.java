package com.example.ohhttp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    JsonPlaceHolderApi jsonPlaceHolderApi;
    TextView textVieW_Result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textVieW_Result = (TextView) findViewById(R.id.textView_Result);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        updatePost();

    }

    public void updatePost(){
        Post post = new Post(1 , "New Title" , null);
        Call<Post> call = jsonPlaceHolderApi.updatePost(5 , post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textVieW_Result.setText(response.code());
                    return;
                }

                Post post1 = response.body();

                String content = "";
                content += "Title : " + post1.getTitle() + "\n" ;
                content += "Body : " + post1.getBody() + "\n" ;
                content += "userId : " + post1.getUserId() + "\n" ;
                content += "Id : " + post1.getId() + "\n\n" ;

                textVieW_Result.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textVieW_Result.setText("Code :" + t.getMessage());
            }
        });
    }
}