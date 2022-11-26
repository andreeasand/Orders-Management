package model;

public class Product {
    private int id;
    private String name;
    private int price;
    private int quantity;

    public Product() {

    }

    public Product(int id, String name, int quantity)
    {
        this.id=id;
        this.name=name;
        this.quantity=quantity;
    }
    public Product(String name, int price, int quantity) {
        this.price = price;
        this.name = name;
        this.quantity=quantity;
    }

    public Product(int ID, String name, int price,int quantity) {
        this.id = ID;
        this.price = price;
        this.name = name;
        this.quantity=quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setID(int iD) {
        this.id = iD;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "id" + this.id + " Name: " + this.name;
    }
}