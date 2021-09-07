package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class BarberActivity extends AppCompatActivity {

    ProgressDialog progress;
    final Context context = this;
    TextView tv1,tv2,tv3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barber);
        getSupportActionBar().setTitle("Saloon");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                Progresss(tv1.getText().toString());
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                Progresss(tv2.getText().toString());
            }
        });
        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                Progresss(tv3.getText().toString());
            }
        });
    }

    public void Progresss(final String clickedItem)
    {
        progress=new ProgressDialog(context);
        progress.setMessage("Finding Serviceman near you");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progress.dismiss();
                Intent intent = new Intent(BarberActivity.this, OrderActivity.class);
                intent.putExtra("message", clickedItem);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BarberActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}