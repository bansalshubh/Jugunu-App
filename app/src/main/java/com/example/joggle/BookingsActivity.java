package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingsActivity extends AppCompatActivity {

    ListView lv;
    Spinner spinner;
    Spinner sp1;
    Spinner sp2;
    TextView text;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    int maxid  = 0;
//    String []country={"Paid for Ac Installation" ,"paid for Furniture", "Paid for ","paid to Barber","Paid for water leakage"};
//    String [] desc= {"Your booking is successful","Your booking is successful","Your booking is successful","Your booking is successful","Your booking is successful"};
//    String [] desc1={"Reference id- 20987619","Reference id- 20987176","Reference id- 10987619","Reference id- 78987619","Reference id- 78987619"};
//    String [] rupee={" Rs 150"," Rs 250"," Rs 500"," Rs 100"," Rs 530"};
    ArrayList<String> ServiceArrayList;
    ArrayList<String> dates;
    ArrayList<String> referenceid;
    ArrayList<String> pricelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        getSupportActionBar().setTitle("Bookings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lv=findViewById(R.id.lv);
        text = (TextView)findViewById(R.id.text);
        ServiceArrayList = new ArrayList<String>();
        dates = new ArrayList<String>();
        referenceid = new ArrayList<String>();
        pricelist = new ArrayList<String>();
//        spinner = findViewById(R.id.sp);
//        sp1=findViewById(R.id.sp1);
//        sp2=findViewById(R.id.sp2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String myphone = user.getPhoneNumber();
        maxid = 1;
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Bookings").child(myphone);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
//                    Toast.makeText(BookingsActivity.this, String.valueOf(maxid)+" Bookings", Toast.LENGTH_SHORT).show();
                    ServiceArrayList.add(dataSnapshot.child("servicename").getValue(String.class));
//                    Toast.makeText(BookingsActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                    dates.add(dataSnapshot.child("date").getValue(String.class));
                    referenceid.add(dataSnapshot.child("bookingid").getValue(String.class));
                    pricelist.add(dataSnapshot.child("price").getValue(String.class));
                    maxid = maxid + 1;
//                    Toast.makeText(BookingsActivity.this, String.valueOf(maxid)+" Bookings", Toast.LENGTH_SHORT).show();
                }
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),ServiceArrayList,referenceid,dates,pricelist);
                lv.setAdapter(customAdapter);
//                text.setText(ServiceArrayList.get(0) + " " + referenceid.get(0) + " " + pricelist.get(0) + " ");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                view.setBackground(Drawable.createFromPath("?android:attr/selectableItemBackground"));
            }
        });


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(BookingsActivity.this,MainActivity.class));
        finish();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}