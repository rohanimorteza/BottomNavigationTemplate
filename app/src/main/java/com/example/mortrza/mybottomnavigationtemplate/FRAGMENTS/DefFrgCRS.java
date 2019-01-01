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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.CrsAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Course;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Education;

import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import java.util.ArrayList;
import java.util.List;

public class DefFrgCRS extends Fragment {

    EditText CrsNameEdt,CrsTuitionEdt;
    ImageView addImg;
    RecyclerView recyclerView;
    List<Education> EduList;
    dbHandler dbh;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_def_crs,container,false);
        setupView(view);

        return view;
    }

    public void setupView(View view){


        CrsNameEdt = view.findViewById(R.id.edt_def_name);
        CrsTuitionEdt = view.findViewById(R.id.edt_def_tuition);
        addImg = view.findViewById(R.id.img_def_add);
        recyclerView = view.findViewById(R.id.rec_def);
        EduList =new ArrayList<>();

        dbh = new dbHandler(getContext());
        dbh.open();

        if(dbh.displayCourseCount()>0){
            setupRecycler(dbh.displayCourse());
        }

        dbh.close();


        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CrsTuitionEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"شهریه را وارد کنید",Toast.LENGTH_LONG).show();
                }
                if (CrsNameEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"نام دوره را صحیح وارد کنید",Toast.LENGTH_LONG).show();
                }
                if (!CrsNameEdt.getText().toString().equals("") && !CrsTuitionEdt.getText().toString().equals("")) {

                    dbHandler dbh = new dbHandler(getContext());
                    dbh.open();
                    dbh.insertCrs(CrsNameEdt.getText().toString(), CrsTuitionEdt.getText().toString());
                    CrsNameEdt.getText().clear();
                    CrsTuitionEdt.getText().clear();
                    dbh.close();

                    dbh = new dbHandler(getContext());
                    dbh.open();
                    setupRecycler(dbh.displayCourse());
                    dbh.close();


                    Toast.makeText(getContext(),"ذخیره شد",Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    public  void  setupRecycler(List<Course> CourseList){

        if(CourseList.size()>0){
            CrsAdapter crsAdapter = new CrsAdapter(getContext(),CourseList);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(crsAdapter);
        }else{
            recyclerView.setAdapter(null);
        }
    }

}
