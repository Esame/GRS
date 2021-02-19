package com.example.gettingridofstuff;

/*Charity class creates charity object containing necessary information about a donation center */
public class Charity {
    String name;
    String hours;
    int id;
    String category;
    String address;
    double latitude;
    double longitude;

    public Charity(String cname, String chours, int cid, String ccategory, String caddress, double latitude, double longitude){
        setName(cname);
        setId(cid);
        setCategory(ccategory);
        setHours(chours);
        setAddress(caddress);
        setLatitude(latitude);
        setLongitude(longitude);
    }

    public String getCategory() {
        return category;
    }

    public String getHours() {
        return hours;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
