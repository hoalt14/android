package com.example.model;

public class Product {
    private int id;
    private String name;
    private String description;
    private byte[] photo;

    public Product(int id, String name, String description, byte[] photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }
}
