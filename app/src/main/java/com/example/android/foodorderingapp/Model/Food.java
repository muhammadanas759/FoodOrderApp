package com.example.android.foodorderingapp.Model;

public class Food {
    String Name,Image,Description,Discount,MenuId,Price;

    public Food() {
    }

    public Food(String name, String image, String description, String discount, String menuId, String price) {
        Name = name;
        Image = image;
        Description = description;
        Discount = discount;
        MenuId = menuId;
        Price = price;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getName() {
        return Name;
    }

    public String getImage() {
        return Image;
    }

    public String getDescription() {
        return Description;
    }

    public String getDiscount() {
        return Discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public String getPrice() {
        return Price;
    }
}
