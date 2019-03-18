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

import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.pos.PosActivity;

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
                Intent intent = new Intent(getActivity(), PosActivity.class);
                startActivity(intent);
            }
        });
    }
}
