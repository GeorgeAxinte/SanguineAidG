package com.example.sanguineaid;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.sanguineaid.fragments.PointsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.view.MenuItem;

import com.example.sanguineaid.fragments.HomeFragment;
import com.example.sanguineaid.fragments.CampaignFragment;
import com.example.sanguineaid.fragments.DonateBloodFragment;
import com.example.sanguineaid.fragments.ProfileFragment;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                if (item.getItemId() == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (item.getItemId() == R.id.nav_campaigns) {
                    selectedFragment = new CampaignFragment();
                } else if (item.getItemId() == R.id.nav_donate) {
                    selectedFragment = new DonateBloodFragment();
                } else if (item.getItemId() == R.id.nav_more) {
                    selectedFragment = new ProfileFragment();
                }else if (item.getItemId() == R.id.nav_points) {
                    selectedFragment = new PointsFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment)
                            .commit();
                }

                return true;
            }
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
    }
}
