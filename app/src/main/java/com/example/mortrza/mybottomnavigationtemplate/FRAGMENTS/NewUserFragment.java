package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;


public class NewUserFragment extends Fragment {

    Spinner spinner;
    ImageView imgg;
    Button save;
    EditText name;
    LinearLayout imgselect;
    byte[] imageInByte;
    BottomSheetDialog mBottomSheetDialog;


    List<String> EducationNames = new ArrayList<>();
    List<String> EducationIDs  = new ArrayList<>();
    String id_education;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_newuser,container,false);

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

                //Toast.makeText(getContext(),imageInByte.length+" تصویر؟ ",Toast.LENGTH_LONG).show();


                if(name.getText().length()>0 &&  !id_education.equals("1") && imageInByte.length>0 ){
                    dbHandler dbHandler = new dbHandler(getContext());
                    dbHandler.open();
                    dbHandler.insertStudent(name.getText().toString(),id_education , imageInByte);
                    dbHandler.close();

                    name.getText().clear();

                    Toast.makeText(getContext()," ذخیره شد",Toast.LENGTH_LONG).show();
                    //finish();
                }

                if(name.getText().length()==0) {
                    Toast.makeText(getContext(),"نام را وارد کنید",Toast.LENGTH_LONG).show();
                }
                if( id_education.equals("1")) {
                    Toast.makeText(getContext(),"تحصیلات را انتخاب کنبد",Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    public void setupSpinner(){

        dbHandler dbh = new dbHandler(getContext());
        dbh.open();
        for(int i=0;i<dbh.displayEducationCount();i++){
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
                Intent intent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,2);

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

}
