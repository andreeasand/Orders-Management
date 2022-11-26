package model;

public class Orderr {
    private int id;
    private int clientID;
    private int productID;
    private int quantity;

    public Orderr() {

    }

    public Orderr(int id, int clientID, int productID, int quantity) {
        this.id = id;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public Orderr(int clientID, int productID, int quantity) {
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getID() {
        return id;
    }

    public void setID(int iD) {
        id = iD;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ID" + this.id + " ClientID: " + this.clientID + " ProductID: " + this.productID;
    }
}
