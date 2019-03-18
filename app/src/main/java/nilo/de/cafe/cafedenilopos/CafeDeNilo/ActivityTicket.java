package nilo.de.cafe.cafedenilopos.CafeDeNilo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.pos.PosActivity;

public class  ActivityTicket extends AppCompatActivity {
    static SwipeMenuListView listView;
    public static Button btnPayment;
    FrameLayout btnPayment2;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        setTitle("Ticket");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        listView = findViewById(R.id.lvItems);

        SwipeMenuCreator creator = new SwipeMenuCreator()
        {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth((160));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_action_del);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        CustomAdapter listAdapter = new CustomAdapter();
        listView.setMenuCreator(creator);
        listView.setAdapter(listAdapter);

        btnPayment2 = findViewById(R.id.btnCharge2);
        btnPayment2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ActivityTicket.this, ActivityPayment.class);
                startActivity(intent);
            }
        });
        btnPayment = findViewById(R.id.btnCharge2a);
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityTicket.this, ActivityPayment.class);
                startActivity(intent);
            }
        });
        if(listAdapter.isEmpty())
        {
            btnPayment.setTextColor(getResources().getColor(R.color.gray));
            btnPayment.setEnabled(false);
            btnPayment2.setEnabled(false);
        }

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

    class CustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return PosActivity.listName.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.customlayout,null);

            TextView tvName = (TextView) view.findViewById(R.id.textview_Name);
            TextView tvDesc = (TextView) view.findViewById(R.id.textview_Desc);
            TextView tvQuan = (TextView) view.findViewById(R.id.textView_Quantity);

            tvName.setText(PosActivity.listName.get(position));
            tvDesc.setText("₱"+(PosActivity.listPrice.get(position)));
            tvQuan.setText("x "+ PosActivity.listQuantity.get(position));

            return view;
        }
    }
}
