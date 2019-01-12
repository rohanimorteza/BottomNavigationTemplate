package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Student;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import static android.os.Build.VERSION.SDK_INT;

public class FragmentStudentDetail extends Fragment {

    ImageView stdimg;
    Student student;
    TextView name,edu;
    FloatingActionButton setting;
    BottomSheetDialog mBottomSheetDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_std_detail,container,false);
        //Toast.makeText(getContext(),getArguments().get("ID").toString(),Toast.LENGTH_SHORT).show();
        setupView(view);
        return view;
    }

    public void setupView(final View view){

        student = new Student();
        name = view.findViewById(R.id.txt_frg_detail_name);
        edu = view.findViewById(R.id.txt_frg_detail_edu);
        stdimg =  view.findViewById(R.id.img_frg_detail_avatar);
        setting = view.findViewById(R.id.fab_detail_std);


        dbHandler dbh = new dbHandler(getContext());
        dbh.open();
        student = dbh.displayStudent(getArguments().get("ID").toString());
        dbh.close();

        byte[] p = student.getImg();
        Bitmap bm = BitmapFactory.decodeByteArray(p,0,p.length);
        stdimg.setImageBitmap(bm);

        name.setText(name.getText().toString()+" "+student.getName());
        edu.setText(edu.getText().toString()+" "+student.getEducation());

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet(view);
            }
        });

    }


    public void openBottomSheet(View v){

        View view = getLayoutInflater().inflate(R.layout.bottomsheetstudent,null);
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        CardView payment,register;
        payment = view.findViewById(R.id.crd_bs_payment);
        register = view.findViewById(R.id.crd_bs_register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();



            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();


            }
        });


    }
}
