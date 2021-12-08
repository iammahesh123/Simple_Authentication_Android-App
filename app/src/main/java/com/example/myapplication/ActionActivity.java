package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class ActionActivity extends AppCompatActivity {
    MeowBottomNavigation bottomNavigation;


    // TextView logout;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        MaterialToolbar toolbar = findViewById(R.id.menu_bar);
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        NavigationView navigationView = findViewById(R.id.menu_view);


        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));


        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            drawerLayout.closeDrawer(GravityCompat.START);
            switch (id) {
                case R.id.menu_home:
                    Toast.makeText(ActionActivity.this, "Home Activity clicked", Toast.LENGTH_SHORT).show();
                case R.id.menu_message:
                    Toast.makeText(ActionActivity.this, "message Activity clicked", Toast.LENGTH_SHORT).show();
                case R.id.menu_sync:
                    Toast.makeText(ActionActivity.this, "sync Activity clicked", Toast.LENGTH_SHORT).show();
                case R.id.menu_trash:
                    Toast.makeText(ActionActivity.this, "trash Activity clicked", Toast.LENGTH_SHORT).show();

                case R.id.menu_settings:
                    Toast.makeText(ActionActivity.this, "setting Activity clicked", Toast.LENGTH_SHORT).show();
                default:
                    return true;

            }
        });


        // setSupportActionBar(toolbar);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        //add menu icons
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.widget));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.search));
        bottomNavigation.add(new MeowBottomNavigation.Model(4, R.drawable.account));


        bottomNavigation.setOnShowListener(item -> {

            Fragment fragment = null;
            //switching fragment activities
            switch (item.getId()) {
                case 1:
                case 2:
                    //setTitle("Home");
                    fragment = new HomeFragment();
                    break;
                case 3:
                    // setTitle("search");
                    fragment = new CourseFragment();
                    break;
                case 4:
                    // setTitle("Account");
                    fragment = new AccountFragment();
                    break;

            }
            loadFragment(fragment);
        });
        //set home count
        //bottomNavigation.setCount(1,"10");
        bottomNavigation.show(2, true);
        bottomNavigation.setOnClickMenuListener(item -> {
            //toasting message
            // Toast.makeText(getApplicationContext(),"", Toast.LENGTH_SHORT).show();
        });
        bottomNavigation.setOnReselectListener(item -> {
            //Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
        });


    }

    private void loadFragment(Fragment fragment) {
        //replacing fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();

    }
    /*

            @Override
            public void onBackPressed() {
            if(backPressedTime+2000 >System.currentTimeMillis())
            {
                super.onBackPressed();
                return;
            }
            else
            {
                backToast=Toast.makeText(ActionActivity.this, "press back again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }


    }
0o
     */
}