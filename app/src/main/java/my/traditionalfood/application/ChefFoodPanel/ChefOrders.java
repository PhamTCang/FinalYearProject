package my.traditionalfood.application.ChefFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import my.traditionalfood.application.ChefFunctions.ChefAcceptedOrder;
import my.traditionalfood.application.R;

public class ChefOrders extends Fragment {

    TextView AcceptedOrder, PreparedOrder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.chef_orders, null);
        getActivity().setTitle("Orders");

        AcceptedOrder=(TextView)v.findViewById(R.id.AcceptedOrder);
        PreparedOrder=(TextView)v.findViewById(R.id.PreparedOrder);

        AcceptedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ChefAcceptedOrder.class);
                startActivity(i);
            }
        });

        PreparedOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ChefPreparedOrder.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
