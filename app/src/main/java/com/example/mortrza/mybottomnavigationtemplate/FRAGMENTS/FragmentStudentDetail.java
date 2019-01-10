package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Student;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

public class FragmentStudentDetail extends Fragment {

    ImageView stdimg;
    Student student;
    TextView name,edu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_std_detail,container,false);
        //Toast.makeText(getContext(),getArguments().get("ID").toString(),Toast.LENGTH_SHORT).show();
        setupView(view);
        return view;
    }

    public void setupView(View view){

        student = new Student();
        name = view.findViewById(R.id.txt_frg_detail_name);
        edu = view.findViewById(R.id.txt_frg_detail_edu);
        stdimg =  view.findViewById(R.id.img_frg_detail_avatar);


        dbHandler dbh = new dbHandler(getContext());
        dbh.open();
        student = dbh.displayStudent(getArguments().get("ID").toString());
        dbh.close();

        byte[] p = student.getImg();
        Bitmap bm = BitmapFactory.decodeByteArray(p,0,p.length);
        stdimg.setImageBitmap(bm);

        name.setText(name.getText().toString()+" "+student.getName());
        edu.setText(edu.getText().toString()+" "+student.getEducation());



    }
}
