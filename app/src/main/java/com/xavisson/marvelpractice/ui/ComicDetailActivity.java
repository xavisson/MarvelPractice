package com.xavisson.marvelpractice.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.xavisson.marvelpractice.R;
import com.xavisson.marvelpractice.model.Date;
import com.xavisson.marvelpractice.model.Result;

import static com.xavisson.marvelpractice.ui.MainActivity.EXTRA_COMIC;

public class ComicDetailActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView comicImage;
    private TextView title;
    private TextView description;
    private TextView releaseDate;
    private TextView price;

    private Result comic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic_detail);

        initViews();
        setupToolbar();

        if (getIntent().hasExtra(EXTRA_COMIC))
            comic = (Result) getIntent().getSerializableExtra(EXTRA_COMIC);

        if (null != comic) {
            fillComicDetails();
        }
    }

    private void initViews() {

        toolbar = findViewById(R.id.toolbar);
        comicImage = findViewById(R.id.detail_backdrop);
        title = findViewById(R.id.comic_detail_title);
        description = findViewById(R.id.comic_detail_description);
        releaseDate = findViewById(R.id.comic_detail_release_date);
        price = findViewById(R.id.comic_detail_price);
    }

    private void fillComicDetails() {

        String imageURL = comic.getThumbnail().getPath() + "." + comic.getThumbnail().getExtension();
        Date date = comic.getDates().get(0);
        String priceString = String.valueOf(comic.getPrices().get(0).getPrice()) + "$";

        Picasso.with(this).load(imageURL).into(comicImage);
        title.setText(comic.getTitle());
        description.setText(comic.getDescription().toString());
        releaseDate.setText(date.getDate());
        price.setText(priceString);

        if (toolbar != null)
            getSupportActionBar().setTitle(comic.getTitle());
    }

    private void setupToolbar() {

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setElevation(7);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
