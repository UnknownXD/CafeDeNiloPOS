package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class Products
{
    @SerializedName("id")
    int id;
    @SerializedName("product_name")
    private String product_name;
    @SerializedName("price")
    double price;
    @SerializedName("prepare_time")
    String prepare_time;
    @SerializedName("category_name")
    String category;
    @SerializedName("availability")
    String availability;


    public Products( int id, String product_name, double price, String prepare_time, String category, String availability ) {
        this.id = id;
        this.product_name = product_name;
        this.price = price;
        this.prepare_time = prepare_time;
        this.category = category;
        this.availability = availability;
    }

    public Products( String product_name, double price, String prepare_time, String category, String availability) {
        this.product_name= product_name;
        this.price = price;
        this.prepare_time = prepare_time;
        this.category = category;
        this.availability = availability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPrepare_time() {
        return prepare_time;
    }

    public void setPrepare_time(String prepare_time) {
        this.prepare_time = prepare_time;
    }
}

