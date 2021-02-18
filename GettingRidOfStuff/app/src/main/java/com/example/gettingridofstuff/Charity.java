package com.example.gettingridofstuff;

public class Charity {
    String name;
    String hours;
    String id;
    String category;
    String address;

    public Charity(String cname, String chours, String cid, String ccategory, String caddress){
        setName(cname);
        setId(cid);
        setCategory(ccategory);
        setHours(chours);
        setAddress(caddress);
    }

    public String getCategory() {
        return category;
    }

    public String getHours() {
        return hours;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
