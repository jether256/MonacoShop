package com.jether.monacoshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jether.monacoshop.Adapters.AdapterBody;
import com.jether.monacoshop.Adapters.AdapterHair;
import com.jether.monacoshop.Models.ModelBody;
import com.jether.monacoshop.Models.ModelHair;
import com.jether.monacoshop.Prefrence.SharedPrefManager;
import com.jether.monacoshop.R;
import com.jether.monacoshop.Retrofit.ApiClient;
import com.jether.monacoshop.Retrofit.ApiInterface;
import com.jether.monacoshop.Retrofit.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HairActivity extends AppCompatActivity {

    public static ApiInterface apiInterface;
    SearchView searchView;
    SharedPrefManager sharedPrefManager;

    ArrayList<ModelHair> sen = new ArrayList<>();
    AdapterHair adapterHair;

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hair);

        apiInterface= ApiClient.getApiClient().create(ApiInterface.class);
        sharedPrefManager= new SharedPrefManager(this);
        rv=findViewById(R.id.pro);
        searchView=findViewById(R.id.search);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                if (!searchView.isIconified()){
                    searchView.setIconified(true);
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<ModelHair> filterProperty=filter(sen,newText);
                adapterHair.setfilter(filterProperty);

                return false;
            }
        });


        init();
    }

    private void init() {


        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,2);
        rv.setLayoutManager(layoutManager);

//        LinearLayoutManager linearpropertyManager= new LinearLayoutManager(this);
//        linearpropertyManager.setOrientation(RecyclerView.VERTICAL);
//        rv.setLayoutManager(linearpropertyManager);

        sen= new ArrayList<>();
        Call<Users> propertySn =apiInterface.haveHair();
        propertySn.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                sen=response.body().getProductHair();

                adapterHair= new AdapterHair(sen,getApplicationContext());
                rv.setAdapter(adapterHair);
                rv.setHasFixedSize(true);
                adapterHair.notifyDataSetChanged();

            }



            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }

    private List<ModelHair>filter(List<ModelHair>hi,String query){
        query=query.toLowerCase();
        final List<ModelHair>filterModeList=new ArrayList<>();
        for (ModelHair modal:hi){

            final String text=modal.getProduct().toLowerCase();
            final String text1=modal.getPrice().toLowerCase();
            if (text.startsWith(query)){
                filterModeList.add(modal);
            }
            else if (text1.startsWith(query)){
                filterModeList.add(modal);
            }

        }
        return filterModeList;
    }
}