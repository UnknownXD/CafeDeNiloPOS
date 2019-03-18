package nilo.de.cafe.cafedenilopos.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class ProductList {

    @SerializedName("products")
    private ArrayList<Products> productList;

    public ArrayList<Products> getProductList() {
        return productList;
    }

    public void setProductArrayList(ArrayList<Products> productArrayList) {
        this.productList = productArrayList;
    }
}
