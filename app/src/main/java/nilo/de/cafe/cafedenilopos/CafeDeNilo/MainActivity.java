package nilo.de.cafe.cafedenilopos.CafeDeNilo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nilo.de.cafe.cafedenilopos.ProductsMVP.FragmentProducts;
import nilo.de.cafe.cafedenilopos.R;
import nilo.de.cafe.cafedenilopos.helper.SharedPrefManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPrefManager session;

    LinearLayout btnSales;
    public static TextView ticketNum;
    public static LinearLayout btnTicket;
    public static String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // session manager
        session = new SharedPrefManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.txtUser);
        TextView navEmail = (TextView) headerView.findViewById(R.id.txtEmail);
        email = SharedPrefManager.getInstance(this).getUser().getEmail();
        String name = SharedPrefManager.getInstance(this).getUser().getFirst_name()+", "+SharedPrefManager.getInstance(this).getUser().getLast_name() ;
        navEmail.setText(email);
        navUsername.setText(name.toUpperCase());

        ticketNum = (TextView) findViewById(R.id.ticketNum);
        btnTicket = (LinearLayout) findViewById(R.id.btnDan);

        btnSales = findViewById(R.id.btnDan);
        btnSales.setVisibility(View.GONE);

        btnTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ActivityTicket.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void logout() {
        SharedPrefManager.getInstance(this).logout();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void logoutUser() {
        session.setLogin(false);


        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
            logout();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;


        if (id == R.id.nav_pos2)
        {
            fragment = new FragmentProducts();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.screen_area, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
