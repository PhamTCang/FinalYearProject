package my.traditionalfood.application.ChefFunctions;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import my.traditionalfood.application.Food.ChefWaitingOrders1;
import my.traditionalfood.application.R;

public class ChefAcceptedOrder extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ChefWaitingOrders1> chefWaitingOrders1List;
    private ChefAcceptedOrderAdapter adapter;
    private DatabaseReference DbRef;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceStage) {
        super.onCreate(savedInstanceStage);
        setContentView(R.layout.activity_chef_accepted_order);

        recyclerView = findViewById(R.id.Recycle_AcceptedOrder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChefAcceptedOrder.this));
        chefWaitingOrders1List = new ArrayList<>();
        swipeRefreshLayout = findViewById(R.id.Swipe1);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark, R.color.gray);
        adapter = new ChefAcceptedOrderAdapter(ChefAcceptedOrder.this, chefWaitingOrders1List);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                chefWaitingOrders1List.clear();
                recyclerView = findViewById(R.id.Recycle_AcceptedOrder);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChefAcceptedOrder.this));
                chefWaitingOrders1List = new ArrayList<>();
                ChefAcceptedOrder();
            }
        });
        ChefAcceptedOrder();

    }

    private void ChefAcceptedOrder() {
        DbRef = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        DbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    chefWaitingOrders1List.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        DatabaseReference data = FirebaseDatabase.getInstance().getReference("ChefWaitingOrders")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(snapshot.getKey())
                                .child("OtherInformation");
                        data.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                ChefWaitingOrders1 chefWaitingOrders1 = snapshot.getValue(ChefWaitingOrders1.class);
                                chefWaitingOrders1List.add(chefWaitingOrders1);
                                adapter = new ChefAcceptedOrderAdapter(ChefAcceptedOrder.this, chefWaitingOrders1List);
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout.setRefreshing(false);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
