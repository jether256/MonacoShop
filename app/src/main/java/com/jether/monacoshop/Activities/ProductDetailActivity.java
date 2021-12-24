package com.jether.monacoshop.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;
import com.jether.monacoshop.Retrofit.ApiClient;
import com.jether.monacoshop.Retrofit.ApiInterface;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {


    private String id,imagee,title,product,descc,price,image,date;

    public static ApiInterface apiInterface;
    SharedPrefManager sharedPrefManager;

    ImageView pro,add,remove;
    TextView name,description,stock,beyi,qunt;

    int totalQuantity=1;
    int totalPrice=0;

    Button cart,buy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_product_detail);


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        sharedPrefManager= new SharedPrefManager(this);

        id = getIntent().getStringExtra("id");
        title=getIntent().getStringExtra("title");
        product =getIntent().getStringExtra("product");
        price=getIntent().getStringExtra("price");
        descc=getIntent().getStringExtra("descc");
        image=getIntent().getStringExtra("image");


        pro=findViewById(R.id.imageView2);
        name=findViewById(R.id.name);
        description=findViewById(R.id.desc);
        beyi=findViewById(R.id.beyi);
        stock=findViewById(R.id.available);
        add=findViewById(R.id.add);
        remove=findViewById(R.id.minus);
        cart=findViewById(R.id.cart);
        buy=findViewById(R.id.buy);
        qunt=findViewById(R.id.quantity);

        name.setText(product);
        description.setText(descc);
        beyi.setText(price);
        //stock.setText(Availability);
        //totalPrice=beyi*totalQuantity;


        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_b_house_24).into(pro);

        } catch (Exception e) {

        }

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            addCart();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totalQuantity<100){
                    totalQuantity ++;
                    qunt.setText(String.valueOf(totalQuantity));
                }

            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(totalQuantity > 1){
                    totalQuantity --;
                    qunt.setText(String.valueOf(totalQuantity));
                }

            }
        });
    }


    //    private void addCart() {
//
//        String ScurrentDate,SCurrentTime;
//        Calendar calForDate= Calendar.getInstance();
//
//
//        SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM dd,yyyy");
//        ScurrentDate=simpleDateFormat.format(calForDate.getTime());
//
//        SimpleDateFormat curentTime= new SimpleDateFormat("HH:mm:ss a");
//        ScurrentDate=curentTime.format(calForDate.getTime());
//
//
//
//            ProgressDialog dialog=new ProgressDialog(ProductDetailActivity.this);
//            dialog.setTitle("Adding Property...");
//            dialog.setMessage("Please wait while we add you property...");
//            dialog.show();
//            dialog.setCanceledOnTouchOutside(false);
//
//            Call<Users> call= apiInterface.addProperty(user_i,pro_ti,pro_desc,pro_status,pro_loc,pro_floo,pro_bedro,pro_bathss,pro_gara,pro_areaa,pro_sizes,pro_lenti,image,pro_add,pro_city);
//            call.enqueue(new Callback<Users>() {
//                @Override
//                public void onResponse(Call<Users> call, Response<Users> response) {
//
//                    if(response.body().getResponse().equals("Ok")){
//
//
//
//                        Toast.makeText(ProductDetailActivity.this,"Property Added", Toast.LENGTH_SHORT).show();
//
//
//                        dialog.dismiss();
//                    }
//                    else if(response.body().getResponse().equals("failed")){
//                        Toast.makeText(ProductDetailActivity.this, "Something went wrong,Please try again....", Toast.LENGTH_SHORT).show();
//                        dialog.dismiss();
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<Users> call, Throwable t) {
//
//                }
//            });
//
//
//
//
//
//
//    }

}