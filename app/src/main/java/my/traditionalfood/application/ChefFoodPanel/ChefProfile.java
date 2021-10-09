package my.traditionalfood.application.ChefFoodPanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import my.traditionalfood.application.ChefFunctions.ChefAddFood;
import my.traditionalfood.application.R;

public class ChefProfile extends Fragment {

    Button AddFood;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.chef_addfood, null);
        getActivity().setTitle("Add Food");

        AddFood = (Button)v.findViewById(R.id.add_food);
        AddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getContext(), ChefAddFood.class));

            }
        });

        return v;
    }
}
