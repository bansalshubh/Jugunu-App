package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Button;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,locat;
    ViewPager viewPager;
    BottomNavigationView btm;
    ViewPagerAdapter viewPagerAdapter;
    SearchView search;
    LinearLayout dotsLayout;
    TextView tev;
    TextView[] dots;
    int[] Layouts;
    PrefManager prefManager;
    private long pressedTime;
    private FirebaseAuth auth;
//    FirebaseAuth.AuthStateListener authListener;
    static int back=0;
    String loc;
    static final long SLIDER_TIMER = 2000; // change slider interval
    int currentPage = 0;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    boolean isCountDownTimerActive = false;
    Handler handler;
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (!isCountDownTimerActive) {
                automateSlider();
            }
            handler.postDelayed(runnable, 1000);
// our runnable should keep running for every 1000 milliseconds (1 seconds)
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        handler = new Handler();
        handler.postDelayed(runnable, 1000);
        runnable.run();
        tev = (TextView)findViewById(R.id.tev);
        viewPager = findViewById(R.id.vp);
        search = (SearchView)findViewById(R.id.search);
        user = FirebaseAuth.getInstance().getCurrentUser();
        final String phone = user.getPhoneNumber();
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users");
//        Query checkuser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phonenumber").equalTo(phone);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                loc = snapshot.child(phone).child("location").getValue(String.class);
                tev.setText(loc);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });
        ll1 = (LinearLayout) findViewById(R.id.ll1);
        ll2 = (LinearLayout) findViewById(R.id.ll2);
        ll3 = (LinearLayout) findViewById(R.id.ll3);
        ll4 = (LinearLayout) findViewById(R.id.ll4);
        ll5 = (LinearLayout) findViewById(R.id.ll5);
        ll6 = (LinearLayout) findViewById(R.id.ll6);
        locat = (LinearLayout) findViewById(R.id.locat);
        locat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,PlumberActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                ColorDrawable color = (ColorDrawable) ll2.getBackground();
//                int colorid = color.getColor();
//                plum.setTextColor(Color.parseColor("#ffffff"));
//                ll2.setBackgroundColor(Color.parseColor("#959191"));
//                Toast.makeText(MainActivity.this, "This is Electrician section", Toast.LENGTH_SHORT).show();
//                ll2.setBackgroundColor(colorid);
//                plum.setTextColor(Color.parseColor("#000000"));
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,CarpenterActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                ColorDrawable color = (ColorDrawable) ll3.getBackground();
//                int colorid = color.getColor();
//                carp.setTextColor(Color.parseColor("#ffffff"));
//                ll3.setBackgroundColor(Color.parseColor("#959191"));
//                Toast.makeText(MainActivity.this, "This is Electrician section", Toast.LENGTH_SHORT).show();
//                ll3.setBackgroundColor(colorid);
//                carp.setTextColor(Color.parseColor("#000000"));
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,ElectricianActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,HomePage.class));
                }
            }
        });
        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                ColorDrawable color = (ColorDrawable) ll4.getBackground();
//                int colorid = color.getColor();
//                help.setTextColor(Color.parseColor("#ffffff"));
//                ll4.setBackgroundColor(Color.parseColor("#959191"));
//                Toast.makeText(MainActivity.this, "This is Electrician section", Toast.LENGTH_SHORT).show();
//                ll4.setBackgroundColor(colorid);
//                help.setTextColor(Color.parseColor("#000000"));
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,BarberActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                ColorDrawable color = (ColorDrawable) ll5.getBackground();
//                int colorid = color.getColor();
//                help.setTextColor(Color.parseColor("#ffffff"));
//                ll5.setBackgroundColor(Color.parseColor("#959191"));
//                Toast.makeText(MainActivity.this, "This is Electrician section", Toast.LENGTH_SHORT).show();
//                ll5.setBackgroundColor(colorid);
//                help.setTextColor(Color.parseColor("#000000"));
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,HelperActivity.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                ColorDrawable color = (ColorDrawable) ll6.getBackground();
//                int colorid = color.getColor();
//                help.setTextColor(Color.parseColor("#ffffff"));
//                ll6.setBackgroundColor(Color.parseColor("#959191"));
//                Toast.makeText(MainActivity.this, "This is Electrician section", Toast.LENGTH_SHORT).show();
//                ll6.setBackgroundColor(colorid);
//                help.setTextColor(Color.parseColor("#000000"));
                if(user!=null)
                {
                    startActivity(new Intent(MainActivity.this,HomePage.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        });
        btm = (BottomNavigationView) findViewById(R.id.btm);
        dotsLayout=findViewById(R.id.layoutDots);
        Layouts = new int[]{
                R.layout.fragment_slider1,
                R.layout.fragment_slider2,
                R.layout.fragment_slider3
        };
        viewPagerAdapter = new ViewPagerAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);
//        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId())
//                {
//                    case R.id.home:
//                        Toast.makeText(MainActivity.this, "This is my Home", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.off:
//                        Toast.makeText(MainActivity.this, "This is my offers", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.set:
//                        Toast.makeText(MainActivity.this, "This is my settings", Toast.LENGTH_SHORT).show();
//                        break;
//
//                    case R.id.prof:
//                        Toast.makeText(MainActivity.this, "This is my profile", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });

        btm.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home:
                        return true;

                    case R.id.off:
                        startActivity(new Intent(MainActivity.this,OfferActivity.class));
                        finish();
                        return true;

                    case R.id.prof:
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                        finish();
                        return true;

                    case R.id.book:
                        startActivity(new Intent(MainActivity.this,BookingsActivity.class));
                        finish();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
//        if(back==0) {
//            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
//            back++;
//        }
//        else
//        {
//            back++;
//            finish();
//        }
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void addBottomDots(int cPage) {
        dots = new TextView[Layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("."));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[cPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[cPage].setTextColor(colorsActive[cPage]);
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == 0) {
//                Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
                currentPage = 0;
            } else if (position == 1) {
//                Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
                currentPage = 1;
            } else {
//                Toast.makeText(MainActivity.this, position, Toast.LENGTH_SHORT).show();
                currentPage = 2;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void automateSlider() {
        isCountDownTimerActive = true;
        new CountDownTimer(SLIDER_TIMER, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {

                int nextSlider = currentPage + 1;
//                Toast.makeText(MainActivity.this, currentPage, Toast.LENGTH_SHORT).show();
                if (nextSlider == 3) {
                    nextSlider = 0; // if it’s last Image, let it go to the first image
                }
                viewPager.setCurrentItem(nextSlider);
                isCountDownTimerActive = false;
            }
        }.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
// Kill this background task once the activity has been killed
        handler.removeCallbacks(runnable);
    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {


        public ViewPagerAdapter(MainActivity mainActivity, @NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new SliderFragment1();
                    break;
                case 1:
                    fragment = new SliderFragment2();
                    break;
                case 2:
                    fragment = new FragmentSlider3();
                    break;
                // you may add more cases for more fragments
            }
            assert fragment != null;
            return fragment;
        }

            @Override
            public int getCount () {
                return Layouts.length;
            }
    }

}