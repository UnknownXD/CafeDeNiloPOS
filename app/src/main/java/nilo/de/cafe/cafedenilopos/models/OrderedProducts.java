package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class OrderedProducts {

    @SerializedName("id")
    private int id;
    @SerializedName("transaction_id")
    int transaction_id;
    @SerializedName("product_name")
    String product_name;
    @SerializedName("price")
    double price;
    @SerializedName("quantity")
    double quantity;
    @SerializedName("created_at")
    String created_at;

    public OrderedProducts( int id, int transaction_id, String product_name, double price, double quantity, String created_at) {
        this.id = id;
        this.transaction_id = transaction_id;
        this.product_name = product_name;
        this.price = price;
        this.quantity =quantity;
        this.created_at = created_at;
    }

    public OrderedProducts( int transaction_id, String product_name, double price, double quantity, String created_at) {

        this.transaction_id = transaction_id;
        this.product_name = product_name;
        this.price = price;
        this.quantity =quantity;
        this.created_at = created_at;
    }

    public OrderedProducts( String product_name, double price) {
        this.product_name= product_name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getTransaction_id() {
        return transaction_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setTransaction_id(int transaction_id) {
        this.transaction_id = transaction_id;
    }
}
