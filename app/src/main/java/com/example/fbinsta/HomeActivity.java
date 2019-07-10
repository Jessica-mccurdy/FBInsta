package com.example.fbinsta;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fbinsta.fragments.ComposeFragment;
import com.example.fbinsta.fragments.PostsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {


    //private static final String imagePath = "~/Downloads/puppy.jpg";
    private final String TAG = "HomeActivity";


    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();


        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = new ComposeFragment();
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        //TODO Swap fragment here
                        // do something here
                        fragment = new PostsFragment();
                        Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_LONG).show();
                        break;
                        //return true;
                    case R.id.action_compose:
                        // do something here
                        fragment = new ComposeFragment();
                        break;
                        //return true;
                    case R.id.action_profile:
                        // do something here
                        fragment = new ComposeFragment();
                        break;
                        //return true;
                    default:
                        //TODO Swap fragment here

                        //return true;
                }
                fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                return true;
            }
        });

        //set default selection
        bottomNavigationView.setSelectedItemId(R.id.action_home);


    }}