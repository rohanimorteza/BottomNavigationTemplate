package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.STDAdapter;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_STD;


public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    TextView nostd;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        setupRecycler(view);

        return view;
    }

    public void setupRecycler(View view){

        dbHandler dbh = new dbHandler(getContext());
        dbh.open();

        if(dbh.displayRowCount(TBL_STD)>0){
            nostd = view.findViewById(R.id.txt_home);
            nostd.setVisibility(View.INVISIBLE);
            recyclerView = view.findViewById(R.id.rec_home);
            STDAdapter stdAdapter = new STDAdapter(getContext(),dbh.displayStudent());
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(stdAdapter);

        }else {

        }

        dbh.close();

    }

}
