package my.traditionalfood.application.ChefFunctions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import my.traditionalfood.application.Food.ChefWaitingOrders;
import my.traditionalfood.application.R;
import my.traditionalfood.application.SendNotification.APIService;

public class ChefAcceptedOrderViewDetail extends AppCompatActivity {

    RecyclerView recyclerViewdish;
    private List<ChefWaitingOrders> chefWaitingOrdersList;
    private ChefAcceptedOrderViewDetailAdapter adapter;
    DatabaseReference DbRef;
    String RandomUID, userid;
    TextView grandtotal, note, address, name, number;
    LinearLayout l1;
    Button Preparing;
    private ProgressDialog progressDialog;
    private APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_accepted_order_view_detail);


    }
}