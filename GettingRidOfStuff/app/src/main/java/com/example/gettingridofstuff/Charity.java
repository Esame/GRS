package com.example.gettingridofstuff;

public class Charity {
    String name;
    String hours;
    String id;
    String category;

    public Charity(String cname, String chours, String cid, String ccategory){
        setName(cname);
        setId(cid);
        setCategory(ccategory);
        setHours(chours);
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

}
