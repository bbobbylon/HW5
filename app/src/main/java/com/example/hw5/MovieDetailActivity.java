package com.example.hw5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MovieDetailActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        Intent intent   = getIntent();
        movie = intent.getParcelableExtra("movie");


        if (movie !=null)
        {
            TextView titleTextView = findViewById(R.id.titleTextView);

            titleTextView.setText(movie.getTitle());

            TextView vote_averageTextView = findViewById(R.id.vote_averageTextView);
            vote_averageTextView.setText(movie.getVote_average());

            TextView overviewTextView = findViewById(R.id.overviewTextView);
            overviewTextView.setText(movie.getOverview());

            TextView popularityTextView = findViewById(R.id.popularityTextView);
            popularityTextView.setText(movie.getPopularity());

            TextView releaseDateTextView = findViewById(R.id.releaseDateTextView);
            releaseDateTextView.setText(movie.getReleaseDate());

        }



    }






}

