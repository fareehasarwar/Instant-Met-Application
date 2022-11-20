package com.example.hifza.instantmet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.hifza.instantmet.adapter.Main_Profile_Top_Adapter;
import com.example.hifza.instantmet.model.Data;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Fareeha on 2/19/2018.
 */

public class Search extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView sv;
    ArrayList<Data> data;
    RecyclerView search_rec_view;
    Main_Profile_Top_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        sv= (SearchView) findViewById(R.id.search);
        search_rec_view=findViewById(R.id.search_recycler_view);
     search_rec_view.  setLayoutManager(new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false));
        if( getIntent().getExtras() != null)
        {
            data=(ArrayList<Data>)getIntent().getSerializableExtra("profile");



        }
        adapter=new Main_Profile_Top_Adapter(Search.this,R.layout.search_layout_item,data,2);
        search_rec_view.setAdapter(adapter);
        sv.setOnQueryTextListener(this);





    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        newText=newText.toLowerCase();
        ArrayList<Data> newlist=new ArrayList<>();
        for(Data data_list: data)
        {
            String name=data_list.getName().toLowerCase();
            if(name.contains(newText))
            {
                newlist.add(data_list);
            }

        }
        adapter.setFilter(newlist);
        return true;
    }
    public  void backButton(View view)
    {
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.backanimation2, R.anim.backanimation1);
    }
}
