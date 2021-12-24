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
import com.jether.monacoshop.Models.ModelBody;
import com.jether.monacoshop.Models.ModelHair;
import com.jether.monacoshop.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterBody extends RecyclerView.Adapter<AdapterBody.Viewholder>{

    private ArrayList<ModelBody> list;
    private Context context;

    public AdapterBody(ArrayList<ModelBody> list, Context context) {
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

        ModelBody modelbo=list.get(position);
        String name1=modelbo.getProduct();
        String price3=modelbo.getPrice();
        String image = modelbo.getImage();

        String id=modelbo.getId();
        String cat=modelbo.getTitle();
        String desc=modelbo.getDescc();
        String qu=modelbo.getQuant();

        String me1=modelbo.getImagee();

        String po=modelbo.getDate();

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
                intent.putExtra("id",modelbo.getId());
                intent.putExtra("title",modelbo.getTitle());
                intent.putExtra("product",modelbo.getProduct());
                intent.putExtra("price",modelbo.getPrice());
                intent.putExtra("descc",modelbo.getDescc());
                intent.putExtra("image",modelbo.getImage());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setfilter(List<ModelBody> filterProperty) {
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
