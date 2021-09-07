package com.example.joggle;

public class UserHelperClass {
    String name,email,location;
    String phonenumber;
    int sno;

    public UserHelperClass()
    {}

    public UserHelperClass(String name, String email, String phonenumber,String location) {
        this.name = name;
        this.email = email;
        this.phonenumber = phonenumber;
        this.location=location;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
