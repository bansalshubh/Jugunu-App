package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CreateAccount extends AppCompatActivity implements View.OnClickListener {

    EditText name,phone;
    TextView loginbtn;
    Button createbtn;
    String username,enailinput,passinput,cpassinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        name=(EditText)findViewById(R.id.name);
        phone=(EditText)findViewById(R.id.phone);
        loginbtn=(TextView) findViewById(R.id.loginbtn);
//        pb=(ProgressBar)findViewById(R.id.pb);
        createbtn=(Button)findViewById(R.id.signupbtn);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CreateAccount.this,LoginActivity.class));
            }
        });
        createbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}