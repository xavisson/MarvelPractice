package com.xavisson.marvelpractice.net;

import android.os.AsyncTask;
import android.util.Log;

import com.xavisson.marvelpractice.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by javidelpalacio on 22/1/18.
 */

public class FetchComicsTask extends AsyncTask<String, Void, String> {

    private static final String LOG_TAG = "FetchComicsTask";

    public interface AsyncComicsResponse {

        void taskPostExecute(String comicsData);

    }

    public AsyncComicsResponse delegate = null;

    public FetchComicsTask(AsyncComicsResponse asyncResponse) {
        delegate = asyncResponse;
    }

    @Override
    protected String doInBackground(String... strings) {

        if (strings.length == 0) {
            return null;
        }

        String sortBy = strings[0];

        //TODO: Retrofit
        URL comicsRequestUrl = NetworkUtils.buildUrl(sortBy);

        try {
            String jsonComicsResponse = NetworkUtils.getResponseFromHttpUrl(comicsRequestUrl);
            Log.d(LOG_TAG, jsonComicsResponse);
            return jsonComicsResponse;

        } catch (Exception e) {
            Log.d(LOG_TAG, "Exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String comicsData) {
        if (comicsData != null)
            delegate.taskPostExecute(comicsData);
    }
}
