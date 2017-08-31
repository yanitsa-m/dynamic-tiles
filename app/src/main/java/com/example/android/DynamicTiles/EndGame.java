package com.example.android.DynamicTiles;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by yanitsam on 1/30/2017.
 */

public class EndGame  extends AppCompatActivity {

    TextView finishMessage;
    Button tryAgain;
    int score=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_game);

        finishMessage = (TextView) findViewById(R.id.message);
        tryAgain = (Button) findViewById(R.id.tryAgain);

       // Intent intent = getIntent();
        Bundle b = getIntent().getExtras();
        score = b.getInt("score");

        finishMessage.setTypeface(null, Typeface.BOLD_ITALIC);
        finishMessage.setGravity(Gravity.CENTER);
        finishMessage.setText("Congratulations you finished the game and you got a score of: " + score +
      "/16!" );


    }// end onCreate

    public void startOver( View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }// end startOver
} // end EndGame