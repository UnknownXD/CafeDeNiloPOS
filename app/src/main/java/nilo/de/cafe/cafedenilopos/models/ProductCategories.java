package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductCategories {
    @SerializedName("products_category")
    private ArrayList<ProductCategory> categories_name;

    public ProductCategories() {

    }

    public ArrayList<ProductCategory> getCategoryName() {
        return categories_name;
    }

    public void setCategories(ArrayList<ProductCategory> categories_name) {
        this.categories_name = categories_name;
    }
}
