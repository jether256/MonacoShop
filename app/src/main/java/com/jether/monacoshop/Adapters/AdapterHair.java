package com.jether.monacoshop.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.jether.monacoshop.Activities.ProductDetailActivity;
import com.jether.monacoshop.Models.ModelAll;
import com.jether.monacoshop.Models.ModelHair;
import com.jether.monacoshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterHair extends RecyclerView.Adapter<AdapterHair.Viewholder> {

    private ArrayList<ModelHair> list;
    private Context context;

    public AdapterHair(ArrayList<ModelHair> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new Viewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        ModelHair modelhair=list.get(position);
        String name1=modelhair.getProduct();
        String price3=modelhair.getPrice();
        String image = modelhair.getImage();

        String id=modelhair.getId();
        String cat=modelhair.getTitle();
        String desc=modelhair.getDescc();
        String qua=modelhair.getQuant();

        String me1=modelhair.getImagee();

        String po=modelhair.getDate();

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

                Intent intent= new Intent(context, ProductDetailActivity.class);
                intent.putExtra("id",modelhair.getId());
                intent.putExtra("title",modelhair.getTitle());
                intent.putExtra("product",modelhair.getProduct());
                intent.putExtra("price",modelhair.getPrice());
                intent.putExtra("descc",modelhair.getDescc());
                intent.putExtra("image",modelhair.getImage());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setfilter(List<ModelHair> filterProperty) {
        list=new ArrayList<>();
        list.addAll(filterProperty);
        notifyDataSetChanged();
    }


    public class Viewholder extends RecyclerView.ViewHolder{

        CardView con;

        ImageView Img;
        TextView Name,price1;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            Img=itemView.findViewById(R.id.imageView);
            Name=itemView.findViewById(R.id.textView);
            price1=itemView.findViewById(R.id.textView2);
            con=itemView.findViewById(R.id.gwe);
        }
    }
}
