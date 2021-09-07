package com.example.joggle;

public class Bookinghelperclass {
    String servicename,location,phonenumber,name,bookingid,bookingdate;

    public String getDate() {
        return bookingdate;
    }

    public void setDate(String bookingdate) {
        this.bookingdate = bookingdate;
    }

    String price;
    public Bookinghelperclass(){}

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getServicename() {
        return servicename;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Bookinghelperclass(String servicename, String location, String phonenumber, String name, String bookingdate, String bookingid, String price) {
        this.servicename = servicename;
        this.location = location;
        this.phonenumber = phonenumber;
        this.bookingdate = bookingdate;
        this.name = name;
        this.bookingid = bookingid;
        this.price = price;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

