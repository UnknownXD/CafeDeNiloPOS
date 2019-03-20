package nilo.de.cafe.cafedenilopos.ProductsMVP;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import nilo.de.cafe.cafedenilopos.API.APIService;
import nilo.de.cafe.cafedenilopos.API.APIUrl;
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.models.ProductCategories;
import nilo.de.cafe.cafedenilopos.models.ProductCategory;
import nilo.de.cafe.cafedenilopos.models.Result;
import nilo.de.cafe.cafedenilopos.pos.PosActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentProducts extends Fragment {

    public static Button btnOrder;

    public FragmentProducts() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnOrder = view.findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrderTransaction();


            }
        });
    }

    private void addOrderTransaction() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<Result> call = service.createVirtual();


        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                Intent intent = new Intent(getActivity(), PosActivity.class);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toasty.error(FragmentProducts.this.getContext(), t.getMessage(), 1, true).show();
            }
        });

    }
}
