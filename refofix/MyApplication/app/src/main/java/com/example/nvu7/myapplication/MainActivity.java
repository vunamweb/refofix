package com.example.nvu7.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.nvu7.myapplication.adapter.AnswersAdapter;
import com.example.nvu7.myapplication.model.Item;
import com.example.nvu7.myapplication.model.SOAnswersResponse;
import com.example.nvu7.myapplication.remote.ApiUtils;
import com.example.nvu7.myapplication.remote.SOService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;

//import retrofit2.Call;

public class MainActivity extends AppCompatActivity {
    private AnswersAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SOService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getSOService();
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mAdapter = new AnswersAdapter(this, new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                Toast.makeText(MainActivity.this, "Post id is" + id, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        try {
            loadAnswers_();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadAnswers_() throws IOException {
        Response<SOAnswersResponse> response = mService.getAnswers().execute();
        if(response.isSuccessful()) {
            mAdapter.updateAnswers(response.body().getItems());
            Log.d("MainActivity", "posts loaded from API");
        }
    }

    public void loadAnswers() {
        mService.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {

                if(response.isSuccessful()) {
                    mAdapter.updateAnswers(response.body().getItems());
                    Log.d("MainActivity", "posts loaded from API");
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                //Log.d("dd",t.toString());
                //showErrorMessage();
                //Log.d("MainActivity", "error loading from API");
                //dfsfsff();
                //Log.e(TAG, "onFailure: " + t.getMessage());
                t.printStackTrace();
            }
        });
    }
}
