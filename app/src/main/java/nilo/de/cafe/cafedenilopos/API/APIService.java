package nilo.de.cafe.cafedenilopos.API;


import nilo.de.cafe.cafedenilopos.models.ProductCategories;
import nilo.de.cafe.cafedenilopos.models.ProductList;
import nilo.de.cafe.cafedenilopos.models.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    //Cafe Denilo
    @GET("products")
    Call<ProductList> getProducts();

    @GET("productscategory")
    Call<ProductCategories> getProductCategory();

    //Register
    @FormUrlEncoded
    @POST("transaction")
    Call<Result> createTransaction(

            @Field("date") String date,
            @Field("cash") Double cash,
            @Field("total_price") Double total_price,
            @Field("email") String email
            );

    //Queue
    @FormUrlEncoded
    @POST("insertqueue")
    Call<Result> createQueue(
            @Field("id") String date
    );

    //the signin call
    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    //create Queue Order
    @FormUrlEncoded
    @POST("insertqueueorder")
    Call<Result> createQueueOrder(
            @Field("queue_number") int queue_number,
            @Field("item_name") String item_name,
            @Field("quantity") double quantity);

    //create OrderTrans
    @FormUrlEncoded
    @POST("ordertransaction")
    Call<Result> createOrderTrans(
            @Field("transaction_id") int transaction_id,
    @Field("product_name") String product_name,
    @Field("price") double price,
    @Field("quantity") double quantity,
    @Field("created_at") String created_at);

    //Create Names
    @FormUrlEncoded
    @POST("name")
    Call<Result> createnames(
            @Field("name") String name);



    //updating user
    @FormUrlEncoded
    @POST("update/{nameID}")
    Call<Result> updateUser(
            @Path("nameID") String nameID,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("deleteuser/{nameID}")
    Call<Result> deleteUser(
            @Path("nameID") String nameID,
            @Field("name") String name
    );
}