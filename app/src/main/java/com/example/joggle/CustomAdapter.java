package com.example.joggle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String> servi = new ArrayList<String>();
    ArrayList<String> servid = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> dates = new ArrayList<String>();
    LayoutInflater inflater;

    public CustomAdapter(Context applicationContext, ArrayList<String>servi, ArrayList<String>servid, ArrayList<String>dates, ArrayList<String>price){
        this.context=applicationContext;
        this.servi = servi;
        this.servid = servid;
        this.dates = dates;
        this.price = price;
        inflater=(LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return servi.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.listview, null);
        TextView countryl=(TextView) view.findViewById(R.id.tv);
        TextView txt=(TextView) view.findViewById(R.id.desc);
        TextView dt = (TextView)view.findViewById(R.id.dt);
        TextView txt1=(TextView) view.findViewById(R.id.desc1);
        TextView txt2=(TextView) view.findViewById(R.id.rs);
        countryl.setText(servi.get(position));
        txt.setText("Your booking is Successfull");
        dt.setText("Order Date : "+dates.get(position));
        txt1.setText("Transaction Id : "+servid.get(position));
        txt2.setText(price.get(position));
        return view;
    }
}
