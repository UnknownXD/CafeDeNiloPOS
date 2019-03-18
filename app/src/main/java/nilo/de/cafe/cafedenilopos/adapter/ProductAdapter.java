package nilo.de.cafe.cafedenilopos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.ActivityPayment;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.ActivityTicket;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.MainActivity;
import nilo.de.cafe.cafedenilopos.ProductsMVP.FragmentProducts;
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.models.Products;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    public static ArrayList<Products> dataList;

    private Context context;
    View mView;

    MaterialDialog dialog;

    public static int count = 0;
    static public double sum = 0.00;
    public static ArrayList<String> listID = new ArrayList<>();
    public static ArrayList<String> listName = new ArrayList<>();
    public static ArrayList<String> listPrice = new ArrayList<>();
    public static ArrayList<String> listQuantity = new ArrayList<>();
    public static ArrayList<Integer> listDefaultQuantity = new ArrayList<>();

    public ProductAdapter(ArrayList<Products> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        mView = layoutInflater.inflate(R.layout.rv_products, parent, false);
        return new ProductViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {

        holder.id = dataList.get(position).getId();

        //holder.txtEmpName.setText(dataList.get(position).getId()+"");
        holder.txtProductName.setText(dataList.get(position).getProduct_name());
        holder.txtProductPrice.setText("₱"+((dataList.get(position).getPrice() * .12)+ dataList.get(position).getPrice()));

        //Data
        final int data = holder.id;
        final String product_name = dataList.get(position).getProduct_name();

        holder.product_clicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean wrapInScrollView = true;
                dialog = new MaterialDialog.Builder(context)
                        .title(product_name)
                        .customView(R.layout.product_quantity, wrapInScrollView)
                        .positiveText("Add")
                        .negativeText("Cancel")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                //ElegantNum
                                View MDView = dialog.getCustomView();
                                ElegantNumberButton numberButton = MDView.findViewById(R.id.numbtn);
                                String i = numberButton.getNumber();

                                count = Integer.parseInt(i) + count;
                                MainActivity.ticketNum.setText(count+"");

                                sum = (Integer.parseInt(i) * dataList.get(position).getPrice()) + sum ;

                                if(listName.contains(dataList.get(position).getProduct_name()))
                                {
                                    int y = listName.indexOf(dataList.get(position).getProduct_name());
                                    double x = (dataList.get(position).getPrice() * (Integer.parseInt (i))) + Double.parseDouble(listPrice.get(y));
                                    int z = (Integer.parseInt(listQuantity.get(y))) + (Integer.parseInt(i));

                                    listPrice.set(y, (x+""));
                                    listQuantity.set(y, (z+""));
                                }

                                else
                                {
                                    listID.add(holder.id+"");

                                    //Toast.makeText(context, model.getQuan()+"", Toast.LENGTH_SHORT).show();
                                    listName.add(dataList.get(position).getProduct_name());
                                    listPrice.add(+Integer.parseInt(i) * dataList.get(position).getPrice() + "");
                                    listQuantity.add(i);
                                }
                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .onAny(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductPrice, txtProductName;
        int id;
        String z;
        int num;
        RelativeLayout product_clicked;

        ProductViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.item_name);
            txtProductPrice = (TextView) itemView.findViewById(R.id.price);
            product_clicked = (RelativeLayout) itemView.findViewById(R.id.product_clicked);
        }
    }
}