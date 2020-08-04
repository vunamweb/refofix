package com.example.nvu7.myapplication.remote;

/**
 * Created by nvu7 on 11/8/2017.
 */

import com.example.nvu7.myapplication.model.SOAnswersResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SOService {

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers();

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<SOAnswersResponse> getAnswers(@Query("tagged") String tags);
}
