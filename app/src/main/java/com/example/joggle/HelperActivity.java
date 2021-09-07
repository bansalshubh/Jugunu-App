package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class HelperActivity extends AppCompatActivity {

    ProgressDialog progress;
    final Context context = this;
    ListView List1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper);
        getSupportActionBar().setTitle("Helper");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        List1 = (ListView)findViewById(R.id.list1);
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("For One Hour / Two Hour");
        arrayList1.add("For 4 Hour");
        arrayList1.add("For 8 Hour");
        arrayList1.add("For 12 Hour");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        List1.setAdapter(arrayAdapter1);

        List1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                String clickedItem = (String) List1.getItemAtPosition(i);
                Progresss(clickedItem);
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
                Intent intent = new Intent(HelperActivity.this, OrderActivity.class);
                intent.putExtra("message", clickedItem);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(HelperActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}