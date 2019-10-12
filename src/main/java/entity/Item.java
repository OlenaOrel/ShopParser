package entity;

import java.util.List;

public class Item {

    private String name;
    private String brand;
    private List<String> color;
    private double price;
    private String articleId;

    public Item() {
    }

    public Item(String name, String brand, List<String> color, double price, String articleId) {
        this.name = name;
        this.brand = brand;
        this.color = color;
        this.price = price;
        this.articleId = articleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public List<String> getColor() {
        return color;
    }

    public void setColor(List<String> color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", color=" + color +
                ", price=" + price +
                ", articleId='" + articleId + '\'' +
                '}';
    }
}
