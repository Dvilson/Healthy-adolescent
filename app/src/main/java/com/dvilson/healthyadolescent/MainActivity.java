package com.dvilson.healthyadolescent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.dvilson.healthyadolescent.fragments.HomeFragment;
import com.dvilson.healthyadolescent.fragments.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {
    BottomNavigationViewEx mBottomNavigationViewEx;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    HomeFragment homeFragment;
    ProfilFragment profilFragment;
    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToolbar = findViewById(R.id.main_toolbar);
        mBottomNavigationViewEx = findViewById(R.id.nav_bottom);
        setSupportActionBar(mToolbar);

        mNavigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        mBottomNavigationViewEx.setOnNavigationItemSelectedListener(this);


        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        mFragmentTransaction.replace(R.id.frame_contenair, homeFragment);
        mFragmentTransaction.commit();

        init();

    }

    public void init() {
        mBottomNavigationViewEx = findViewById(R.id.nav_bottom);


    }

    private void checkAuthenticationState() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LogInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();

        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();


        }

    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_logout:


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.menu_item_home:
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    homeFragment = new HomeFragment();
                    mFragmentTransaction.replace(R.id.frame_contenair, homeFragment);
                    mFragmentTransaction.commit();
                    return true;

                case R.id.menu_item_profil:
                    mFragmentManager = getSupportFragmentManager();
                    mFragmentTransaction = mFragmentManager.beginTransaction();
                    profilFragment = new ProfilFragment();
                    mFragmentTransaction.replace(R.id.frame_contenair, profilFragment);
                    mFragmentTransaction.commit();
                    return true;
                default:
                    return true;
            }



    }

}