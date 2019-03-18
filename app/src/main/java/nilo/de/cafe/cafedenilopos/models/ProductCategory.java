package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

public class ProductCategory {

    @SerializedName("id")
    int id;
    @SerializedName("category_name")
    private String category_name;

    public ProductCategory( int id, String category_name) {
        this.category_name = category_name;
        this.id = id;
    }
    public ProductCategory( String category_name) {
        this.category_name = category_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getId() {
        return id;
    }

    public String getCategory_name() {
        return category_name;
    }
}
