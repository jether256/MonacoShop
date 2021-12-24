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
import com.jether.monacoshop.Models.ModelAll;
import com.jether.monacoshop.R;
import com.jether.monacoshop.logged.BottomActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterAllLogged extends RecyclerView.Adapter<AdapterAllLogged.Viewholder>{
    private ArrayList<ModelAll> list;
    private Context context;


    public AdapterAllLogged(ArrayList<ModelAll> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public AdapterAllLogged.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterAllLogged.Viewholder holder, int position) {

        ModelAll modelAll=list.get(position);
        String name1=modelAll.getProduct();
        String price3=modelAll.getPrice();
        String image = modelAll.getImage();

        String id=modelAll.getId();
        String cat=modelAll.getTitle();
        String desc=modelAll.getDescc();
        String qua=modelAll.getQuant();

        String me1=modelAll.getImagee();

        String po=modelAll.getDate();

        int sync_status=list.get(position).getSync_status();

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
//                intent.putExtra("id",modelAll.getId());
//                intent.putExtra("title",modelAll.getTitle());
//                intent.putExtra("product",modelAll.getProduct());
//                intent.putExtra("price",modelAll.getPrice());
//                intent.putExtra("descc",modelAll.getDescc());
//                intent.putExtra("image",modelAll.getImage());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);


                showQant(modelAll);

            }

        });


    }

    private double cost = 0;
    private double finalCost= 0;
    private int quantity=0;
    private void showQant(ModelAll modelAll) {
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

        final String productId=modelAll.getId();
        String title =modelAll.getProduct();
        String productQuantity=modelAll.getQuant();
        String description=modelAll.getDescc();
        //String discountNote=modelAll.getDiscountNote();
        String image=modelAll.getImage();
        String price2=modelAll.getPrice();


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

    public void setfilter(List<ModelAll> filterProperty) {
        list=new ArrayList<>();
        list.addAll(filterProperty);
        notifyDataSetChanged();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        CardView con;

        ImageView Img,sync;
        TextView Name,price1;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Img=itemView.findViewById(R.id.imageView);
            Name=itemView.findViewById(R.id.textView);
            price1=itemView.findViewById(R.id.textView2);
            con=itemView.findViewById(R.id.gwe);
            sync=itemView.findViewById(R.id.imageView5);
        }
    }
}
