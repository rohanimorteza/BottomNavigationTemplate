package com.example.mortrza.mybottomnavigationtemplate;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.DefFrgCRS;
import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.DefFrgEdu;
import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.DefFrgTCH;
import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.DefFrgTRM;

public class DefinitionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definition);

        Bundle b = getIntent().getExtras();
        //Toast.makeText(getApplicationContext(),""+b.get("FRG").toString(),Toast.LENGTH_LONG).show();

        if(b.get("FRG").toString().equals("EDU")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container,new DefFrgEdu());
            transaction.commit();
        } else if(b.get("FRG").toString().equals("CRS")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container,new DefFrgCRS());
            transaction.commit();
        }else if(b.get("FRG").toString().equals("TRM")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container,new DefFrgTRM());
            transaction.commit();
        }else if(b.get("FRG").toString().equals("TCH")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.container,new DefFrgTCH());
            transaction.commit();
        }
    }
}
