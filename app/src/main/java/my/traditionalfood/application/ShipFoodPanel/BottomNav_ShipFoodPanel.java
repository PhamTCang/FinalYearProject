package my.traditionalfood.application.ShipFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import my.traditionalfood.application.ChefFoodPanel.ChefPendingOrder;
import my.traditionalfood.application.CusFoodPanel.CusHomeMenu;
import my.traditionalfood.application.CusFoodPanel.CusOrders;
import my.traditionalfood.application.CusFoodPanel.CusProfile;
import my.traditionalfood.application.CusFoodPanel.CusTrackOrders;
import my.traditionalfood.application.R;
import my.traditionalfood.application.ShipFoodPanel.ShipPendingOrders;
import my.traditionalfood.application.ShipFoodPanel.ShipProfile;
import my.traditionalfood.application.ShipFoodPanel.ShippingOrders;

public class BottomNav_ShipFoodPanel extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_ship_food_panel);

        BottomNavigationView navView = findViewById(R.id.ship_bottom_nav);
        navView.setOnItemSelectedListener(this);

        String PageName = getIntent().getStringExtra("Page");
        if (PageName != null) {
            if (PageName.equalsIgnoreCase("ShippingOrderPage")) {
                loadfragment(new ShipPendingOrders());
            }

        }else{
            loadfragment(new ShipPendingOrders());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment  = null;

        switch (item.getItemId()){

            case R.id.ShipOrder:
                fragment = new ShipPendingOrders();
                break;
            case R.id.Shipping:
                fragment = new ShippingOrders();

            case R.id.ShipProfile:
                fragment = new ShipProfile();
                break;
        }
        return loadfragment(fragment);

    }

    private boolean loadfragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_ship, fragment).commit();
            return true;
        }
        return false;
    }
}