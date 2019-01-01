package com.example.mortrza.mybottomnavigationtemplate;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.DefFragment;
import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.HomeFragment;
import com.example.mortrza.mybottomnavigationtemplate.FRAGMENTS.NewUserFragment;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("");

        if (SDK_INT >17){
            getWindow().peekDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        dbHandler dbHandler = new dbHandler(this);
        dbHandler.open();
        Toast.makeText(getApplicationContext(),dbHandler.displayEducation(1),Toast.LENGTH_LONG).show();
        dbHandler.close();

        bottomNavigationView = findViewById(R.id.bottomnavigation);

        Menu menu = bottomNavigationView.getMenu();
        //bottomNavigationView.setItemIconTintList(null);
        selectFragment(menu.getItem(0));

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return false;
            }
        });

    }

    public void selectFragment(MenuItem item){
        item.setChecked(true);
        int id = item.getItemId();
        switch (id){
            case R.id.navigation_home :
                setFragment(new HomeFragment());
                break;
            case R.id.navigation_new_user :
                setFragment(new NewUserFragment());
                break;
            case R.id.navigation_definition :
                setFragment(new DefFragment());
                break;
        }
    }

    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
    }

}
