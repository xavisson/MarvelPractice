package com.xavisson.marvelpractice.utilities;

import android.net.Uri;

import com.xavisson.marvelpractice.BuildConfig;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by javidelpalacio on 23/1/18.
 */

public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    final static String CHARACTER_BASE_URL = "https://gateway.marvel.com/v1/public/characters";
    final static String COMICS = "comics";
    final static String TIMESTAMP = "ts";
    final static String API_KEY = "apikey";
    final static String HASH = "hash";
    final static String ORDER = "orderBy";

    final static String publicAPIKey = BuildConfig.MARVEL_PUBLIC_KEY;
    final static String privateAPIKey = BuildConfig.MARVEL_PRIVATE_KEY;
    final static String characterId = BuildConfig.CHARACTER_ID;


    public static URL buildUrl(String sortBy) {

        String characterIdUrl = CHARACTER_BASE_URL + "/" + characterId + "/" + COMICS;
        String ts = Long.toString(System.currentTimeMillis() / 1000);
        String hash = Tools.md5(ts + privateAPIKey + publicAPIKey);
        String order = "title";

// WORKS  https://gateway.marvel.com/v1/public/comics?ts=1&apikey=f46711b64325456ca24cd1bd55e5ab72&hash=c9b58811c9b709461f6fbf5dc6c270a4
// doesnt https://gateway.marvel.com/v1/public/comics?ts=1&apikey=f46711b64325456ca24cd1bd55e5ab72&hash=c9b58811c9b79461f6fbf5dc6c270a4

        Uri builtUri = Uri.parse(characterIdUrl + "?").buildUpon()
                .appendQueryParameter(ORDER, order)
                .appendQueryParameter(TIMESTAMP, ts)
                .appendQueryParameter(API_KEY, publicAPIKey)
                .appendQueryParameter(HASH, hash)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}