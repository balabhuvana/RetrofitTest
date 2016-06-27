package com.example.retrofittest;

import java.util.*;
import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by 3542 on 6/20/2016.
 */
public interface HttpRequestHandler {
    @GET("/jayaraj")
    public Call<List<Book>> listBooks();
}
