package com.xavisson.marvelpractice.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.xavisson.marvelpractice.BuildConfig;
import com.xavisson.marvelpractice.R;
import com.xavisson.marvelpractice.component.DaggerMarvelApiComponent;
import com.xavisson.marvelpractice.component.MarvelApiComponent;
import com.xavisson.marvelpractice.model.ComicQuery;
import com.xavisson.marvelpractice.model.Result;
import com.xavisson.marvelpractice.module.ContextModule;
import com.xavisson.marvelpractice.net.MarvelApi;
import com.xavisson.marvelpractice.ui.adapter.ComicsAdapter;
import com.xavisson.marvelpractice.utilities.Tools;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_COMIC = "extraComic";

    private static final String LOG_TAG = "MainActivity";
    private static final int COLUMN_NUMBER_PORTRAIT = 2;
    private static final int COLUMN_NUMBER_LANDSCAPE = 3;

    private List<Result> resultList = new ArrayList<>();
    private RecyclerView comicsRecycler;
    private ComicsAdapter comicsAdapter;

    private MarvelApi marvelApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initComponents();
        getComics();
    }

    private void initViews() {

        comicsRecycler = findViewById(R.id.recyclerView);
        comicsRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initComponents() {

        MarvelApiComponent daggerMarvelApiComponent = DaggerMarvelApiComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
        marvelApi = daggerMarvelApiComponent.getMarvelApi();
    }

    private void getComics() {

        String publicAPIKey = BuildConfig.MARVEL_PUBLIC_KEY;
        String privateAPIKey = BuildConfig.MARVEL_PRIVATE_KEY;
        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String hash = Tools.md5(ts + privateAPIKey + publicAPIKey);
        String order = "title";

        Call<ComicQuery> call = marvelApi.getComics(order, publicAPIKey, hash, ts);
        call.enqueue(new Callback<ComicQuery>() {
            @Override
            public void onResponse(Call<ComicQuery> call, Response<ComicQuery> response) {
                resultList = response.body().getData().getResults();
                displayComics();
            }

            @Override
            public void onFailure(Call<ComicQuery> call, Throwable t) {
                Toast.makeText(MainActivity.this, getString(R.string.something_wrong),
                        Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, "Couldn't retrieve comics: " + t.getCause());
            }
        });
    }

    private void displayComics() {
        initRecycler();
        comicsAdapter.notifyDataSetChanged();
    }

    private void initRecycler() {

        comicsAdapter = new ComicsAdapter(MainActivity.this, resultList);
        RecyclerView.LayoutManager layoutManager;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            layoutManager = new GridLayoutManager(getApplicationContext(), COLUMN_NUMBER_PORTRAIT);
        else
            layoutManager = new GridLayoutManager(getApplicationContext(), COLUMN_NUMBER_LANDSCAPE);

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
