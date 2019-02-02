package com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.CrsAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.TabViewPagerAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ADAPTERS.TrmAdapter;
import com.example.mortrza.mybottomnavigationtemplate.ENCAP.Student;
import com.example.mortrza.mybottomnavigationtemplate.R;
import com.example.mortrza.mybottomnavigationtemplate.dbHandler;

import static com.example.mortrza.mybottomnavigationtemplate.MainActivity.Flag_called_from_stdDetail_frg;
import static com.example.mortrza.mybottomnavigationtemplate.dbHandler.TBL_CRS;

public class FragmentStudentDetail extends Fragment {

    static Dialog dialog;
    static String CrsId;
    ImageView stdimg;
    Student student;
    TextView name,edu,debt;
    static Context c;
    static String stdId;
    TabLayout tabLayout;
    ViewPager viewPager;


    FloatingActionButton setting;
    BottomSheetDialog mBottomSheetDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_std_detail,container,false);
        //Toast.makeText(getContext(),getArguments().get("ID").toString(),Toast.LENGTH_SHORT).show();
        c = getContext();
        setupView(view);
        return view;
    }

    public void setupView(final View view){

        student = new Student();
        name = view.findViewById(R.id.txt_frg_detail_name);
        edu = view.findViewById(R.id.txt_frg_detail_edu);
        debt = view.findViewById(R.id.txt_frg_detail_debt);
        stdimg =  view.findViewById(R.id.img_frg_detail_avatar);
        setting = view.findViewById(R.id.fab_detail_std);
        tabLayout = view.findViewById(R.id.tabs_in_detail_std_frg);
        viewPager = view.findViewById(R.id.vp_in_detail_std_frg);

        TabViewPagerAdapter adapter = new TabViewPagerAdapter(getChildFragmentManager());
        Bundle b = new Bundle();
        b.putString("ID",getArguments().get("ID").toString());
        FragmentStudentDetailRegisteredTuition frgStdTui =new FragmentStudentDetailRegisteredTuition();
        adapter.addFragment(frgStdTui,"پرداختی ها");
        frgStdTui.setArguments(b);

        FragmentStudentDetailRegisteredCrs frgStdCrs = new FragmentStudentDetailRegisteredCrs();
        adapter.addFragment(frgStdCrs,"دوره ها");
        frgStdCrs.setArguments(b);

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        tabLayout.setupWithViewPager(viewPager);


        stdId = getArguments().get("ID").toString();

        dbHandler dbh = new dbHandler(getContext());
        dbh.open();
        student = dbh.displayStudent(stdId);
        debt.setText("وضعیت بدهی : "+dbh.Debt(stdId));

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

                dbHandler dbHandler = new dbHandler(getContext());
                dbHandler.open();
                if(dbHandler.displayRowCount(TBL_CRS)>0){
                    Flag_called_from_stdDetail_frg="1";
                    selectCrs();
                }else {
                    Toast.makeText(getContext(),"لطفا از قسمت تعاریف دوره جدید تعریف نمایید.",Toast.LENGTH_SHORT).show();
                }

                dbHandler.close();

            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Flag_called_from_stdDetail_frg="-";
                mBottomSheetDialog.dismiss();


            }
        });
    }

    public void selectCrs(){
        View alertLayout = LayoutInflater.from(getContext()).inflate(R.layout.alert_select_term,null);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(alertLayout);
        refreshCrs(alertLayout);


        //////////////////
        dialog = alert.create();
        dialog.show();
    }
    private void refreshCrs(View m){


        final RecyclerView recyclerView = (RecyclerView) m.findViewById(R.id.rec_alert_select_term);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        final dbHandler dbh = new dbHandler(getContext());
        dbh.open();

        CrsAdapter trmAdapter = new CrsAdapter(getContext(),dbh.displayCourse());
        dbh.close();
        recyclerView.setAdapter(trmAdapter);
    }

    public  static void SelectCrs(String id){

        CrsId=id;
        dbHandler dbh=new dbHandler(c);
        dbh.open();
        if(dbh.CheckDuplicateregisterdCrs(stdId,CrsId)<=0){
            dbh.insertCrsToStd(stdId,CrsId);
            Toast.makeText(c,"ثبت نام انجام شد",Toast.LENGTH_SHORT).show();

        }else {
            Toast.makeText(c,"قبلا در این دوره ثبت نام شده",Toast.LENGTH_SHORT).show();
        }

        dbh.close();
        Flag_called_from_stdDetail_frg="-";
        dialog.dismiss();
    }

}
