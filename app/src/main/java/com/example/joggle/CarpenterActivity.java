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

import java.util.ArrayList;

public class CarpenterActivity extends AppCompatActivity {

    ProgressDialog progress;
    final Context context = this;
    LinearLayout img1ll1,img2ll1,img3ll1;
    TextView tv1;
    ImageView imgbtn1,imgbtn2,imgbtn3,imgbtn4;
    TextView others;
    ListView List1,List2,List3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpenter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Carpenter");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        imgbtn1 = (ImageView)findViewById(R.id.imgbtn1);
        imgbtn2 = (ImageView)findViewById(R.id.imgbtn2);
        imgbtn3 = (ImageView)findViewById(R.id.imgbtn3);
        img1ll1 = (LinearLayout)findViewById(R.id.img1ll1);
        img2ll1 = (LinearLayout)findViewById(R.id.img2ll1);
        img3ll1 = (LinearLayout)findViewById(R.id.img3ll1);
        tv1 = (TextView)findViewById(R.id.tv1);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String clickedItem = tv1.getText().toString();
                Progresss(clickedItem);
            }
        });
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
                                        Progresss(ed1.getText().toString());
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
        arrayList1.add("Order For a Bed");
        arrayList1.add("Dining Table");
        arrayList1.add("For Door Installation");
        arrayList1.add("Sofa Installation");
        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList1);
        List1.setAdapter(arrayAdapter1);

        List1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                String clickedItem = (String) List1.getItemAtPosition(i);
                Progresss(clickedItem);
//                Toast.makeText(ElectricianActivity.this,clickedItem, Toast.LENGTH_LONG).show();
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
        arrayList2.add("Door Repairing");
        arrayList2.add("Window Repairing");
        arrayList2.add("Bathroom Accessories & Repairing");
        arrayList2.add("Furniture Repair");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList2);
        List2.setAdapter(arrayAdapter2);

        List2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
                String clickedItem = (String) List2.getItemAtPosition(i);
                Progresss(clickedItem);
//                Toast.makeText(ElectricianActivity.this,clickedItem, Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(CarpenterActivity.this, OrderActivity.class);
                intent.putExtra("message", clickedItem);
                startActivity(intent);
                finish();
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CarpenterActivity.this,MainActivity.class));
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}