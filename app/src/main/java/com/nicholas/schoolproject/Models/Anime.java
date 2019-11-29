package com.nicholas.schoolproject.Models;

public class Anime {
    private  String Title;
    private  String Description;
    private  String Image;
    private  String  Price;
    private  String Category;

    public Anime() {
    }

    public Anime(String title, String description, String image, String price, String category) {
        Title = title;
        Description = description;
        Image = image;
        Price = price;
        Category = category;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
