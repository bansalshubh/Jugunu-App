package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Random;

public class ConfirmActivity extends AppCompatActivity implements PaymentResultListener {

    TextView name,serviceid,dates,amount,addr,phone,services;
    Button proceed;
    int maxid = 0;
    String myphone,id,currentDate;
    String service,address,nm;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        SharedPreferences sharedPreferences = getSharedPreferences("myKey", MODE_PRIVATE);
        address = sharedPreferences.getString("add","");
        String Phone = sharedPreferences.getString("pho","");
        service = sharedPreferences.getString("serv","");
        getSupportActionBar().setTitle("Track Order");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Checkout.preload(getApplicationContext());
        user = FirebaseAuth.getInstance().getCurrentUser();
        proceed = (Button)findViewById(R.id.proceed);
        myphone = user.getPhoneNumber();
        name = (TextView)findViewById(R.id.name);
        serviceid = (TextView)findViewById(R.id.serviceid);
        dates = (TextView)findViewById(R.id.date);
        amount = (TextView)findViewById(R.id.amount);
        addr = (TextView)findViewById(R.id.addr);
        phone = (TextView)findViewById(R.id.phone);
        services = (TextView)findViewById(R.id.service);
        Date date = new Date();
        DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");
        currentDate = dtf.format(date);
        id = String.valueOf(makeid());
        serviceid.setText(id);
        dates.setText(currentDate);
        addr.setText(address);
        phone.setText(Phone);
        services.setText(service);
        firebaseDatabase = FirebaseDatabase.getInstance();
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
        reference = firebaseDatabase.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                nm = snapshot.child(myphone).child("name").getValue(String.class);
                name.setText(nm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ConfirmActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public char[] makeid()
    {
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String numbers = "0123456789";
        String values = Capital_chars + numbers;
        Random rand = new Random();
        char[] pass = new char[10];
        for (int i = 0; i < 10; i++)
            pass[i] = values.charAt(rand.nextInt(values.length()));
        return pass;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ad1=new AlertDialog.Builder(this);
        ad1.setMessage("Are you sure you want to exit from this page ? ");
        ad1.setCancelable(false);
        ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
            }
        });
        ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startActivity(new Intent(ConfirmActivity.this,MainActivity.class));
                finish();
            }
        });
        AlertDialog alert=ad1.create();
        alert.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_PajWdFF0SD1JRP");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Juggunu");
            options.put("description", "Test Payment in jugunu");
//            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", 30000);//pass amount in currency subunits
            options.put("prefill.email", "shubhamcs2120@gmail.com");
            options.put("prefill.contact","9351710500");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            checkout.open(ConfirmActivity.this, options);
//            Toast.makeText(this, "This is error", Toast.LENGTH_SHORT).show();

        } catch(Exception e) {
            Toast.makeText(ConfirmActivity.this, "Some Error occured", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Succesfull", Toast.LENGTH_SHORT).show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Bookings").child(myphone);

        final Bookinghelperclass bookinghelperclass = new Bookinghelperclass(service,address,myphone,nm,currentDate,id,"300");
//        Toast.makeText(this, String.valueOf(maxid), Toast.LENGTH_SHORT).show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reference.child(id).setValue(bookinghelperclass);
                startActivity(new Intent(ConfirmActivity.this,BookingsActivity.class));
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(ConfirmActivity.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "error in payment" + s, Toast.LENGTH_SHORT).show();
    }
}