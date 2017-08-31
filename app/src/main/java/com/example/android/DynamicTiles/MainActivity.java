package com.example.android.DynamicTiles;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.android.DynamicTiles.R.id.gridview;

public class MainActivity extends AppCompatActivity {

    int i;
    int firstClick ;
    int secondClick ;
    GridView grid;
    Boolean start;
    Button goNext;
    TextView scoreText;
    TextView timeText;

    int score =0;
    // IMAGE MAPS
    // Map represents < Integer image number,  Integer res file for that image>
    public Map<Integer, Integer> imageFileMap = createImageFileMap();

    public Map<Integer, Integer> createImageFileMap() {

        Map<Integer, Integer> imageFileMap = new HashMap<Integer, Integer>();
        imageFileMap.put(0, R.drawable.python );
        imageFileMap.put(1, R.drawable.js);
        imageFileMap.put(2, R.drawable.clojure);
        imageFileMap.put(3, R.drawable.julia);
        imageFileMap.put(4, R.drawable.ruby);
        imageFileMap.put(5, R.drawable.scala );
        imageFileMap.put(6, R.drawable.perl );
        imageFileMap.put(7, R.drawable.php);

        return imageFileMap;
    }// end createImageFileMap

    // Map represents < Integer position in grid, Integer image number>
    public Map<Integer, Integer> imgPosMap = createImgPosMap();

    public Map<Integer, Integer> createImgPosMap() {

        Map<Integer, Integer> imgPosMap = new HashMap<Integer, Integer>();
        return imgPosMap;
    }// end createImageFileMap

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        grid = (GridView) findViewById(gridview);
        grid.setAdapter(new ImageAdapter(this));
        goNext = (Button) findViewById(R.id.next);
        scoreText = (TextView) findViewById(R.id.score);
        timeText = (TextView) findViewById(R.id.time);
        generateTiles();
        setTimer();

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, int position, long id) {

                i++;
                if (i % 2 != 0) {
                    firstClick = position;
                   // String name = imgs[firstClick];
                    ImageView img = (ImageView) parent.getChildAt(firstClick);
                    img.setImageResource(imageFileMap.get(imgPosMap.get(firstClick)));
                    // Turn image question over to actual image for 4 seconds (ish)
                    new CountDownTimer(900, 4000) {

                        @Override
                        public void onTick(long millisUntilFinished) {}

                        // Here change back to question image
                        @Override
                        public void onFinish() {
                            ImageView img = (ImageView) parent.getChildAt(firstClick);
                             img.setImageResource(R.drawable.question);
                        }
                    }.start();
                 // end timer  & display for firstClick

                }
                else {
                    secondClick = position;
                    ImageView img = (ImageView) parent.getChildAt(secondClick);
                    // int idImg = getResources().getIdentifier("com.example.android.gridTest:drawable/" + name, null, null);
                    img.setImageResource(imageFileMap.get(imgPosMap.get(secondClick)));

                    new CountDownTimer(900, 4000) {

                        @Override
                        public void onTick(long millisUntilFinished) {}

                        @Override
                        public void onFinish() {
                            ImageView img = (ImageView) parent.getChildAt(secondClick);
                            img.setImageResource(R.drawable.question);
                        }
                    }.start();

                 } // end timer  & display for secondClick
                      if (imageFileMap.get(imgPosMap.get(firstClick)) == imageFileMap.get(imgPosMap.get(secondClick)) && (firstClick != secondClick)) {
                          score = score+2;

                          Toast.makeText(getApplicationContext(), "CORRECT " + " +2", Toast.LENGTH_LONG).show();
                          scoreText.setText("Score: "+ score);
                          new CountDownTimer(300, 3000) {
                              @Override
                              public void onTick(long millisUntilFinished) {}

                              @Override
                              public void onFinish() {
                                  parent.getChildAt(firstClick).setVisibility(View.GONE);
                                  parent.getChildAt(secondClick).setVisibility(View.GONE);
                               }
                          }.start();
                      }
                            //ADD SCORE
                } // end onItemClick
            }); // end Listener

        };// end Oncreate

    public void setTimer(){
        start = true;
        timeText.setText(" Time :45  ");
         new CountDownTimer(45100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeText.setText(" Time: "+ (millisUntilFinished/1000)+ "  ");
            }
            @Override
            public void onFinish() {
                timeText.setText(" Time Up! ");
                 start = false;
             }
        }.start();
    }// end setTimer

    // Set the board of tiles
    public void generateTiles() {

        List<Integer> positions = new ArrayList<Integer>();
        // Make a list of positions sorted 0-15 ( for 16 grid tiles)
        for (int i =0 ; i < imageFileMap.size()*2 ; i ++)
        {
            positions.add(i) ;

        }
        //Randomize positions so images appear randomly on grid
        Collections.shuffle(positions);
        // Randomly populate grid of images

        int count = 0;
         for (int p =0 ; p < positions.size()  ; p =p+2) {

             for (int f = 0; f < 2 ; f++){

                 imgPosMap.put(positions.get(p), count);
                 imgPosMap.put(positions.get(p+1), count);
            }
            count = count+1;
        } // end for loop
    } // end generateTiles

    public void allDone( View view) {

        Intent intent = new Intent(this, EndGame.class);
        Bundle b = new Bundle();
        b.putInt("score", score);
        intent.putExtras(b);
        startActivity(intent);
    }// end allDone - go to next Screen
}// end main

