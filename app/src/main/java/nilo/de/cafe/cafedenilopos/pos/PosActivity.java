package nilo.de.cafe.cafedenilopos.pos;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.angmarch.views.NiceSpinner;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import nilo.de.cafe.cafedenilopos.API.APIService;
import nilo.de.cafe.cafedenilopos.API.APIUrl;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.ActivityPayment;
import nilo.de.cafe.cafedenilopos.CafeDeNilo.ActivityTicket;
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.adapter.OrderAdapter;
import nilo.de.cafe.cafedenilopos.models.Item;
import nilo.de.cafe.cafedenilopos.models.ProductCategories;
import nilo.de.cafe.cafedenilopos.models.ProductCategory;
import nilo.de.cafe.cafedenilopos.models.Products;
import nilo.de.cafe.cafedenilopos.models.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
    NiceSpinner niceSpinner;
    ArrayList<String> spinneradapter = new ArrayList<>();

    public int count = 0;
    public static double sumunvatted = 0.00;
    public static double sum = 0.00;
    public static ArrayList<String> listID = new ArrayList();
    public static ArrayList<String> listName = new ArrayList();
    public static ArrayList<String> listPrice = new ArrayList();
    public static ArrayList<String> listPriceunvatted = new ArrayList();
    public static ArrayList<String> listQuantity = new ArrayList();
    public Period period = Period.ZERO;
    public static String finaldate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        setTitle("");
        initializeToolbarAndRecyclerView();
        initProgressBar();
        spinneradapter.clear();
        load();

        sum = 0.00;
        sumunvatted = 0.00;
        listName.clear();
        listID.clear();
        listPrice.clear();
        listQuantity.clear();
        niceSpinner = (NiceSpinner) findViewById(R.id.spinner);

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
            public void onClick(View v) {
                Intent intent = new Intent(PosActivity.this, ActivityTicket.class);
                intent.putExtra("payment", sum);
                startActivity(intent);
            }
        });

        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toasty.info(PosActivity.this, spinneradapter.get(position)+"", Toast.LENGTH_LONG).show();
                if(spinneradapter.get(position).equals("All Products"))
                {
                    adapter.getSpinnerFilter().filter("");
                }

                else
                {
                    adapter.getSpinnerFilter().filter(spinneradapter.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
     */
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
     */
    private RecyclerItemClickListener recyclerItemClickListener = new RecyclerItemClickListener() {
        @Override
        public void onItemClick(final Products products) {
            if (products.getAvailability().equals("available")) {
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
                                final int i = Integer.parseInt(numberButton.getNumber());
                                int id = products.getId();


                                //Dan
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl(APIUrl.BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build();

                                //Defining retrofit api service
                                APIService service = retrofit.create(APIService.class);
                                //Defining the user object as we need to pass it with the call
                                Item item = new Item(
                                        id, i, id);

                                //defining the call
                                Call<Result> call = service.updateVTItems(
                                        item.getProductId(), item.getQuantity(), item.getId()
                                );

                                //calling the api
                                call.enqueue(new Callback<Result>() {
                                    @Override
                                    public void onResponse(Call<Result> call, Response<Result> response) {
                                        if (response.body().getMessage().equals("success")) {
                                            count = i + count;
                                            ticketNum.setText(count + "");
                                            //Toasty.success(PosActivity.this, ((products.getPrice()* .12)+products.getPrice())+"", Toasty.LENGTH_LONG).show();
                                            double toDouble = (i * ((products.getPrice() * .12) + products.getPrice())) + sum;
                                            double toDoubleUnvatted = (i * products.getPrice()) + sumunvatted;
                                            sum = Double.parseDouble(String.format("%.2f", toDouble));
                                            sumunvatted = Double.parseDouble(String.format("%.2f", toDoubleUnvatted));
                                            btnPayment.setText("Charge ₱" + sum);

                                            if (listName.contains(products.getProduct_name())) {
                                                int y = listName.indexOf(products.getProduct_name());
                                                double x = ((products.getPrice() + (products.getPrice() * .12)) * (i)) + Double.parseDouble(listPrice.get(y));
                                                int z = (Integer.parseInt(listQuantity.get(y))) + (i);

                                                listPrice.set(y, String.format("%.2f", x));

                                                listQuantity.set(y, (z + ""));
                                            } else {
                                                listID.add(products.getId() + "");
                                                //Toast.makeText(context, model.getQuan()+"", Toast.LENGTH_SHORT).show();
                                                listName.add(products.getProduct_name());
                                                listPrice.add(String.format("%.2f", (i * (products.getPrice() + (products.getPrice() * .12)))));
                                                listQuantity.add(i + "");
                                            }

                                            PeriodFormatter parser = new PeriodFormatterBuilder().appendHours().appendLiteral(":").appendMinutes().appendLiteral(":").appendSeconds().toFormatter();
                                            PosActivity.this.period = PosActivity.this.period.plus(parser.parsePeriod(products.getPrepare_time()));
                                            PeriodFormatter printer = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2).appendHours().appendLiteral(":").appendMinutes().appendLiteral(":").appendSeconds().toFormatter();
                                            try {
                                                StringBuilder stringBuilder3 = new StringBuilder();
                                                stringBuilder3.append(printer.print(PosActivity.this.period.normalizedStandard(PeriodType.time())));
                                                stringBuilder3.append("");
                                                finaldate = stringBuilder3.toString();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            presenter.onRefreshButtonClick();
                                            spinneradapter.clear();
                                            load();
                                            btnPayment.setEnabled(true);
                                            Toasty.info(PosActivity.this, finaldate+"", Toasty.LENGTH_SHORT).show();
                                            return;
                                        }
                                        Toasty.error(PosActivity.this, (CharSequence) "insufficient item in inventory", 1, true).show();
                                    }

                                    @Override
                                    public void onFailure(Call<Result> call, Throwable t) {

                                        Toasty.error(PosActivity.this, (CharSequence) "insufficient item in inventory", 1, true).show();
                                    }
                                });
                                //dan



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
            } else {
                Toasty.error(PosActivity.this, "Product Unavailable!", Toasty.LENGTH_LONG, true).show();
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

    private void load() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIService service = retrofit.create(APIService.class);

        Call<ProductCategories> call = service.getproductscategory();

        call.enqueue(new Callback<ProductCategories>() {
            @Override
            public void onResponse(Call<ProductCategories> call, Response<ProductCategories> response) {

                List<ProductCategory> users = response.body().getCategoryName();

                spinneradapter.add("All Products");
                             String[] user = new String[users.size()];
                             for(int i = 0; i <users.size(); i++)
                             {
                                 spinneradapter.add(users.get(i).getCategory_name());
                                 //user[i] =
                                 //Toast.makeText(PosActivity.this, users.get(i).getCategory_name()+"", Toast.LENGTH_SHORT).show();
                             }
                niceSpinner.attachDataSource(spinneradapter);
                // List<ProductCategory> users = response.body().getCategoryName();
                // Toast.makeText(PosActivity.this, +"", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<ProductCategories> call, Throwable t) {
                Toast.makeText(PosActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onBackPressed() {
        this.dialog = new MaterialDialog.Builder(this).title((CharSequence) "Quit").content((CharSequence) "Are you sure you want to cancel this order?").positiveText((CharSequence) "Yes").negativeText((CharSequence) "No").onPositive(new MaterialDialog.SingleButtonCallback() {
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                PosActivity.this.finish();
            }
        }).onNeutral(new MaterialDialog.SingleButtonCallback() {
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            }
        }).onNegative(new MaterialDialog.SingleButtonCallback() {
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            }
        }).onAny(new MaterialDialog.SingleButtonCallback() {
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
            }
        }).show();
    }

    public void dan()
    {

    }
    }
