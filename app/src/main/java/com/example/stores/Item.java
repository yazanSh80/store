package com.example.stores;

public class Item {
    String name;
    String code;
    String type;
    String description;
    String brand;
    int quantity;
    int price;

    public Item(String name, String code, String type, String description, String brand, int quantity, int price) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.description = description;
        this.brand = brand;
        this.quantity = quantity;
        this.price = price;
    }

    public Item() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "name='" + name + '\n' +
                ", category='" + type + '\n' +
                ", brand='" + brand + '\n' +
                ", price=" + price +
                '}';
    }
}
