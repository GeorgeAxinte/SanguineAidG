package com.example.sanguineaid.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sanguineaid.ApiClient;
import com.example.sanguineaid.R;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PointsFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView pointsDescription;
    private Button earnMorePointsButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.points_fragment, container, false);

        progressBar = view.findViewById(R.id.points_progress_bar);
        pointsDescription = view.findViewById(R.id.points_description);
        earnMorePointsButton = view.findViewById(R.id.earn_more_points_button);

        String loggedInUsername = getLoggedInUsername();
        if (loggedInUsername != null) {
            getUserPoints(loggedInUsername);
        } else {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
        }

        earnMorePointsButton.setOnClickListener(v -> {
            Fragment donateBloodFragment = new DonateBloodFragment();
            replaceFragment(donateBloodFragment);
        });

        return view;
    }

    private void getUserPoints(String username) {
        ApiClient.getApiService().getPoints(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body().string();
                        Log.d("API_RESPONSE", responseBody);

                        JSONObject jsonObject = new JSONObject(responseBody);
                        int points = jsonObject.getInt("points");

                        progressBar.setProgress(points);
                        pointsDescription.setText("You have earned " + points + " points!");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error parsing points data", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Log.e("API_ERROR", "Response code: " + response.code() + ", error: " + errorBody);
                        Toast.makeText(getContext(), "Failed to retrieve points", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Error reading error response", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error retrieving points", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getLoggedInUsername() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sharedPreferences.getString("logged_in_username", null);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
