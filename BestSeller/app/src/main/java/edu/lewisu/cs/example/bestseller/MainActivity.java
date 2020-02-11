package edu.lewisu.cs.example.bestseller;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final String API_KEY = "42ff06dcd8c04a4cae037a10a43ffd4c";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("api.nytimes.com")
                .appendPath("svc")
                .appendPath("books")
                .appendPath("v3")
                .appendPath("lists.json")
                .appendQueryParameter("api-key", API_KEY)
                .appendQueryParameter("list", "hardcover-fiction");

        Uri nytBooks = builder.build();
        Log.d("url", nytBooks.toString());
    }
}
