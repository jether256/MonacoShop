 package com.jether.monacoshop.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.jether.monacoshop.Adapters.AdapterAll;
import com.jether.monacoshop.Adapters.AdapterCartItem;
import com.jether.monacoshop.Adapters.AdapterLatest;
import com.jether.monacoshop.Models.ModelAll;
import com.jether.monacoshop.Models.ModelCartItem;
import com.jether.monacoshop.Models.ModelLatest;
import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;
import com.jether.monacoshop.Retrofit.ApiClient;
import com.jether.monacoshop.Retrofit.ApiInterface;
import com.jether.monacoshop.Retrofit.Users;
import com.jether.monacoshop.logged.BottomActivity;
import com.jether.monacoshop.logged.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import p32929.androideasysql_library.EasyDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    static final float END_SCALE = 0.7f;
    LinearLayout contentView;

    ImageButton menu,cart;

    TextView log;

    CardView bodycare,haircare;
    public static ApiInterface apiInterface;
    ArrayList<ModelLatest> latest= new ArrayList<>();
    AdapterLatest adapterLatest;

    ProgressDialog progressDialog;

    ArrayList<ModelAll> all;
    AdapterAll adapterAll;

    SharedPrefManager sharedPrefManager;
    RecyclerView rv;

    RecyclerView vv;

    TextView sePro,seeAll,cartCountTv;

    private EasyDB easyDB;
    DrawerLayout drawerLayout;
    NavigationView navigationview;

    //cart
    private ArrayList<ModelCartItem> cartItemList;
    private AdapterCartItem adapterCartItem;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        sharedPrefManager= new SharedPrefManager(this);


        menu=findViewById(R.id.menuBtn);

        progressDialog= new ProgressDialog(this);

        log=findViewById(R.id.reviewBtn1);

        cartCountTv = findViewById(R.id.cartCountTv);
        cart=findViewById(R.id.cartBtn);
        rv=findViewById(R.id.new_product_rec);
        vv=findViewById(R.id.popular_rec);
        sePro=findViewById(R.id.newProducts_see_all_1);
        seeAll=findViewById(R.id.popular_see_all);

        bodycare=findViewById(R.id.body);
        haircare=findViewById(R.id.hair);

        //menu hooks
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationview=findViewById(R.id.navigation_view);

        contentView=findViewById(R.id.content);




        ImageSlider imageSlider=findViewById(R.id.image_slider);

        List<SlideModel> slideModels= new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.one,"Discount on bodycare", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.two,"Discount on haircare", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.three,"70% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);


        progressDialog.setTitle("Welcome To MonacoShop Online App.");
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCartDialog();
            }
        });

        bodycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, BodyActivity.class);
                startActivity(intent);
            }
        });

        haircare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this, HairActivity.class);
                startActivity(intent);
            }
        });


        sePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, LatestActivity.class);
                startActivity(intent);
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, ProductAllActivity.class);
                startActivity(intent);
            }
        });

        easyDB = EasyDB.init(this, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn("Item_id", new String[]{"text", "unique"})
                .addColumn("Item_PID", new String[]{"text", "not null"})
                .addColumn("Item_Name", new String[]{"text", "not null"})
                .addColumn("Item_Price_Each", new String[]{"text", "not null"})
                .addColumn("Item_Price", new String[]{"text", "not null"})
                .addColumn("Item_Quantity", new String[]{"text", "not null"})
                .doneTableColumn();

        navigationDrawer();
        init();
        init2();

        deleteCartData();
        cartCount();



    }

    public void cartCount() {
        //keep it public so that it can be accessed in adapter
        //get cart count
        int count = easyDB.getAllData().getCount();
        if (count <= 0) {
            // no item in cart,hide cart count textView
            cartCountTv.setVisibility(View.GONE);
        } else {
            //have items in cart, show cart count textview and set count
            cartCountTv.setVisibility(View.VISIBLE);
            cartCountTv.setText("" + count);//concatnate with string coz we cant set interger in textview
        }
    }


    private void deleteCartData() {
        easyDB.deleteAllDataFromTable();//delete all records from cart
    }

    public double allTotalPrice = 0.00;
    //need to access these views in adapter so make them public
    public TextView sTotalTv,  allTotalPriceTv;//
    private void showCartDialog() {

        //ini list
        cartItemList = new ArrayList<>();

        //inflate cart_layout
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_cart, null);

        //init views
        RecyclerView cartViewItem = view.findViewById(R.id.cartItemRv);
        TextView shopNameTv = view.findViewById(R.id.shopNameTv);
        sTotalTv = view.findViewById(R.id.sTotalTv);
        allTotalPriceTv = view.findViewById(R.id.totalTv);
        Button checkOutBtn = view.findViewById(R.id.checkOutBtn);



        //dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set view to this buiolder
        builder.setView(view);

        EasyDB easyDB = EasyDB.init(this, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn("Item_id", new String[]{"text", "unique"})
                .addColumn("Item_PID", new String[]{"text", "not null"})
                .addColumn("Item_Name", new String[]{"text", "not null"})
                .addColumn("Item_Price_Each", new String[]{"text", "not null"})
                .addColumn("Item_Price", new String[]{"text", "not null"})
                .addColumn("Item_Quantity", new String[]{"text", "not null"})
                .doneTableColumn();

        //get all records from db
        Cursor res = easyDB.getAllData();
        while (res.moveToNext()) {

            String id = res.getString(1);
            String pId = res.getString(2);
            String name = res.getString(3);
            String price = res.getString(4);
            String cost = res.getString(5);
            String quantity = res.getString(6);

            allTotalPrice = allTotalPrice + Double.parseDouble(cost);

            ModelCartItem modelCartItem = new ModelCartItem(
                    "" + id,
                    "" + pId,
                    "" + name,
                    "" + price,
                    "" + cost,
                    "" + quantity);

            cartItemList.add(modelCartItem);

        }

        //set up adapter
        adapterCartItem = new AdapterCartItem(this, cartItemList);
        //set to recyclerviewc
        cartViewItem.setAdapter(adapterCartItem);


        sTotalTv.setText("Shs" + String.format("%.2f", allTotalPrice));
        allTotalPriceTv.setText("Shs" + (allTotalPrice));



        //show dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        //reset total price on dialog dismiss
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            allTotalPrice=0.00;
            }
        });

        //place order
        checkOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Please Login in First", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void init() {

//
//        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
//        rv.setLayoutManager(layoutManager);

        LinearLayoutManager linearpropertyManager= new LinearLayoutManager(this);
        linearpropertyManager.setOrientation(RecyclerView.HORIZONTAL);
        rv.setLayoutManager(linearpropertyManager);

        latest= new ArrayList<>();
        Call<Users> propertyBaby =apiInterface.haveLatest();
        propertyBaby.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                latest=response.body().getProductLate() ;

                adapterLatest= new AdapterLatest(latest,MainActivity.this);
                rv.setAdapter(adapterLatest);
                rv.setHasFixedSize(true);
                adapterLatest.notifyDataSetChanged();
                progressDialog.dismiss();

            }



            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }


    private void init2() {

//
//        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
//        rv.setLayoutManager(layoutManager);

        LinearLayoutManager linearpropertyManager= new LinearLayoutManager(MainActivity.this);
        linearpropertyManager.setOrientation(RecyclerView.HORIZONTAL);
        vv.setLayoutManager(linearpropertyManager);

        all= new ArrayList<>();
        Call<Users> allll =apiInterface.haveAll();
        allll.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                all=response.body().getProductAll();

                adapterAll= new AdapterAll(all,MainActivity.this);
                vv.setAdapter(adapterAll);
                vv.setHasFixedSize(true);
                adapterAll.notifyDataSetChanged();

            }



            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }



    //navigation drawer functions
    private void navigationDrawer() {

        //Navigation Drawer
        navigationview.bringToFront();
        navigationview.setNavigationItemSelectedListener(this);
        navigationview.setCheckedItem(R.id.home);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else{
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });


        navigationNavigationDrawer();
    }

    private void navigationNavigationDrawer() {
        drawerLayout.setScrimColor(getResources().getColor(R.color.grey));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                //Scale the view based on current slide offset.
                final float diffScaledOffset=slideOffset*(1-END_SCALE);
                final float offsetScale=1-diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);


                //translate the view,accounting for the scaled width
                final float xOffset=drawerView.getWidth()*slideOffset;
                final float xOffsetDiff=contentView.getWidth()*diffScaledOffset/2;
                final float xTranslation=xOffset-xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                //label.View.setVisibility(View.GONE)
            }
        });

    }


    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){

            case R.id.home:
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

            case R.id.profile:

            case R.id.category:


            case R.id.newp:


            case R.id.offers:

            case R.id.orders:


            case R.id.cart:
                Toast.makeText(MainActivity.this, "Please Login in First", Toast.LENGTH_SHORT).show();
                break;

            case R.id.out:
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                break;

        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onStart() {
        super.onStart();

        if (sharedPrefManager.isLogin()) {
            Intent intent = new Intent(MainActivity.this, BottomActivity.class);
            startActivity(intent);
        }else{

        }
    }
}