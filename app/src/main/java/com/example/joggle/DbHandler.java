package com.example.joggle;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHandler  extends SQLiteOpenHelper {
    public DbHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create = "CREATE TABLE userdetails (name TEXT, email TEXT, phonenumber TEXT, location TEXT)";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop = String.valueOf("DROP TABLE IF EXISTS");
        sqLiteDatabase.execSQL(drop,new String[]{"userdetails"});
    }

    public boolean addUser(UserHelperClass helperClass)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",helperClass.getName());
        values.put("email",helperClass.getEmail());
        values.put("phonenumber",helperClass.getPhonenumber());
        values.put("location",helperClass.getLocation());
        db.insert("userdetails",null,values);
        db.close();
        return true;
    }

    public int getData(String pn) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from userdetails where phonenumber="+pn+"", null );
        if(res!=null && res.moveToFirst()) {
//            UserHelperClass help = new UserHelperClass(res.getString(0), res.getString(1), res.getString(2), res.getString(3));
            return 1;
        }
        else
        {
            return 0;
        }
    }
    public void update(String s, String s1) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("UPDATE userdetails SET name = "+"'"+s+"' "+ "WHERE salary = "+"'"+s1+"'");
    }
}
