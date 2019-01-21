package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.CrsAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.TrmAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Course;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Education;

import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import java.util.ArrayList;
import java.util.List;

import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_CRS;
import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_TCH;
import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_TRM;
import static com.example.mortrza.mybottomnavigationtemplate.MainActivity.Flag_called_from_crsfrg;

public class DefFrgCRS extends Fragment {

    EditText CrsNameEdt,CrsTuitionEdt;
    LinearLayout term;
    ImageView addImg;
    RecyclerView recyclerView;
    List<Education> EduList;
    dbHandler dbh;
    Spinner Tchspinner;
    static Dialog dialog;
    static TextView termName;
    static String TermId;

    List<String> TeacherNames  = new ArrayList<>();
    List<String> TeacherIDs  = new ArrayList<>();
    String id_teacher;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_def_crs,container,false);
        setupView(view);

        return view;
    }

    public void setupView(View view){

        Tchspinner = view.findViewById(R.id.spn_new_tch);
        termName = view.findViewById(R.id.txt_frg_def_crs_term);
        term = view.findViewById(R.id.lin_frg_def_crs_term);
        CrsNameEdt = view.findViewById(R.id.edt_def_name);
        CrsTuitionEdt = view.findViewById(R.id.edt_def_tuition);
        addImg = view.findViewById(R.id.img_def_add);
        recyclerView = view.findViewById(R.id.rec_def);
        EduList =new ArrayList<>();

        final Typeface tf_yekan = Typeface.createFromAsset(getContext().getAssets(),"byekan.ttf");
        termName.setTypeface(tf_yekan);


        dbh = new dbHandler(getContext());
        dbh.open();

        if(dbh.displayRowCount(TBL_CRS)>0){
            setupRecycler(dbh.displayCourse());
        }

        dbh.close();

        setupSpinner();

        term.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dbHandler dbHandler = new dbHandler(getContext());
                dbHandler.open();
                if(dbHandler.displayRowCount(TBL_TRM)>0){
                    TermId="-";
                    Flag_called_from_crsfrg="1";
                    selectTerm();
                }else {
                    Toast.makeText(getContext(),"لطفا از قسمت تعاریف ترم جدید تعریف نمایید.",Toast.LENGTH_SHORT).show();
                }
                dbHandler.close();



            }
        });



        Tchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_teacher = TeacherIDs.get(position);
                //Toast.makeText(getApplicationContext(),id_education,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if( id_teacher.equals("1")) {
                    Toast.makeText(getContext(),"نام استاد را انتخاب کنبد",Toast.LENGTH_LONG).show();
                }
                if( TermId.equals("-")) {
                    Toast.makeText(getContext(),"ترم  را انتخاب کنبد",Toast.LENGTH_LONG).show();
                }
                if (CrsTuitionEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"شهریه را وارد کنید",Toast.LENGTH_LONG).show();
                }
                if (CrsNameEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"نام دوره را صحیح وارد کنید",Toast.LENGTH_LONG).show();
                }
                if (!TermId.equals("-")  && !id_teacher.equals("1") && !CrsNameEdt.getText().toString().equals("") && !CrsTuitionEdt.getText().toString().equals("")) {

                    dbHandler dbh = new dbHandler(getContext());
                    dbh.open();
                    dbh.insertCrs(CrsNameEdt.getText().toString(), CrsTuitionEdt.getText().toString(),id_teacher,TermId);
                    CrsNameEdt.getText().clear();
                    CrsTuitionEdt.getText().clear();
                    TermId="-";
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

    public void setupSpinner(){

        dbHandler dbh = new dbHandler(getContext());
        dbh.open();
        for(int i=0;i<dbh.displayRowCount(TBL_TCH);i++){
            TeacherNames.add(dbh.displayTeacher(i+1));
            TeacherIDs.add(i+1+"");

        }
        dbh.close();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.support_simple_spinner_dropdown_item,TeacherNames);

        Tchspinner.setAdapter(adapter);

    }

    public void selectTerm(){
        View alertLayout = LayoutInflater.from(getContext()).inflate(R.layout.alert_select_term,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        refreshTerms(alertLayout);


        //////////////////
        dialog = alert.create();
        dialog.show();
    }
    private void refreshTerms(View m){


        final RecyclerView recyclerView = (RecyclerView) m.findViewById(R.id.rec_alert_select_term);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        final dbHandler dbh = new dbHandler(getContext());
        dbh.open();

            TrmAdapter trmAdapter = new TrmAdapter(getContext(),dbh.displayTerm());
            dbh.close();
            recyclerView.setAdapter(trmAdapter);
    }
    public  static void SelectedTerm(String id,String name,String year){

        TermId=id;
        termName.setText(name+"  "+"("+year+")");
        Flag_called_from_crsfrg="-";
        dialog.dismiss();

    }


}
