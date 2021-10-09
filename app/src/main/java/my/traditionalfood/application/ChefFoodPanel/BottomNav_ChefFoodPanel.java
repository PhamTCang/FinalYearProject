package my.traditionalfood.application.ChefFoodPanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import my.traditionalfood.application.ChefFoodPanel.ChefHome;
import my.traditionalfood.application.ChefFoodPanel.ChefOrders;
import my.traditionalfood.application.ChefFoodPanel.ChefPendingOrder;
import my.traditionalfood.application.ChefFoodPanel.ChefProfile;
import my.traditionalfood.application.CusFoodPanel.CusHomeMenu;
import my.traditionalfood.application.CusFoodPanel.CusTrackOrders;
import my.traditionalfood.application.R;

public class BottomNav_ChefFoodPanel extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav_chef_food_panel);

        BottomNavigationView navView = findViewById(R.id.chef_bottom_nav);
        navView.setOnItemSelectedListener(this);

        String PageName = getIntent().getStringExtra("Page");
        if(PageName != null) {
            if (PageName.equalsIgnoreCase("OrderPage")) {
                loadfragment(new ChefPendingOrder());
            } else if (PageName.equalsIgnoreCase("ConfirmPage")) {
                loadfragment(new ChefOrders());
            } else if (PageName.equalsIgnoreCase("AcceptOrderPage")) {
                loadfragment(new ChefOrders());
            }else if(PageName.equalsIgnoreCase("DeliveredPage")) {
                loadfragment(new ChefOrders());
            }
        } else {
            loadfragment(new ChefHome());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment  = null;

        switch (item.getItemId()){

            case R.id.ChefHome:
                fragment = new ChefHome();
                break;
            case R.id.PendingOrdersChef:
                fragment = new ChefPendingOrder();
                break;
            case R.id.OrdersChef:
                fragment = new ChefOrders();
                break;
            case R.id.AddFoodChef:
                fragment = new ChefProfile();
                break;
        }
        return loadfragment(fragment);

    }

    private boolean loadfragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_chef, fragment).commit();
            return true;
        }
        return false;
    }
}