package nilo.de.cafe.cafedenilopos.CafeDeNilo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;
import nilo.de.cafe.cafedenilopos.API.APIService;
import nilo.de.cafe.cafedenilopos.API.APIUrl;
import nilo.de.cafe.cafedenilopos.PrinterPOS.PrintMainActivity;
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.models.OrderedProducts;
import nilo.de.cafe.cafedenilopos.models.QueueOrder;
import nilo.de.cafe.cafedenilopos.models.Result;
import nilo.de.cafe.cafedenilopos.models.Transaction;
import nilo.de.cafe.cafedenilopos.pos.PosActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityPayment extends AppCompatActivity {
    Button btnCharge;
    double payment = 0.00;

    public TextView tvTotal;
    public TextView tvCash;
    public String date2;
    double cash = 0.00;

    int TransID = 0;
    int QueueID = 0;
    int x ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnCharge = findViewById(R.id.btnchargefinal);

        setTitle("Payment");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvCash = (TextView) findViewById(R.id.txtCash);

        payment = getIntent().getDoubleExtra("payment", 0.00);
        tvTotal.setText("₱" + payment);

        btnCharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cash = Double.parseDouble(tvCash.getText().toString());
                if(payment > cash)
                {
                    Toasty.error(ActivityPayment.this,  "Insufficient Cash", Toasty.LENGTH_SHORT, true).show();
                }

                else
                {
                    addTransactiion();

                    addQueue();
                    //addQueueOrderTransaction();

                    Intent intent = new Intent(ActivityPayment.this, PrintMainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private  void addOrderTransaction()
    {

        for(x = 0; x < PosActivity.listName.size() ; x++)
        {
        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
            Toast.makeText(ActivityPayment.this, TransID+"", Toast.LENGTH_SHORT).show();
        OrderedProducts transaction = new OrderedProducts(
                TransID,
                PosActivity.listName.get(x),
                Double.parseDouble(PosActivity.listPrice.get(x)),
                Double.parseDouble(PosActivity.listQuantity.get(x)),
                date2);

        //defining the call
        Call<Result> call = service.createOrderTrans(
                transaction.getTransaction_id(),
                transaction.getProduct_name(),
                transaction.getPrice(),
                transaction.getQuantity(),
                transaction.getCreated_at()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                //displaying the message from the response as toast

                //Toast.makeText(getApplicationContext(), response.body().getTransID()+"", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        }
    }
    //Queue
    private  void addQueueOrderTransaction()
    {
        for(x = 0; x < PosActivity.listName.size() ; x++)
        {
            //building retrofit object
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(APIUrl.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //Defining retrofit api service
            APIService service = retrofit.create(APIService.class);

            //Defining the user object as we need to pass it with the call
            QueueOrder queueOrder = new QueueOrder(
                    QueueID,
                    PosActivity.listName.get(x),
                    Integer.parseInt(
                            PosActivity.listQuantity.get(x))
                    );

            //defining the call
            Call<Result> call = service.createQueueOrder(
                    queueOrder.getQueue_number(),
                    queueOrder.getItem_name(),
                    queueOrder.getQuantity()
            );

            //calling the api
            call.enqueue(new Callback<Result>() {
                @Override
                public void onResponse(Call<Result> call, Response<Result> response) {

                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onFailure(Call<Result> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //add Queue
    private void addQueue (){

        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call

        //defining the call
        Call<Result> call = service.createQueue(null);

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                //displaying the message from the response as toast
                QueueID = Integer.valueOf(response.body().getQueueID());
                addQueueOrderTransaction();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void addTransactiion (){

        //defining a progress dialog to show while signing up


        //getting the user values
        double cash = Double.parseDouble( tvCash.getText().toString().trim());
        //Date
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date2 = dateFormat.format(date);
        double total = PosActivity.sum;


        //building retrofit object
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        APIService service = retrofit.create(APIService.class);

        //Defining the user object as we need to pass it with the call
        Transaction transaction = new Transaction(date2, cash, total, MainActivity.email);
        //defining the call
        Call<Result> call = service.createTransaction(
                transaction.getDate(),
                transaction.getCash(),
                transaction.getTotal_price(),
                transaction.getEmail()
        );

        //calling the api
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {

                //displaying the message from the response as toast
                TransID = Integer.valueOf(response.body().getTransid());
                addOrderTransaction();
                //Toast.makeText(getApplicationContext(), response.body().getTransID()+"", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
        {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
