package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    final Context context = this;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    TextView tv1,tv2,tv3,profile,tv4;
    String name,phone;
    int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        getSupportActionBar();
        getSupportActionBar().setTitle("MyProfile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        tv1 = (TextView)findViewById(R.id.tv1);
        tv2 = (TextView)findViewById(R.id.tv2);
        tv3 = (TextView)findViewById(R.id.tv3);
        tv4 = (TextView)findViewById(R.id.tv4);
        profile = (TextView)findViewById(R.id.profile);
        phone = user.getPhoneNumber();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users");;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv3.setText(snapshot.child(phone).child("phonenumber").getValue(String.class));
                if(snapshot.child(phone).child("name").getValue(String.class)!=null) {
                    name = snapshot.child(phone).child("name").getValue(String.class).toString().trim();
                    tv4.setText(name);
                    index = name.indexOf(" ",0);
                    if(index!=-1) {
                        tv1.setText(name.substring(0,index));
                        tv2.setText(name.substring(index+1,name.length()));
                    }
                    else
                        tv1.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(ProfileActivity.this, text, Toast.LENGTH_SHORT).show();
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.editprofile, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(promptsView);
                final EditText ed1 = (EditText) promptsView.findViewById(R.id.ed1);
                final EditText ed2 = (EditText) promptsView.findViewById(R.id.ed2);
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Confirm",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // get user input and set it to result
                                        // edit text
                                        assert phone != null;
//                                        Toast.makeText(ProfileActivity.this,ed1.getText().toString()+" "+ed2.getText().toString() , Toast.LENGTH_SHORT).show();
                                        reference.child(phone).child("name").setValue(ed1.getText().toString()+" "+ed2.getText().toString());
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

//                final Dialog dialog = new Dialog(ProfileActivity.this);
//                dialog.setContentView(R.layout.editprofile);
//                dialog.setCancelable(false);
//                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//
//                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//                dialog.getWindow().setAttributes(lp);
//                lp.gravity = Gravity.CENTER;
//                final EditText ed1 = dialog.findViewById(R.id.ed1);
//                final EditText ed2 = dialog.findViewById(R.id.ed2);
//                final Button btn1 = dialog.findViewById(R.id.btn1);
//                dialog.show();
//                dialog.setOnKeyListener(new Dialog.OnKeyListener() {
//
//                    @Override
//                    public boolean onKey(DialogInterface arg0, int keyCode,
//                                         KeyEvent event) {
//                        // TODO Auto-generated method stub
//                        if (keyCode == KeyEvent.KEYCODE_BACK) {
//                            dialog.dismiss();
//                        }
//                        return true;
//                    }
//                });
//                btn1.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        assert phone != null;
//                        dialog.dismiss();
//                        Toast.makeText(ProfileActivity.this,ed1.getText().toString()+" "+ed2.getText().toString() , Toast.LENGTH_SHORT).show();
//                        reference.child(phone).child("name").setValue(ed1.getText().toString()+" "+ed2.getText().toString());
//                    }
//                });
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ProfileActivity.this,MainActivity.class));
        finish();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.menu_eletrician, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.logout) {
            AlertDialog.Builder ad1=new AlertDialog.Builder(this);
            ad1.setMessage("Do you really want to Logout ? ");
            ad1.setCancelable(false);
            ad1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                }
            });
            ad1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
                        finish();
                }
            });
            AlertDialog alert=ad1.create();
            alert.show();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}