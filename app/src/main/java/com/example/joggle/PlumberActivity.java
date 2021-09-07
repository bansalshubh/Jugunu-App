package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlumberActivity extends AppCompatActivity {

    final Context context = this;
    LinearLayout img1ll1,img2ll1,img3ll1;
    ImageView imgbtn1,imgbtn2,imgbtn3,imgbtn4;
    TextView others;
    ListView List1,List2,List3;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);
        imgbtn1 = (ImageView)findViewById(R.id.imgbtn1);
        imgbtn2 = (ImageView)findViewById(R.id.imgbtn2);
        imgbtn3 = (ImageView)findViewById(R.id.imgbtn3);
        img1ll1 = (LinearLayout)findViewById(R.id.img1ll1);
        img2ll1 = (LinearLayout)findViewById(R.id.img2ll1);
        img3ll1 = (LinearLayout)findViewById(R.id.img3ll1);
        getSupportActionBar().setTitle("Plumber");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        others = (TextView)findViewById(R.id.others);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.others, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText ed1 = (EditText) promptsView.findViewById(R.id.ed1);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        Toast.makeText(context, ed1.getText().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        List1 = (ListView)findViewById(R.id.list1);
        ArrayList<String> arrayList1 = new ArrayList<>();
        arrayList1.add("WashBasin Installation");
        arrayList1.add("WaterTank Installation");
        arrayList1.add("Complete Bathroom Setup");
        arrayList1.add("Commod Installation");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        List1.setAdapter(arrayAdapter1);

        List1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                final String clickedItem = (String) List1.getItemAtPosition(i);
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
                        Intent intent = new Intent(PlumberActivity.this, OrderActivity.class);
                        intent.putExtra("message", clickedItem);
                        startActivity(intent);
                        finish();
                    }
                }).start();
            }
        });
        imgbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(img1ll1.getVisibility() == View.VISIBLE)
                {
                    img1ll1.setVisibility(View.GONE);
                    imgbtn1.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                }
                else
                {
                    img1ll1.setVisibility(View.VISIBLE);
                    imgbtn1.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                }
            }
        });

        List2 = (ListView)findViewById(R.id.list2);
        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Tap Fittings");
        arrayList2.add("Cystern Fitting");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        List2.setAdapter(arrayAdapter2);

        List2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                String clickedItem = (String) List2.getItemAtPosition(i);
                progress=new ProgressDialog(context);
                progress.setMessage("Finding Serviceman near you");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(5000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progress.dismiss();
                    }
                }).start();
                Intent intent = new Intent(PlumberActivity.this, OrderActivity.class);
                intent.putExtra("message", clickedItem);
                startActivity(intent);
                finish();
//                Toast.makeText(PlumberActivity.this,clickedItem, Toast.LENGTH_LONG).show();
            }
        });

        imgbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(img2ll1.getVisibility() == View.VISIBLE)
                {
                    img2ll1.setVisibility(View.GONE);
                    imgbtn2.setImageResource(R.drawable.ic_baseline_add_circle_outline_24);
                }
                else
                {
                    img2ll1.setVisibility(View.VISIBLE);
                    imgbtn2.setImageResource(R.drawable.ic_baseline_remove_circle_outline_24);
                }
            }
        });
    }
    public void onBackPressed() {
        startActivity(new Intent(PlumberActivity.this,MainActivity.class));
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}