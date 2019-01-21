package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.EduAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.TchAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Education;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Teacher;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import java.util.ArrayList;
import java.util.List;

public class DefFrgTCH extends Fragment {


    EditText unitEdt;
    ImageView addImg;
    RecyclerView recyclerView;
    List<Teacher> TchList;
    dbHandler dbh;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_def_tch,container,false);
        setupView(view);

        return view;
    }

    public void setupView(View view){


        unitEdt = view.findViewById(R.id.edt_def_name);
        addImg = view.findViewById(R.id.img_def_add);
        recyclerView = view.findViewById(R.id.rec_def);
        TchList =new ArrayList<>();

        dbh = new dbHandler(getContext());
        dbh.open();
        setupRecycler(dbh.displayTeacher());
        dbh.close();

        unitEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                dbHandler dbh =new dbHandler(getContext());
                dbh.open();
                if(dbh.DisplayTchCount(unitEdt.getText().toString())>0){

                    setupRecycler(dbh.displayTeacher(unitEdt.getText().toString()));
                }else {
                    recyclerView.setAdapter(null);
                }

                dbh.close();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (unitEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"مقدار را صحیح وارد کنید",Toast.LENGTH_LONG).show();
                }else {

                    dbHandler dbh =new dbHandler(getContext());
                    dbh.open();
                    dbh.insertTeacher(unitEdt.getText().toString());

                    unitEdt.getText().clear();

                    dbh.open();
                    setupRecycler(dbh.displayTeacher());
                    dbh.close();
                    Toast.makeText(getContext(),"ذخیره شد",Toast.LENGTH_LONG).show();
                    unitEdt.getText().clear();
                    dbh.close();
                }
            }
        });

    }

    public  void  setupRecycler(List<Teacher> unitList1){

        if(unitList1.size()>0){
            TchAdapter tchAdapter = new TchAdapter(getContext(),unitList1);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
            recyclerView.setAdapter(tchAdapter);
        }else{
            recyclerView.setAdapter(null);
        }
    }


}
