package com.jether.monacoshop.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jether.monacoshop.Activities.MainActivity;
import com.jether.monacoshop.Models.ModelCartItem;
import com.jether.monacoshop.R;

import java.util.ArrayList;

import p32929.androideasysql_library.EasyDB;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.Viewholder>{

    private Context context;
    private ArrayList<ModelCartItem> cartItems;

    public AdapterCartItem(Context context, ArrayList<ModelCartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_cart item xml
        return  new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cartitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        // get data
        ModelCartItem modelCartItem= cartItems.get(position);

        final String id= modelCartItem.getId();
        String getpId=modelCartItem.getPid();
        String title =modelCartItem.getName();
        String price=modelCartItem.getPrice();
        String cost=modelCartItem.getTotal();
        String quantity=modelCartItem.getQuantity();

        //set data
        holder.itemTitleTv.setText(""+title);
        holder.itemPriceTv.setText(""+cost);
        holder.itemQuantityTv.setText("["+quantity+"]");
        holder.itemPriceEachTv.setText(""+price);

        // handle remove click listener, delete item from cart

        holder.itemRemoveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // wil create table if does not exist,but in case must exist
                EasyDB easyDB=EasyDB.init(context,"ITEMS_DB")
                        .setTableName("ITEMS_TABLE")
                        .addColumn("Item_id",new String[]{"text","unique"})
                        .addColumn("Item_PID",new String[]{"text","not null"})
                        .addColumn("Item_Name",new String[]{"text","not null"})
                        .addColumn("Item_Price_Each",new String[]{"text","not null"})
                        .addColumn("Item_Price",new String[]{"text","not null"})
                        .addColumn("Item_Quantity",new String[]{"text","not null"})
                        .doneTableColumn();

                easyDB.deleteRow(1,id);
                Toast.makeText(context, "Removed from cart...",Toast.LENGTH_SHORT).show();

                // refresh list
                cartItems.remove(position);
                notifyItemChanged(position);
                notifyDataSetChanged();

                double tx=Double.parseDouble((((MainActivity)context).allTotalPriceTv.getText().toString().trim().replace("Shs","")));
                double totalPrice = tx-Double.parseDouble(cost.replace("Shs",""));
                double sTotalPrice= Double.parseDouble(String.format("%.2f",totalPrice));
                ((MainActivity)context).allTotalPrice=0.00;
                ((MainActivity)context).sTotalTv.setText("Shs"+String.format("%.2f",sTotalPrice));
                ((MainActivity)context).allTotalPriceTv.setText("Shs"+String.format("%.2f",Double.parseDouble(String.format("%.2f",totalPrice))));

                // after removing item from cart,update count
                ((MainActivity)context).cartCount();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        private TextView itemTitleTv,itemPriceTv,itemPriceEachTv,itemQuantityTv;
        private ImageButton itemRemoveTv;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            itemTitleTv=itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv=itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv=itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantityTv=itemView.findViewById(R.id.itemQuantityTv);
            itemRemoveTv=itemView.findViewById(R.id.itemRemoveTv);
        }
    }
}
