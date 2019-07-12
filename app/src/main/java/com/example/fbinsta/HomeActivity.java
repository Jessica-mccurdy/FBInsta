package com.example.fbinsta;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fbinsta.fragments.ComposeFragment;
import com.example.fbinsta.fragments.PostsFragment;
import com.example.fbinsta.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {


    //private static final String imagePath = "~/Downloads/puppy.jpg";
    private final String TAG = "HomeActivity";


    private BottomNavigationView bottomNavigationView;

    // Can be any fragment, `DetailFragment` is just an example
    Fragment fragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void onChangeProfile(MenuItem mi) {
        // handle click here
        Intent intent = new Intent(HomeActivity.this, ChangePicActivity.class);
        // show the activity
        HomeActivity.this.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        getSupportActionBar().setTitle("Instagram"); // set the top title






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
                        //Toast.makeText(HomeActivity.this, "Home", Toast.LENGTH_LONG).show();
                        break;
                        //return true;
                    case R.id.action_compose:
                        // do something here
                        fragment = new ComposeFragment();
                        break;
                        //return true;
                    case R.id.action_profile:
                        // do something here
                        fragment = new ProfileFragment();
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


    }

    /*
    //added

    // Now we can define the action to take in the activity when the fragment event fires
    // This is implementing the `OnItemSelectedListener` interface methods
    @Override
    public void onRssItemSelected(String link) {
        if (fragment != null && fragment.isInLayout()) {
            fragment.setText(link);
        }
    }
    */

}