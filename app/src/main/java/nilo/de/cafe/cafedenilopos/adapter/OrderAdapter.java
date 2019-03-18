package nilo.de.cafe.cafedenilopos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.models.Products;
import nilo.de.cafe.cafedenilopos.pos.RecyclerItemClickListener;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.EmployeeViewHolder> implements Filterable {


    private ArrayList<Products> dataList;
    private ArrayList<Products> dataListFull;
    private RecyclerItemClickListener recyclerItemClickListener;
    Context context;
    int x = 0;

    public OrderAdapter(ArrayList<Products> dataList , RecyclerItemClickListener recyclerItemClickListener) {
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
        this.recyclerItemClickListener = recyclerItemClickListener;
    }


    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.rv_products, parent, false);
        context = parent.getContext();
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txtProductName.setText(dataList.get(position).getProduct_name()+"");
        holder.txtProductPrice.setText("₱"+((dataList.get(position).getPrice() * .12)+ dataList.get(position).getPrice()+""));
        if(dataList.get(position).getAvailability().equals("not available"))
        {
            holder.item_clicked.setEnabled(false);
            holder.txtProductName.setTextColor(Color.GRAY);
        }
        else
        {
            holder.item_clicked.setEnabled(true);
            holder.txtProductName.setTextColor(Color.BLACK);
        }
        //holder.txtProductPrice.setText(dataList.get(position).getCategory()+"");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerItemClickListener.onItemClick(dataList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductPrice, txtProductName;
        RelativeLayout item_clicked;

        EmployeeViewHolder(View itemView) {
            super(itemView);
            txtProductName = (TextView) itemView.findViewById(R.id.item_name);
            txtProductPrice = (TextView) itemView.findViewById(R.id.price);
            item_clicked = (RelativeLayout) itemView.findViewById(R.id.product_clicked);

        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public Filter getSpinnerFilter() {
        return SpinnerFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList <Products> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(dataListFull);
            }

            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Products item : dataListFull)
                {
                    if(item.getProduct_name().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values =filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    private Filter SpinnerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList <Products> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() == 0)
            {
                filteredList.addAll(dataListFull);
            }

            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(Products item : dataListFull)
                {
                    if(item.getCategory().toLowerCase().contains(filterPattern))
                    {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values =filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            dataList.clear();
            dataList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}