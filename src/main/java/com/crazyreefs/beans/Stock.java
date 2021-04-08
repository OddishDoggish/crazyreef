package com.crazyreefs.beans;

import java.util.HashSet;
import java.util.Set;

public class Stock {
    private int stockId; // primary key,
    private String name; // not null, unique
    private double price;
    private double cost;
    private String source;
    private String description;
    private String size;
    private int available;
    private boolean sample; // true for typical, false for WYSIWYG
    private String category;
    private String subcategory;
    private Set<String> photos;
    private Condition conditions;

    public Stock() {
        stockId = 0;
        name = "";
        price = 0.0;
        cost = 0.0;
        source = "";
        description = "";
        size = "";
        available = 0;
        sample = true;
        category = "";
        subcategory = "";
        setPhotos(new HashSet<String>());
        setConditions(new Condition());
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public boolean isSample() {
        return sample;
    }

    public void setSample(boolean sample) {
        this.sample = sample;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Set<String> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<String> photos) {
        this.photos = photos;
    }

    public Condition getConditions() {
        return conditions;
    }

    public void setConditions(Condition conditions) {
        this.conditions = conditions;
    }
}
