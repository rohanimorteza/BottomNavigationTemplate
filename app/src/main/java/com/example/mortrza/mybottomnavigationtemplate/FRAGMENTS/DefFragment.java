package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mortrza.mybottomnavigationtemplate.DefinitionActivity;
import com.example.mortrza.mybottomnavigationtemplate.R;



public class DefFragment extends Fragment {

    CardView crdEdu;
    CardView crdCourse;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_def,container,false);

        setupView(view);

        return view;
    }


    public void setupView(View view){

        crdEdu = view.findViewById(R.id.crd_education);
        crdCourse = view.findViewById(R.id.crd_course);

        crdEdu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),DefinitionActivity.class);
                i.putExtra("FRG","EDU");
                startActivity(i);
            }
        });

        crdCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),DefinitionActivity.class);
                i.putExtra("FRG","CRS");
                startActivity(i);
            }
        });

    }
}
