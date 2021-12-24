   package com.jether.monacoshop.logged;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;
import com.jether.monacoshop.Activities.BodyActivity;
import com.jether.monacoshop.Activities.HairActivity;
import com.jether.monacoshop.Activities.LatestActivity;
import com.jether.monacoshop.Activities.MainActivity;
import com.jether.monacoshop.Activities.ProductAllActivity;
import com.jether.monacoshop.Adapters.AdapterAll;
import com.jether.monacoshop.Adapters.AdapterCartItem;
import com.jether.monacoshop.Adapters.AdapterLatest;
import com.jether.monacoshop.DBHelper.DBContact;
import com.jether.monacoshop.DBHelper.DBHelper;
import com.jether.monacoshop.LoggedAdapter.AdapterAllLogged;
import com.jether.monacoshop.LoggedAdapter.AdapterCartItemLogged;
import com.jether.monacoshop.LoggedAdapter.AdapterLatestLogged;
import com.jether.monacoshop.Models.ModelAll;
import com.jether.monacoshop.Models.ModelCartItem;
import com.jether.monacoshop.Models.ModelLatest;
import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;
import com.jether.monacoshop.Retrofit.ApiClient;
import com.jether.monacoshop.Retrofit.ApiInterface;
import com.jether.monacoshop.Retrofit.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import p32929.androideasysql_library.EasyDB;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class BottomActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

     static final float END_SCALE = 0.7f;
     LinearLayout contentView;

     ImageButton menu,cart;

     TextView log;

     CardView bodycare,haircare;
     public static ApiInterface apiInterface;

     ArrayList<ModelLatest> latest= new ArrayList<>();
     AdapterLatestLogged adapterLatest;

     ProgressDialog progressDialog;

     ArrayList<ModelAll> all;
     AdapterAllLogged adapterAll;

     SharedPrefManager sharedPrefManager;
     RecyclerView rv;

     RecyclerView vv;

     TextView sePro,seeAll,cartCountTv,wale,slow;

     private EasyDB easyDB;
     DrawerLayout drawerLayout;
     NavigationView navigationview;


     String pro_iddd;
     String pro_costtt;
     String pro_orderrr;

     //cart
     private ArrayList<ModelCartItem> cartItemList;
     private AdapterCartItemLogged adapterCartItem;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_bottom);

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        sharedPrefManager= new SharedPrefManager(this);

        wale=findViewById(R.id.wel);


        HashMap<String, String> kojo=sharedPrefManager.getUserDetail();
        String kajoee=kojo.get(SharedPrefManager.NAME);
        wale.setText("Welcome Back"+"\n"+kajoee);



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


//        ConnectivityManager connectivityManager=(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        //get active network info
//        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
//
//        //check network status
//        if(networkInfo==null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
//            //when internet is active
//
//            Intent intent = new Intent(getContext(), NoInternetActivity.class);
//            startActivity(intent);
//
//        }else{
//            //when internet is active
//            //init();
//        }


        ImageSlider imageSlider=findViewById(R.id.image_slider);

        List<SlideModel> slideModels= new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.one,"Discount on bodycare", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.two,"Discount on haircare", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.three,"70% OFF", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);


        progressDialog.setTitle("Welcome Back."+"\n"+kajoee);
        progressDialog.setMessage("Please wait.....");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPrefManager.editor.clear();
                sharedPrefManager.editor.commit();
                Intent intent= new Intent(BottomActivity.this, MainActivity.class);
                startActivity(intent);
                Animatoo.animateSlideLeft(BottomActivity.this);
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
                Intent intent= new Intent(BottomActivity.this, BodyActivity.class);
                startActivity(intent);
            }
        });

        haircare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(BottomActivity.this, HairActivity.class);
                startActivity(intent);
            }
        });


        sePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BottomActivity.this, LatestActivity.class);
                startActivity(intent);
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(BottomActivity.this, ProductAllActivity.class);
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

    public boolean checkNetworkConnection(){
        ConnectivityManager connectivityManager=(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        return (networkInfo!= null && networkInfo.isConnected());
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
     public TextView name1,location,phone,status,ode,user;
     private void showCartDialog() {

         //ini list
         cartItemList = new ArrayList<>();

         //inflate cart_layout
         View view = LayoutInflater.from(this).inflate(R.layout.dailog_cart_log, null);

         //init views
         ode=view.findViewById(R.id.orderNameTv);
         user=view.findViewById(R.id.userNameTv);

         RecyclerView cartViewItem = view.findViewById(R.id.cartItemRv);
          name1 = view.findViewById(R.id.namNameTv);
          location = view.findViewById(R.id.locNameTv);
         phone = view.findViewById(R.id.phoNameTv);
          status = view.findViewById(R.id.statNameTv);
         sTotalTv = view.findViewById(R.id.sTotalTv);
         allTotalPriceTv = view.findViewById(R.id.totalTv);
         Button checkOutBtn = view.findViewById(R.id.checkOutBtn);



         //dialog

         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         //set view to this buiolder
         builder.setView(view);


         HashMap<String, String> od =sharedPrefManager.getOrderDetails();
         String order=od.get(SharedPrefManager.oderid);

         ode.setText(order);

         HashMap<String, String> kojo=sharedPrefManager.getUserDetail();
         String me=kojo.get(SharedPrefManager.ID);
         String namee=kojo.get(SharedPrefManager.NAME);
         String looo=kojo.get(SharedPrefManager.LOCATION);
         String phoooo=kojo.get(SharedPrefManager.MOBILE);

         name1.setText(namee);
         location.setText(looo);
         phone.setText(phoooo);
         user.setText(me);




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
         adapterCartItem = new AdapterCartItemLogged(this, cartItemList);
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
                 if (cartItemList.size() == 0) {
                     //cartlist empty
                     Toast.makeText(BottomActivity.this, "No item in cart", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 submitOrder();
             }
         });

     }

     private void submitOrder() {
         // show progress dialog
         progressDialog.setMessage("Placing Order....");
         progressDialog.show();

         String cost = sTotalTv.getText().toString().trim().replaceAll("shs", "");
         String nem = name1.getText().toString();
         String loko = location.getText().toString();
         String fwo = phone.getText().toString();
         String stat=status.getText().toString();
         String userK=user.getText().toString();
         //setup your order data

         HashMap<String, String> jay1 = new HashMap<>();
         jay1.put("Cost", cost);
         jay1.put("customer", nem);
         jay1.put("location", loko);
         jay1.put("phone", fwo);
         jay1.put("status", stat);
         jay1.put("userid", userK);




         Call<Users> call= apiInterface.insertOrder(jay1);
         call.enqueue(new Callback<Users>() {
             @Override
             public void onResponse(Call<Users> call, Response<Users> response) {

                 if(response.body().getResponse().equals("Ok")){



                     pro_iddd=response.body().getProID();
                     pro_costtt=response.body().getProCost();
                     pro_orderrr=response.body().getProOder();
                     sharedPrefManager.createPro(pro_iddd,pro_costtt,pro_orderrr);

                     //Toast.makeText(BottomActivity.this,String.valueOf(response.body().getProCost()), Toast.LENGTH_SHORT).show();
                     String y = user.getText().toString();

                     for (int i = 0; i < cartItemList.size(); i++) {
                         String pId = cartItemList.get(i).getId();
                         String id = cartItemList.get(i).getId();
                         String cost = cartItemList.get(i).getTotal();
                         String price = cartItemList.get(i).getPrice();
                         String name = cartItemList.get(i).getName();
                         String quantity = cartItemList.get(i).getQuantity();

                         HashMap<String, String> map = new HashMap<>();
                         map.put("pId", pId);
                         map.put("name", name);
                         map.put("cost", cost);
                         map.put("price", price);
                         map.put("quantity", quantity);
                         

                         //databaseReference.child(timestamp).child("Items").child(pId).setValue(hashMap1);

                         Call<Users> yita=apiInterface.insertItems(map );
                         yita.enqueue(new Callback<Users>() {
                             @Override
                             public void onResponse(Call<Users> call, Response<Users> response) {
                                 if(response.body().getResponse().equals("Yes")){


                                     Toast.makeText(BottomActivity.this,"Order Made and items added..", Toast.LENGTH_SHORT).show();
                                 }

                                else if(response.body().getResponse().equals("Neda")){

                                     Toast.makeText(BottomActivity.this,"items not added....", Toast.LENGTH_SHORT).show();
                                 }
                             }

                             @Override
                             public void onFailure(Call<Users> call, Throwable t) {

                             }
                         });

                     }


                     Toast.makeText(BottomActivity.this,"Order Made", Toast.LENGTH_SHORT).show();

                     progressDialog.dismiss();
                 }
                 else if(response.body().getResponse().equals("No")){
                     Toast.makeText(BottomActivity.this, "Something went wrong,Please try again....", Toast.LENGTH_SHORT).show();
                     progressDialog.dismiss();
                 }


             }

             @Override
             public void onFailure(Call<Users> call, Throwable t) {

             }
         });



     }


//     private void readFromLocalStorage(){
//         DBHelper dbHelper= new DBHelper(this);
//         SQLiteDatabase database=dbHelper.getReadableDatabase();
//         Cursor cursor=dbHelper.readFromLocalDatabase(database);
//
//         while(cursor.moveToNext()){
//             String name= cursor.getString(cursor.getColumnIndex(DBContact.PRODUCT));
//             String pr= cursor.getString(cursor.getColumnIndex(DBContact.PRICE));
//             String ima= cursor.getString(cursor.getColumnIndex(DBContact.IMAGE));
//             int sync_status=cursor.getInt(cursor.getColumnIndex(DBContact.SYNC_STATUS));
//             latest.add(new ModelLatest(name,pr,ima,sync_status));
//
//         }
//     }

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

                 adapterLatest= new AdapterLatestLogged(latest,BottomActivity.this);
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

         LinearLayoutManager linearpropertyManager= new LinearLayoutManager(BottomActivity.this);
         linearpropertyManager.setOrientation(RecyclerView.HORIZONTAL);
         vv.setLayoutManager(linearpropertyManager);

         all= new ArrayList<>();
         Call<Users> allll =apiInterface.haveAll();
         allll.enqueue(new Callback<Users>() {
             @Override
             public void onResponse(Call<Users> call, Response<Users> response) {

                 all=response.body().getProductAll();

                 adapterAll= new AdapterAllLogged(all,BottomActivity.this);
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
                 startActivity(new Intent(getApplicationContext(),BottomActivity.class));
                 break;

             case R.id.profile:
                 startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                 break;

             case R.id.category:
                 // startActivity(new Intent(getApplicationContext(),CartActivity.class));
                 break;


             case R.id.offers:
                 // startActivity(new Intent(getApplicationContext(),ProductAllActivity.class));
                 break;


             case R.id.newp:
                 //startActivity(new Intent(getApplicationContext(),LatestActivity.class));
                 break;

             case R.id.orders:
                 //startActivity(new Intent(getApplicationContext(),OrdersActivity.class));
                 break;


             case R.id.cart:
                 //startActivity(new Intent(getApplicationContext(),AddtoCartActivity.class));
                 break;

             case R.id.out:

                 sharedPrefManager.editor.clear();
                 sharedPrefManager.editor.commit();
                 Intent intent= new Intent(BottomActivity.this, MainActivity.class);
                 startActivity(intent);
                 Animatoo.animateSlideLeft(BottomActivity.this);
                 break;

         }
         return false;
     }

     @Override
     public void onPointerCaptureChanged(boolean hasCapture) {

     }

     @Override
     protected void onStart() {
         super.onStart();
         if (!sharedPrefManager.isLogin()) {
             sharedPrefManager.editor.clear();
             sharedPrefManager.editor.commit();
             Intent intent = new Intent(BottomActivity.this,MainActivity.class);
             startActivity(intent);
         }else{

         }
     }
}