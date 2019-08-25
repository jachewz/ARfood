package com.google.ar.sceneform.samples.hellosceneform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private CardView cardView, cardView2, cardView3, cardView4;
    String num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = (CardView) findViewById(R.id.card_view);
        cardView2 = (CardView) findViewById(R.id.card_view2);
        cardView3 = (CardView) findViewById((R.id.card_view3));
        cardView4 = (CardView) findViewById((R.id.card_view4));

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "burger";
                openActivity2();
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "pizza";
                openActivity2();
            }
        });

        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "cake";
                openActivity2();
            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num = "random";
                openFirebase();
            }
        });
    }

    public void openActivity2() {
        Intent intent = new Intent(this, HelloSceneformActivity.class);
        intent.putExtra("model",num);
        startActivity(intent);
    }

    public void openFirebase() {
        Intent intent = new Intent(this, Firebase.class);
        intent.putExtra("model",num);
        startActivity(intent);
    }

}
