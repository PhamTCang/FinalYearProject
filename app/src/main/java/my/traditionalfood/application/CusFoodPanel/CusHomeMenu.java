package my.traditionalfood.application.CusFoodPanel;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import my.traditionalfood.application.CusFunctions.CusInf;
import my.traditionalfood.application.Food.FoodMenu;
import my.traditionalfood.application.CusFunctions.CusMenuAdapter;
import my.traditionalfood.application.R;

public class CusHomeMenu extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView recyclerView;
    private List<FoodMenu> foodMenuList;
    private CusMenuAdapter cusMenuAdapter;
    String City, District;
    DatabaseReference DbRefCus, DbRefFood;
    SwipeRefreshLayout swipeRefreshLayout;


    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.customer_homemenu,null);
        getActivity().setTitle("Food Around You");

        recyclerView = v.findViewById(R.id.recycler_menu);
        recyclerView.setHasFixedSize(true);
        Animation anim = AnimationUtils.loadAnimation(getContext(),R.anim.move);
        recyclerView.startAnimation(anim);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        foodMenuList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout)v.findViewById(R.id.Swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.design_default_color_primary_dark,R.color.purple_white);

        swipeRefreshLayout.post(() -> {

            swipeRefreshLayout.setRefreshing(true);
            String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DbRefCus = FirebaseDatabase.getInstance().getReference("Customer").child(UserId);
            DbRefCus.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    CusInf CustomerInformation = snapshot.getValue(CusInf.class);
                    City = CustomerInformation.getCity();
                    District = CustomerInformation.getDistrict();
                    cushome_menu();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        return v;
    }

    @Override
    public void onRefresh() {
        cushome_menu();
    }

    private void cushome_menu() {

        swipeRefreshLayout.setRefreshing(true);
        DbRefFood = FirebaseDatabase.getInstance().getReference("FoodInf").child(City).child(District);
        DbRefFood.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                foodMenuList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    for (DataSnapshot snapshot2 : snapshot1.getChildren()){
                        FoodMenu foodMenu = snapshot2.getValue(FoodMenu.class);
                        foodMenuList.add(foodMenu);
                    }
                }
                cusMenuAdapter = new CusMenuAdapter(getContext(), foodMenuList);
                recyclerView.setAdapter(cusMenuAdapter);
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
}
