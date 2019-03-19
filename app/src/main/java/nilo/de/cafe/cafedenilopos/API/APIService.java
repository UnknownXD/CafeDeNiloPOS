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
    @FormUrlEncoded
    @POST("ordertransaction")
    Call<Result> createOrderTrans(@Field("transaction_id") int i, @Field("product_name") String str, @Field("price") double d, @Field("quantity") double d2, @Field("created_at") String str2);

    @FormUrlEncoded
    @POST("insertqueue")
    Call<Result> createQueue(@Field("prepare_time") String str);

    @FormUrlEncoded
    @POST("insertqueueorder")
    Call<Result> createQueueOrder(@Field("queue_number") int i, @Field("item_name") String str, @Field("quantity") double d);

    @FormUrlEncoded
    @POST("transaction")
    Call<Result> createTransaction(@Field("date") String str, @Field("cash") Double d, @Field("total_price") Double d2, @Field("email") String str2, @Field("vat") Double d3, @Field("discount") Double d4);

    @GET("createvirtual")
    Call<Result> createVirtual();

    @FormUrlEncoded
    @POST("name")
    Call<Result> createnames(@Field("name") String str);

    @FormUrlEncoded
    @POST("deleteuser/{nameID}")
    Call<Result> deleteUser(@Path("nameID") String str, @Field("name") String str2);

    @GET("products")
    Call<ProductList> getProducts();

    @GET("productscategory")
    Call<ProductCategories> getproductscategory();

    @FormUrlEncoded
    @POST("update/{nameID}")
    Call<Result> updateUser(@Path("nameID") String str, @Field("name") String str2);

    @FormUrlEncoded
    @POST("update/{productId}")
    Call<Result> updateVTItems(@Path("productId") int i, @Field("quantity") int i2, @Field("productId") int i3);

    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(@Field("email") String str, @Field("password") String str2);
}
