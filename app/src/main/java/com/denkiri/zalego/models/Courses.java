package com.denkiri.zalego.models;

public class Courses {
    int id,category_id;

    String name,description,campus,duration,image,price;
    String rating;

    public Courses(int id ,String name, String description,String price,String campus, String duration,String rating,String image,int category_id ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.campus =campus;
        this.duration=duration;
        this.rating=rating;
        this.image=image;
        this.category_id=category_id;

    }


    public int getId(){
        return id;
    }
    public String getCourseName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getPrice(){
        return price;
    }
    public String getCampus(){
        return campus;
    }
    public String getDuration(){
        return duration;
    }
    public String getRating(){
        return rating;
    }
    public String getImage(){
        return image;
    }
    public int getCategory_id(){
        return category_id;
    }
}

