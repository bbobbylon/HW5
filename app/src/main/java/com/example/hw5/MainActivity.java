package com.example.hw5;

//import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final String API_KEY = "5aea6b8c5526b617ed3558cc63040698";
    private final static String URL_STRING = "https://api.themoviedb.org/3/movie/now_playing?api_key=5aea6b8c5526b617ed3558cc63040698&language=en-US&page=1";
    private ProgressBar progressBar;
    private MovieAdapter movieAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        //dont have to do this, can just do it as a string
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("now_playing")
                .appendQueryParameter("api_key", API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1");

        Uri tmdbMovies = builder.build();
        Log.d("url", tmdbMovies.toString());
        DownloadMovie downloadMovie = new DownloadMovie(this);
        downloadMovie.execute(tmdbMovies);

    }

    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra("movie", movie);
        startActivity(detailIntent);
    }

    private static class DownloadMovie extends AsyncTask<Uri, Void, ArrayList<Movie> >
    {
        private WeakReference<MainActivity> activityWeakReference;
        public DownloadMovie(MainActivity mainActivity)
        {
            activityWeakReference = new WeakReference<>(mainActivity); //avoids memory leaks
        }

        @Override
        protected void onPreExecute() {
            MainActivity activity = activityWeakReference.get();
            activity.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Uri... uris) {



            OkHttpClient client = new OkHttpClient();
            String jsonData = "";
            ArrayList<Movie> movies= new ArrayList<>();
            try {
                URL url = new URL(uris[0].toString());
                Request.Builder builder = new Request.Builder();
                builder.url(url);
                Request request = builder.build();
                Response response = client.newCall(request).execute();
                if(response.body() != null)
                {
                    jsonData = response.body().string();
                    String title;
                    String vote_average;
                    String overview;


                    //I would like to implement a poster image of the movie instead of popularity.
                    String popularity;
                    String releaseDate;
                    int numMovies = 10;
                    JSONObject results = new JSONObject(jsonData);
                    JSONArray movieList = results.getJSONArray("results");
                    if(movieList.length() < 10)
                    {
                        numMovies = movieList.length();
                    }

                    for (int i =0; i<numMovies; i++)
                    {
                        JSONObject movieObject = movieList.getJSONObject(i);
                        title = movieObject.getString("title");
                        vote_average = movieObject.getString("vote_average");
                        overview = movieObject.getString("overview");
                        popularity = movieObject.getString("popularity");
                        releaseDate = movieObject.getString("release_date");
                        Movie movie = new Movie(title, vote_average, popularity, overview,releaseDate) ;
                        movies.add(movie);
                    }
                    return movies;

                }


            }catch(Exception ex)
            {
                Log.d(TAG, ex.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            MainActivity activity = activityWeakReference.get();
            if(activity == null || activity.isFinishing())
            {
                return;
            }
            activity.progressBar.setVisibility(View.GONE);
            if(movies != null)
            {
                activity.movieAdapter.setMovieData(movies);
            }

        }
    }

}
