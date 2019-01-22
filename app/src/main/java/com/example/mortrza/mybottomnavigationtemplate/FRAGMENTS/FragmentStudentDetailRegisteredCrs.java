package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.CrsAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Course;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.mortrza.mybottomnavigationtemplate.MainActivity.Flag_called_from_crsfrg;
import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_CRS;
import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_TRM;

public class FragmentStudentDetailRegisteredCrs extends Fragment {

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_std_detail_reg_crs,container,false);
        setupView(view);
        return view;
    }

    public void setupView(View view){

        recyclerView = view.findViewById(R.id.rec_registerd_crs);

        //final Typeface tf_yekan = Typeface.createFromAsset(getContext().getAssets(),"byekan.ttf");


        dbHandler dbh = new dbHandler(getContext());
        dbh = new dbHandler(getContext());
        dbh.open();

        if(dbh.displayRowCount(TBL_CRS)>0){
            setupRecycler(dbh.displayCourse());
        }

        dbh.close();

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
