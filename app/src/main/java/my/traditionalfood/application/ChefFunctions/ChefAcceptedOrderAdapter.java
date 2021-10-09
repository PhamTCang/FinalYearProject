package my.traditionalfood.application.ChefFunctions;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import my.traditionalfood.application.Food.ChefWaitingOrders1;
import my.traditionalfood.application.R;

public class ChefAcceptedOrderAdapter extends RecyclerView.Adapter<ChefAcceptedOrderAdapter.ViewHolder> {


    private Context context;
    private List<ChefWaitingOrders1> chefWaitingOrders1list;


    public ChefAcceptedOrderAdapter(Context context, List<ChefWaitingOrders1> chefWaitingOrders1list) {
        this.chefWaitingOrders1list = chefWaitingOrders1list;
        this.context = context;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.chef_accepted_orders, parent, false);
        return new ChefAcceptedOrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ChefWaitingOrders1 chefWaitingOrders1 = chefWaitingOrders1list.get(position);
        holder.Address.setText(chefWaitingOrders1.getAddress());
        holder.TotalPrice.setText("Total Price: " + chefWaitingOrders1.getGrandTotalPrice() + " VND");
        final String random = chefWaitingOrders1.getRandomUID();
        holder.ViewOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChefAcceptedOrderViewDetail.class);
                intent.putExtra("RandomUID", random);
                context.startActivity(intent);
                ((ChefAcceptedOrder) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {

        return chefWaitingOrders1list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Address, TotalPrice;
        Button ViewOrderBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Address = itemView.findViewById(R.id.CusAddress);
            TotalPrice = itemView.findViewById(R.id.OrderTotalPrice);
            ViewOrderBtn = itemView.findViewById(R.id.ViewOrderBtn);

        }
    }
}