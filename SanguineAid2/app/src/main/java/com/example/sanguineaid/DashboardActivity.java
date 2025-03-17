package com.example.sanguineaid;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        welcomeMessage = findViewById(R.id.welcomeMessage);

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
                } else if (item.getItemId() == R.id.nav_points) {
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

        String loggedInUsername = getLoggedInUsername();
        if (loggedInUsername != null) {
            getFirstNameAndSetWelcomeMessage(loggedInUsername);
        }
    }

    private String getLoggedInUsername() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getString("logged_in_username", null);
    }

    private void getFirstNameAndSetWelcomeMessage(String username) {
        ApiClient.getApiService().get_firstname(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String firstName = response.body().string();

                        firstName = firstName.replace("\"", "").trim();

                        welcomeMessage.setText("Welcome, " + firstName + "!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        welcomeMessage.setText("Welcome, User!");
                    }
                } else {
                    welcomeMessage.setText("Welcome, User!");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                welcomeMessage.setText("Welcome, User!");
            }
        });
    }
}
