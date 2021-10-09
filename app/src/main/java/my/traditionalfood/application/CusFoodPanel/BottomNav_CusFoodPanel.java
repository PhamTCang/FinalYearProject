package my.traditionalfood.application.CusFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import my.traditionalfood.application.ChefFoodPanel.ChefPendingOrder;
import my.traditionalfood.application.CusFoodPanel.CusCart;
import my.traditionalfood.application.CusFoodPanel.CusHomeMenu;
import my.traditionalfood.application.CusFoodPanel.CusOrders;
import my.traditionalfood.application.CusFoodPanel.CusProfile;
import my.traditionalfood.application.CusFoodPanel.CusTrackOrders;
import my.traditionalfood.application.R;

public class BottomNav_CusFoodPanel extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_cus_food_panel);

        BottomNavigationView navView = findViewById(R.id.cus_bottom_nav);
        navView.setOnItemSelectedListener(this);

        String PageName = getIntent().getStringExtra("Page");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(PageName != null) {
            if (PageName.equalsIgnoreCase("HomePage")) {
                loadfragment(new CusHomeMenu());
            } else if (PageName.equalsIgnoreCase("FoodCartPage")) {
                loadfragment(new CusTrackOrders());
            } else if (PageName.equalsIgnoreCase("TrackOrderPage")) {
                loadfragment(new CusTrackOrders());
            } else if (PageName.equalsIgnoreCase("DeliverOrderPage")) {
                loadfragment(new CusTrackOrders());
            }else if(PageName.equalsIgnoreCase("ThankYouPage")) {
                loadfragment(new CusHomeMenu());
            }
        } else {
            loadfragment(new CusHomeMenu());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment  = null;

        switch (item.getItemId()){

            case R.id.CusHome:
                fragment = new CusHomeMenu();
                break;
            case R.id.CartCus:
                fragment = new ChefPendingOrder();
                break;
            case R.id.PendingOrderCus:
                fragment = new CusOrders();
                break;
            case R.id.Track:
                fragment = new CusTrackOrders();
                break;
            case R.id.CusProfile:
                fragment = new CusProfile();
                break;
        }
        return loadfragment(fragment);

    }

    private boolean loadfragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_cus, fragment).commit();
            return true;
        }
        return false;
    }
}