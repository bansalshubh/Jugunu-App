package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Html;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OrderActivity extends AppCompatActivity {

//    String url1 = "https://images.unsplash.com/photo-1517646287270-a5a9ca602e5c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80";
//    String url2 = "https://cdn.pixabay.com/photo/2013/12/13/21/13/plumber-228010_960_720.jpg";
//    String url3 = "https://bizzbucket.co/wp-content/uploads/2020/08/Life-in-The-Metro-Blog-Title-22.png";
    EditText ed1, ed2;
    ViewPager viewPager;
    String phone, address;
    Button proceed;
//    String[] texts = {"Plumber1", "Plumber2", "Plumber3"};
    FirebaseUser user;
    TextView[] dots;
    LinearLayout dotsLayout;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Intent intent1 = getIntent();
        final String service = intent1.getStringExtra("message");
        ed1 = (EditText) findViewById(R.id.ed1);
        viewPager = (ViewPager) findViewById(R.id.vp);
        proceed = (Button)findViewById(R.id.proceed);
        getSupportActionBar().setTitle("Service");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.background_dark)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dotsLayout=findViewById(R.id.layoutDots);
        ed2 = (EditText) findViewById(R.id.ed2);
        user = FirebaseAuth.getInstance().getCurrentUser();
        phone = user.getPhoneNumber();
        ed2.setText(phone);
        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                address = snapshot.child(phone).child("location").getValue(String.class);
                ed1.setText(address);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(OrderActivity.this, "Error while fetching data", Toast.LENGTH_SHORT).show();
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("myKey", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("add",ed1.getText().toString());
                editor.putString("pho",ed2.getText().toString());
                editor.putString("serv",service);
                editor.commit();
                Intent intent = new Intent(OrderActivity.this,ConfirmActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);

//        ArrayList<SliderData> sliderDataArrayList = new ArrayList<>();

//        sliderview = (SliderView)findViewById(R.id.slider);

//        sliderDataArrayList.add(new SliderData(url1));
//        sliderDataArrayList.add(new SliderData(url2));
//        sliderDataArrayList.add(new SliderData(url3));

//        SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
//        ((SliderView) findViewById(R.id.slider)).setSliderAdapter(adapter);

//        sliderAdapter = new SliderAdapter(images,texts);
//        sliderview.setSliderAdapter(sliderAdapter);
//        sliderview.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        sliderview.setIndicatorAnimation(IndicatorAnimationType.SLIDE);
//        sliderview.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
//        sliderView.setAutoCycleDirection(0);

//        sliderview.setSliderAdapter(adapter);

//        sliderview.setScrollTimeInSec(3);

//        sliderview.setAutoCycle(true);

//        sliderview.startAutoCycle();
    }

    public void addBottomDots(int cPage) {
        int[] images = {R.drawable.logo, R.drawable.logo, R.drawable.logo};
        dots = new TextView[images.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("-"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[cPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[cPage].setTextColor(colorsActive[cPage]);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        startActivity(new Intent(OrderActivity.this,PlumberActivity.class));
        finish();
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {
            OrderActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                        addBottomDots(1);
                    }
                    else if(viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                        addBottomDots(2);
                    }
                    else {
                        viewPager.setCurrentItem(0);
                        addBottomDots(0);
                    }
                }
            });
        }
    }


    public class ViewPagerAdapter extends PagerAdapter {

        Context context;
        LayoutInflater layoutInflater;
        int[] images = {R.drawable.logo, R.drawable.logo, R.drawable.logo};

        public ViewPagerAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.sliders, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.myimage);
            imageView.setImageResource(images[position]);
            ViewPager vp = (ViewPager) container;
            vp.addView(view, 0);
            return view;
        }

        public void destroyItem(ViewGroup container,int position ,Object object)
        {
            ViewPager vp = (ViewPager)container;
            View view = (View) object;
            vp.removeView(view);
        }
    }

}