package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class StartPaymentActivity extends AppCompatActivity{

    Button pay;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_payment);
//        Checkout.preload(getApplicationContext());
        pay = (Button)findViewById(R.id.pay);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startPayment();
            }
        });

    }

//    public void startPayment() {
//        /**
//         * Instantiate Checkout
//         */
//        Checkout checkout = new Checkout();
//        checkout.setKeyID("rzp_test_PajWdFF0SD1JRP");
//        /**
//         * Set your logo here
//         */
//        checkout.setImage(R.drawable.logo);
//
//        /**
//         * Reference to current activity
//         */
//
//        /**
//         * Pass your payment options to the Razorpay Checkout as a JSONObject
//         */
//        try {
//            JSONObject options = new JSONObject();
//
//            options.put("name", "Juggunu");
//            options.put("description", "Test Payment in jugunu");
////            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
////            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
//            options.put("theme.color", "#3399cc");
//            options.put("currency", "INR");
//            options.put("amount", 10000);//pass amount in currency subunits
//            options.put("prefill.email", "shubhamcs2120@gmail.com");
//            options.put("prefill.contact","9351710500");
////            JSONObject retryObj = new JSONObject();
////            retryObj.put("enabled", true);
////            retryObj.put("max_count", 4);
////            options.put("retry", retryObj);
//
//            checkout.open(StartPaymentActivity.this, options);
//
//        } catch(Exception e) {
//            Toast.makeText(StartPaymentActivity.this, "Some Error occured", Toast.LENGTH_SHORT).show();
//        }

//    @Override
//    public void onPaymentSuccess(String s) {
//        Toast.makeText(this, "Payment Succesfull", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(StartPaymentActivity.this,MainActivity.class));
//        finish();
//    }
//
//    @Override
//    public void onPaymentError(int i, String s) {
//        Toast.makeText(this, "error in payment" + s, Toast.LENGTH_SHORT).show();
//    }
}