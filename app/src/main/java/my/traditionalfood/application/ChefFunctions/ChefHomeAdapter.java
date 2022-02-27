package my.traditionalfood.application.ChefFunctions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

import my.traditionalfood.application.Food.EditFood;
import my.traditionalfood.application.Food.FoodMenu;
import my.traditionalfood.application.R;

public class ChefHomeAdapter extends RecyclerView.Adapter<ChefHomeAdapter.ViewHolder>{

    private Context FContext;
    private List<FoodMenu> FoodMenuList;
    DatabaseReference DbRef;

    public ChefHomeAdapter(Context context , List<FoodMenu> FoodMenuList) {

        this.FContext = context;
        this.FoodMenuList = FoodMenuList;
    }

    @NonNull
    @Override
    public ChefHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(FContext).inflate(R.layout.chef_home_edit, parent, false);
        return new ChefHomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChefHomeAdapter.ViewHolder holder, int position) {

        final FoodMenu foodMenu = FoodMenuList.get(position);
        holder.ChefFood.setText(foodMenu.getFood());
        foodMenu.getRandomUID();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FContext, EditFood.class);
                intent.putExtra("EditFood", foodMenu.getRandomUID());
                FContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return FoodMenuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView ChefFood;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
                ChefFood = itemView.findViewById(R.id.chef_home_foodname);
        }
    }
}
