package com.example.assignment_0696;
public class Snack {

    private int image;
    private String name;
    private String price;
    private int quantity;

    public Snack(int image, String name, String price) {
        this.image = image;
        this.name = name;
        this.price = price;
        this.quantity = 0;
    }

    public int getImage() { return image; }
    public String getName() { return name; }
    public String getPrice() { return price; }
    public int getQuantity() { return quantity; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
