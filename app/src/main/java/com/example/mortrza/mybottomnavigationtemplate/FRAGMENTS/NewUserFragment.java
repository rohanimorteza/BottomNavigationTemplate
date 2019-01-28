package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;
import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_EDU;


public class NewUserFragment extends Fragment {

    Spinner spinner;
    ImageView imgg;
    Button save;
    EditText name;
    LinearLayout imgselect;
    byte[] imageInByte;
    BottomSheetDialog mBottomSheetDialog;
    Context context;


    List<String> EducationNames = new ArrayList<>();
    List<String> EducationIDs  = new ArrayList<>();
    String id_education;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newuser,container,false);

        context = getContext();
        setupView(view);

        return view;
    }

    public void setupView(View view){

        spinner = view.findViewById(R.id.spn_new_educate);
        save = view.findViewById(R.id.btn_new_save);
        name = view.findViewById(R.id.edt_new_name);
        imgselect = view.findViewById(R.id.lin_newuser_img);
        imgg = view.findViewById(R.id.img_new_pic);


        imgselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet(v);

            }
        });


        setupSpinner();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_education = EducationIDs.get(position);
                //Toast.makeText(getApplicationContext(),id_education,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bitmap bitmap = ((BitmapDrawable) imgg.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , baos);
                imageInByte = baos.toByteArray();


                if(name.getText().length()==0) {
                    Toast.makeText(getContext(),"نام را وارد کنید",Toast.LENGTH_LONG).show();
                }
                if( id_education.equals("1")) {
                    Toast.makeText(getContext(),"تحصیلات را انتخاب کنبد",Toast.LENGTH_LONG).show();
                }

                if(name.getText().length()>0 &&  !id_education.equals("1") && imageInByte.length>0 ){
                    dbHandler dbHandler = new dbHandler(getContext());
                    dbHandler.open();
                    dbHandler.insertStudent(name.getText().toString(),id_education , imageInByte);
                    dbHandler.close();

                    name.getText().clear();

                    Toast.makeText(getContext()," ذخیره شد",Toast.LENGTH_LONG).show();
                }

            }
        });


    }


    public void setupSpinner(){

       // EducationNames.add("لطفا لطفا");
       // EducationIDs.add("0");


        dbHandler dbh = new dbHandler(getContext());
        dbh.open();
        for(int i=0;i<dbh.displayRowCount(TBL_EDU);i++){
            EducationNames.add(dbh.displayEducation(i+1));
            EducationIDs.add(i+1+"");

        }
        dbh.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,EducationNames);

        spinner.setAdapter(adapter);

    }

    public void openBottomSheet(View v){

        View view = getLayoutInflater().inflate(R.layout.bottomsheet,null);
        mBottomSheetDialog = new BottomSheetDialog(getContext());
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();

        CardView camera,Gallery;
        camera = view.findViewById(R.id.crd_bs_camera);
        Gallery = view.findViewById(R.id.crd_bs_gallery);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();



                if(SDK_INT>= Build.VERSION_CODES.M){
                    if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
                        if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.CAMERA)){

                            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                            builder.setCancelable(true);
                            builder.setTitle("دسترسی به دوربین ؟؟؟");
                            builder.setMessage("برای استفاده از دوربین، و گرفتن عکس از دانشجو برنامه به این دسترسی نیاز دارد.");
                            builder.setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CAMERA},2);
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }else {
                            ActivityCompat.requestPermissions(getActivity(),new String[] {Manifest.permission.CAMERA},2);
                        }
                    }else {
                        //Toast.makeText(getApplicationContext(),"شما قبلا این دسترسی را تائید کرده اید.",Toast.LENGTH_LONG).show();
                        mBottomSheetDialog.dismiss();
                        Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent,2);
                    }
                }else {
                    mBottomSheetDialog.dismiss();
                    Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,2);
                }

            }
        });

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                Intent intent  = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK && data!=null ){

            Uri img = data.getData();
            try{
                imgg.setImageURI(img);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(requestCode==2 && resultCode==RESULT_OK && data!=null ){

            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgg.setImageBitmap(photo);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 2 :
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getContext(),"تائید شد",Toast.LENGTH_LONG).show();
                    Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,2);
                }else {
                    Toast.makeText(getContext()," تائید نشد ",Toast.LENGTH_LONG).show();
                    //finish();
                }
        }



    }
}
