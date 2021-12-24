package com.jether.monacoshop.LoggedAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jether.monacoshop.Activities.MainActivity;
import com.jether.monacoshop.Adapters.AdapterAll;
import com.jether.monacoshop.Adapters.AdapterLatest;
import com.jether.monacoshop.DBHelper.DBContact;
import com.jether.monacoshop.Models.ModelLatest;
import com.jether.monacoshop.R;
import com.jether.monacoshop.logged.BottomActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterLatestLogged extends RecyclerView.Adapter<AdapterLatestLogged.Viewholder>{


    private ArrayList<ModelLatest> list;
    private Context context;

    public AdapterLatestLogged(ArrayList<ModelLatest> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.latest,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterLatestLogged.Viewholder holder, int position) {
        ModelLatest modella=list.get(position);
        String name1=modella.getProduct();
        String price3=modella.getPrice();
        String image = modella.getImage();

        String id=modella.getId();
        String cat=modella.getTitle();
        String desc=modella.getDescc();
        String qu=modella.getQuant();

        String me1=modella.getImagee();

        String po=modella.getDate();

        int sync_status=list.get(position).getSync_status();
        if(sync_status == DBContact.SYNC_STATUS_OK){
            holder.sync.setImageResource(R.drawable.ic_baseline_done_24);
        }else{
            holder.sync.setImageResource(R.drawable.ic_baseline_sync_24);
        }

        holder.Name.setText(name1);
        holder.price1.setText(price3);


        //set user dp
        try{
            Picasso.get().load(image).placeholder(R.drawable.ic_shopi).into(holder.Img);

        }catch(Exception e){

        }
        holder.con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent= new Intent(context, ProductDetailActivity.class);
//                intent.putExtra("id",modella.getId());
//                intent.putExtra("title",modella.getTitle());
//                intent.putExtra("product",modella.getProduct());
//                intent.putExtra("price",modella.getPrice());
//                intent.putExtra("descc",modella.getDescc());
//                intent.putExtra("image",modella.getImage());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

                showQant(modella);

            }
        });

    }


    private double cost = 0;
    private double finalCost= 0;
    private int quantity=0;
    private void showQant(ModelLatest modella) {

        //inflate dialog

        View view=LayoutInflater.from(context).inflate(R.layout.quantity_dialog_log,null);

        //init layout
        ImageView productTv= view.findViewById(R.id.productTv);
        final TextView titleTv=view.findViewById(R.id.titleTv);

        TextView descriptionTv=view.findViewById(R.id.descriptionTv);
        final TextView originalPriceTv=view.findViewById(R.id.originalPriceTv);
        final TextView finalPriceTv=view.findViewById(R.id.finalPriceTv);
        ImageButton decreaseBtn=view.findViewById(R.id.decreaseBtn);
        final TextView quantityTv=view.findViewById(R.id.quantityTv);
        ImageButton increaseBtn=view.findViewById(R.id.increaseBtn);
        Button Addto=view.findViewById(R.id.Addto);

        // get data from model

        final String productId=modella.getId();
        String title =modella.getProduct();
        String productQuantity=modella.getQuant();
        String description=modella.getDescc();
        //String discountNote=modelAll.getDiscountNote();
        String image=modella.getImage();
        String price2=modella.getPrice();


        cost=Double.parseDouble(price2.replaceAll("Shs",""));
        finalCost=Double.parseDouble(price2.replaceAll("Shs",""));
        quantity=1;


        //dialog
        AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setView(view);

        try{

            Picasso.get().load(image).placeholder(R.drawable.ic_shopi).into(productTv);


        }catch (Exception e){
            productTv.setImageResource(R.drawable.ic_shopi);
        }

        titleTv.setText(""+title);

        originalPriceTv.setText("Shs"+price2);
        descriptionTv.setText(""+description);
        quantityTv.setText(""+quantity);
        finalPriceTv.setText("Shs"+finalCost);


        final AlertDialog alertDialog=builder.create();
        alertDialog.show();

        //increase quantity of product
        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalCost = finalCost + cost;
                quantity++;

                finalPriceTv.setText("Shs"+finalCost);
                quantityTv.setText(""+quantity);
            }
        });


        //decrement button
        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>1){

                    finalCost = finalCost-cost;
                    quantity--;

                    finalPriceTv.setText("Shs"+finalCost);
                    quantityTv.setText(""+quantity);
                }
            }
        });

        Addto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleTv.getText().toString().trim();
                String priceEach=price2;
                String totalPrice =finalPriceTv.getText().toString().trim().replace("Shs","");
                String quantity=quantityTv.getText().toString().trim();


                //add to db Sqlite
                addToCart(productId,title,priceEach,totalPrice,quantity);


                alertDialog.dismiss();
            }
        });

    }

    private  int itemId=1;
    private void addToCart(String productId, String title, String priceEach, String totalPrice, String quantity) {
        itemId++;

        EasyDB easyDB=EasyDB.init(context,"ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("item_id", new String[]{"text","unique"}))
                .addColumn(new Column("item_PID", new String[]{"text","unique"}))
                .addColumn(new Column("item_Name", new String[]{"text","unique"}))
                .addColumn(new Column("item_Price_Each", new String[]{"text","unique"}))
                .addColumn(new Column("item_Price", new String[]{"text","unique"}))
                .addColumn(new Column("item_Quantity", new String[]{"text","unique"}))
                .doneTableColumn();

        Boolean b=easyDB.addData("item_id",itemId)
                .addData("item_PID",productId)
                .addData("item_Name",title)
                .addData("item_Price_Each",priceEach)
                .addData("item_Price",totalPrice)
                .addData("item_Quantity",quantity)
                .doneDataAdding();

        Toast.makeText(context, "Added to cart.......", Toast.LENGTH_SHORT).show();

        ((BottomActivity)context).cartCount();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setfilter(List<ModelLatest> filterProperty) {
        list=new ArrayList<>();
        list.addAll(filterProperty);
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        Button add;
        ImageView Img;
        TextView Name,price1;
        CardView con;
        ImageView sync;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Img=itemView.findViewById(R.id.imageView);
            Name=itemView.findViewById(R.id.textView);
            price1=itemView.findViewById(R.id.textView2);
            con=itemView.findViewById(R.id.late);
            sync=itemView.findViewById(R.id.imageView5);

        }
    }
}
