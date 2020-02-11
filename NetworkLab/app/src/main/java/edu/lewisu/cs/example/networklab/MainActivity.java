package edu.lewisu.cs.example.networklab;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private final static String  URL_STRING = "http://cs.lewisu.edu/~howardcy/materials/php/books1.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goButtonClick(View v) {
        String jsonData = "";

    }
}
