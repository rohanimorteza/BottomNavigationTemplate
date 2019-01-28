package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.TrmAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Term;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import java.util.ArrayList;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;

import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_TRM;

public class DefFrgTRM extends Fragment {

    EditText TrmNameEdt,TrmYearEdt;
    ImageView addImg;
    RecyclerView recyclerView;
    List<Term> TrmList;
    dbHandler dbh;
    CardView CrdStart,CrdEnd;
    TextView tBegin;
    String sBegin="-";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_def_trm,container,false);
        setupView(view);

        return view;
    }

    public void setupView(View view){

        final Typeface tf_yekan = Typeface.createFromAsset(getContext().getAssets(),"byekan.ttf");

        TrmNameEdt = view.findViewById(R.id.edt_deftrm_name);
        TrmYearEdt = view.findViewById(R.id.edt_deftrm_year);
        addImg = view.findViewById(R.id.img_def_add);
        recyclerView = view.findViewById(R.id.rec_deftrm);
        CrdStart = view.findViewById(R.id.crd_trmfrg_start);
        CrdEnd = view.findViewById(R.id.crd_trmfrg_end);
        tBegin = view.findViewById(R.id.txt_trmfrg_start);

        TrmYearEdt.setTypeface(tf_yekan);


        TrmList = new ArrayList<>();

        dbh = new dbHandler(getContext());
        dbh.open();

        if(dbh.displayRowCount(TBL_TRM)>0){
            setupRecycler(dbh.displayTerm());
        }

        dbh.close();


        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sBegin.equals("-")){
                    Toast.makeText(getContext(),"تاریخ آغاز ترم را انتخاب کنید",Toast.LENGTH_LONG).show();
                }

                if (TrmYearEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"سال را وارد کنید",Toast.LENGTH_LONG).show();
                }
                if (TrmNameEdt.getText().toString().equals("")){
                    Toast.makeText(getContext(),"نام ترم را صحیح وارد کنید",Toast.LENGTH_LONG).show();
                }
                if (!TrmNameEdt.getText().toString().equals("") && !TrmYearEdt.getText().toString().equals("") && !sBegin.equals("-")) {

                    dbHandler dbh = new dbHandler(getContext());
                    dbh.open();
                    dbh.insertTrm(TrmNameEdt.getText().toString(), TrmYearEdt.getText().toString() , sBegin);
                    dbh.close();

                    dbh = new dbHandler(getContext());
                    dbh.open();
                    setupRecycler(dbh.displayTerm());
                    dbh.close();


                    Toast.makeText(getContext(),"ذخیره شد",Toast.LENGTH_LONG).show();
                    TrmNameEdt.getText().clear();
                    TrmYearEdt.getText().clear();
                    sBegin="-";
                    tBegin.setText("آغاز : ");
                }

            }
        });


        CrdStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Typeface tf_yekan = Typeface.createFromAsset(getContext().getAssets(),"byekan.ttf");

                PersianDatePickerDialog picker = new PersianDatePickerDialog(getContext())
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setMinYear(1395)
                        .setActionTextColor(Color.GRAY)
                        .setTypeFace(tf_yekan)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                //Toast.makeText(getContext(), persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();

                                tBegin.setText("آغاز : "+persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay());
                                tBegin.setTypeface(tf_yekan);
                                sBegin=persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay();
                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();

            }
        });

        CrdEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public  void  setupRecycler(List<Term> TermList){

        if(TermList.size()>0){
            TrmAdapter trmAdapter = new TrmAdapter(getContext(),TermList);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
            recyclerView.setAdapter(trmAdapter);
        }else{
            recyclerView.setAdapter(null);
        }
    }

}
