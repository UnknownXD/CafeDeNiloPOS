package nilo.de.cafe.cafedenilopos.pos;

import android.util.Log;


import nilo.de.cafe.cafedenilopos.API.APIService;
import nilo.de.cafe.cafedenilopos.API.APIUrl;
import nilo.de.cafe.cafedenilopos.models.ProductList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bpn on 12/7/17.
 */

public class GetNoticeIntractorImpl implements MainContract.GetNoticeIntractor {
    @Override
    public void getNoticeArrayList(final OnFinishedListener onFinishedListener) {

        /** Create handle for the RetrofitInstance interface*/
        APIService service = APIUrl.getRetrofitInstance().create(APIService.class);

        /** Call the method with parameter in the interface to get the notice data*/
        Call<ProductList> call = service.getProducts();

        /**Log the URL called*/
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ProductList>() {
            @Override
            public void onResponse(Call<ProductList> call, Response<ProductList> response) {
                onFinishedListener.onFinished(response.body().getProductList());

            }

            @Override
            public void onFailure(Call<ProductList> call, Throwable t) {
                onFinishedListener.onFailure(t);
            }
        });

    }

}
