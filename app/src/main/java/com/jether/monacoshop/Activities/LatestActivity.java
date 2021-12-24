package com.jether.monacoshop.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jether.monacoshop.Adapters.AdapterLatest;
import com.jether.monacoshop.Models.ModelLatest;
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

public class LatestActivity extends AppCompatActivity {


    public static ApiInterface apiInterface;
    SearchView searchView;
    SharedPrefManager sharedPrefManager;

    ArrayList<ModelLatest> latest= new ArrayList<>();
    AdapterLatest adapterLatest;

    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest);

        Window window=getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_latest);

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
                final List<ModelLatest> filterProperty=filter(latest,newText);
                adapterLatest.setfilter(filterProperty);

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

        latest= new ArrayList<>();
        Call<Users> propertyLa =apiInterface.haveLatest();
        propertyLa.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                latest=response.body().getProductLate();

                adapterLatest= new AdapterLatest(latest,getApplicationContext());
                rv.setAdapter(adapterLatest);
                rv.setHasFixedSize(true);
                adapterLatest.notifyDataSetChanged();

            }



            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }

    private List<ModelLatest>filter(List<ModelLatest>hi,String query){
        query=query.toLowerCase();
        final List<ModelLatest>filterModeList=new ArrayList<>();
        for (ModelLatest modal:hi){

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