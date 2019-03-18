package nilo.de.cafe.cafedenilopos.pos;

import android.content.Intent;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.adapter.OrderAdapter;
import nilo.de.cafe.cafedenilopos.models.Products;


public class PosActivity extends AppCompatActivity implements MainContract.MainView {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private Button btnPayment;
    public static TextView ticketNum;
    public static LinearLayout btnTicket;
    private MainContract.presenter presenter;

    OrderAdapter adapter;
    //Adapter
    double payment = 0.00;
    MaterialDialog dialog;

    public  int count = 0;
    public static double sum = 0.00;
    public static ArrayList<String> listID = new ArrayList<>();
    public static ArrayList<String> listName = new ArrayList<>();
    public static ArrayList<String> listPrice = new ArrayList<>();
    public static ArrayList<String> listQuantity = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        setTitle("");
        initializeToolbarAndRecyclerView();
        initProgressBar();

        sum = 0.00;
        listName.clear();
        listID.clear();
        listPrice.clear();
        listQuantity.clear();

        ticketNum = (TextView) findViewById(R.id.ticketNum);
        btnTicket = (LinearLayout) findViewById(R.id.btnDan);

        presenter = new MainPresenterImpl(this, new GetNoticeIntractorImpl());
        presenter.requestDataFromServer();

        btnPayment = findViewById(R.id.btnPayment);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PosActivity.this, ActivityPayment.class);
                intent.putExtra("payment", sum);
                startActivity(intent);
            }
        });

        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(PosActivity.this, ActivityTicket.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    /**
     * Initializing Toolbar and RecyclerView
     */
    private void initializeToolbarAndRecyclerView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recycler_view_employee_list2);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(PosActivity.this);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * Initializing progressbar programmatically
     * */
    private void initProgressBar() {
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.setGravity(Gravity.CENTER);
        relativeLayout.addView(progressBar);

        RelativeLayout.LayoutParams params = new
                RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        progressBar.setVisibility(View.INVISIBLE);

        this.addContentView(relativeLayout, params);
    }

    /**
     * RecyclerItem click event listener
     * */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(final Products products) {
            if(products.getAvailability().equals("available"))
            {
                boolean wrapInScrollView = true;
                dialog = new MaterialDialog.Builder(PosActivity.this)
                        .title(products.getProduct_name())
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
                                ticketNum.setText(count+"");
                                //Toasty.success(PosActivity.this, ((products.getPrice()* .12)+products.getPrice())+"", Toasty.LENGTH_LONG).show();

                                sum  = (Integer.parseInt(i) * (products.getPrice()*.12)+products.getPrice()) + sum ;
                                btnPayment.setText("Charge ₱" + sum);

                                if(listName.contains(products.getProduct_name()))
                                {
                                    int y = listName.indexOf(products.getProduct_name());
                                    double x = ((products.getPrice() + (products.getPrice() *.12)) * (Integer.parseInt (i))) + Double.parseDouble(listPrice.get(y));
                                    int z = (Integer.parseInt(listQuantity.get(y))) + (Integer.parseInt(i));

                                    listPrice.set(y, (x+""));
                                    listQuantity.set(y, (z+""));
                                }

                                else
                                {
                                    listID.add(products.getId()+"");
                                    //Toast.makeText(context, model.getQuan()+"", Toast.LENGTH_SHORT).show();
                                    listName.add(products.getProduct_name());
                                    listPrice.add((Integer.parseInt(i) * (products.getPrice() + (products.getPrice() *.12))) + "");
                                    listQuantity.add(i);
                                }
                                presenter.onRefreshButtonClick();
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
            else
            {
                Toasty.error(PosActivity.this, "Product Unavailable!", Toasty.LENGTH_LONG,true).show();
            }

        }
    };

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setDataToRecyclerView(ArrayList<Products> productsArrayList) {

         adapter = new OrderAdapter(productsArrayList, recyclerItemClickListener);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onResponseFailure(Throwable throwable) {
        Toast.makeText(PosActivity.this,
                "Something went wrong...Error message: " + throwable.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

}


