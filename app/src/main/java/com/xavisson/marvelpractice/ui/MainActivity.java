package com.xavisson.marvelpractice.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xavisson.marvelpractice.R;
import com.xavisson.marvelpractice.model.Comic;
import com.xavisson.marvelpractice.model.Result;
import com.xavisson.marvelpractice.net.FetchComicsTask;
import com.xavisson.marvelpractice.ui.adapter.ComicsAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COMIC = "extraComic";

    private static final String LOG_TAG = "MainActivity";
    private static final int COLUMN_NUMBER_PORTRAIT = 2;
    private static final int COLUMN_NUMBER_LANDSCAPE = 3;

    private Comic comicQueryResult = new Comic();
    private List<Result> resultList = new ArrayList<>();
    private RecyclerView comicsRecycler;
    private ComicsAdapter comicsAdapter;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        getPopularComics();
    }

    private void initViews() {

        comicsRecycler = findViewById(R.id.recyclerView);
        comicsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getPopularComics() {

        String sortBy = "popular";
        new FetchComicsTask(new FetchComicsTask.AsyncComicsResponse() {
            @Override
            public void taskPostExecute(String moviesData) {
                listComics(moviesData);
            }
        }).execute(sortBy);
    }

    public void listComics(String comicsData) {

        comicQueryResult = gson.fromJson(comicsData, Comic.class);
        resultList = comicQueryResult.getData().getResults();
        displayComics();
    }

    private void displayComics() {

        initRecycler();
        comicsAdapter.notifyDataSetChanged();
    }

    private void initRecycler() {

        comicsAdapter = new ComicsAdapter(MainActivity.this, resultList);
        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(getApplicationContext(), COLUMN_NUMBER_PORTRAIT);
        }
        else{
            layoutManager = new GridLayoutManager(getApplicationContext(), COLUMN_NUMBER_LANDSCAPE);
        }

        comicsRecycler.setLayoutManager(layoutManager);
        comicsRecycler.setHasFixedSize(true);
        comicsRecycler.setAdapter(comicsAdapter);
        comicsAdapter.setOnItemClickListener(new ComicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(MainActivity.this, ComicDetailActivity.class);
                intent.putExtra(EXTRA_COMIC, resultList.get(position));
                startActivity(intent);
            }
        });
    }
}
