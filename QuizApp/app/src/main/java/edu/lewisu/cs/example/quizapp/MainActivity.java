package edu.lewisu.cs.example.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int lifetimeScore = 25;
    private TextView mainScoreTextView;
    private static final int QUIZ_INTENT_RESULT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainScoreTextView = findViewById(R.id.mainScoreTextView);
        String scoreString = getResources().getString(R.string.lifetime_score_string, lifetimeScore);
        mainScoreTextView.setText(scoreString);

        Button startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent quizIntent = new Intent(getApplicationContext(), QuizActivity.class);
                quizIntent.putExtra("lifetime", lifetimeScore);
                startActivityForResult(quizIntent, QUIZ_INTENT_RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == QUIZ_INTENT_RESULT) {
            int gameScore = data.getIntExtra("score", 0);
            lifetimeScore += gameScore;
            String scoreString = getResources().getString(R.string.lifetime_score_string, lifetimeScore);
            mainScoreTextView.setText(scoreString);
        }

    }
}
