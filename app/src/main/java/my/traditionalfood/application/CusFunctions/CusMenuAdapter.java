package my.traditionalfood.application.CusFunctions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import my.traditionalfood.application.Food.FoodMenu;
import my.traditionalfood.application.R;

public class CusMenuAdapter extends RecyclerView.Adapter<CusMenuAdapter.ViewHolder> {

    private Context mcontext;
    private List<FoodMenu>cusmenuslist;
    DatabaseReference DbRef;

    public CusMenuAdapter(Context context , List<FoodMenu>cusmenuslist) {

        this.cusmenuslist = cusmenuslist;
        this.mcontext = context;
    }



    @NonNull
    @Override
    public CusMenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.customer_homemenu_upload,parent,false);
        return new CusMenuAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CusMenuAdapter.ViewHolder holder, int position) {

        final FoodMenu foodMenu = cusmenuslist.get(position);
        Glide.with(mcontext).load(foodMenu.getImageURL()).into(holder.ImgView);
        holder.FoodName.setText(foodMenu.getPrice());
        foodMenu.getRandomUID();
        foodMenu.getChefId();
        holder.Price.setText("Price: " + foodMenu.getPrice()+ "VND");
    }

    @Override
    public int getItemCount() {
        return  cusmenuslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ImgView;
        TextView FoodName, Price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ImgView = itemView.findViewById(R.id.cus_menu_img);
            FoodName = itemView.findViewById(R.id.cus_menu_food_name);
            Price = itemView.findViewById(R.id.cus_menu_price);

        }
    }
}
