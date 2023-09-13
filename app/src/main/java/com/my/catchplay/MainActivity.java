package com.my.catchplay;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timeText;
    TextView scoreText;
    int score;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML dosyasındaki bileşenleri tanımlama
        timeText =findViewById(R.id.timeId);
        scoreText =findViewById(R.id.scoreId);
        imageView1=findViewById(R.id.imageView1);
        imageView2=findViewById(R.id.imageView2);
        imageView3=findViewById(R.id.imageView3);
        imageView4=findViewById(R.id.imageView4);
        imageView5=findViewById(R.id.imageView5);
        imageView6=findViewById(R.id.imageView6);
        imageView7=findViewById(R.id.imageView7);
        imageView8=findViewById(R.id.imageView8);
        imageView9=findViewById(R.id.imageView9);

        // Resimleri bir diziye yerleştirme
        imageArray=new ImageView[]{imageView1, imageView2, imageView3, imageView4, imageView5, imageView6, imageView7, imageView8, imageView9};

        score = 0;

        // 10 saniyelik bir geri sayım başlatma
        new CountDownTimer(10000,1000){
            @Override
            public void onTick(long l) {
                timeText.setText("Time: "+l/1000);
            }
            @Override
            public void onFinish() {
                handler.removeCallbacks(runnable);

                // Resimleri gizleme
                for (ImageView image: imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }

                // Yeniden başlatma ile ilgili bir iletişim kutusu gösterme
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Restart?");
                alert.setMessage("Are you sure to restart the game");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Oyunu yeniden başlatma
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Oyunu sonlandırma
                        Toast.makeText(MainActivity.this,"Game over!",Toast.LENGTH_LONG).show();
                    }
                });
                alert.show();
            }
        }.start();

        // Resimleri gizleme işlemini başlatma
        hideImages();
    }

    // Skoru artırma işlevi
    public void increaseScore(View view){
        score++;
        scoreText.setText("Score: " + score);
    }

    // Resimleri gizleme işlevi
    public void hideImages(){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                for (ImageView image: imageArray) {
                    image.setVisibility(View.INVISIBLE);
                }
                Random random = new Random();
                int i = random.nextInt(imageArray.length);
                imageArray[i].setVisibility(View.VISIBLE);

                // Resimleri belirli aralıklarla gösterme
                handler.postDelayed(this,500);
            }
        };
        handler.post(runnable);
    }
}
