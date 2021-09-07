package com.example.joggle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android .widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity {
    EditText edittext;
    Button log;
    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        edittext = (EditText)findViewById(R.id.edittext);
        log = (Button)findViewById(R.id.log);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        place = intent.getStringExtra("place");
        edittext.setText(place);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomePage.this,LoginActivity.class));
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomePage.this, "Wait, I will write code to change this", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(HomePage.this,indexActivity.class));
            }
        });
    }
}