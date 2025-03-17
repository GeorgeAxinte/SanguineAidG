package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sanguineaid.R;

public class PointsFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView pointsDescription;
    private Button earnMorePointsButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.points_fragment, container, false);

        progressBar = view.findViewById(R.id.points_progress_bar);
        pointsDescription = view.findViewById(R.id.points_description);
        earnMorePointsButton = view.findViewById(R.id.earn_more_points_button);

        progressBar.setProgress(25);

        pointsDescription.setText("Earn points by donating blood, participating in campaigns, and more!\n" +
                "The more you donate, the more points you earn.");

        earnMorePointsButton.setOnClickListener(v -> {
            Fragment donateBloodFragment = new DonateBloodFragment();
            replaceFragment(donateBloodFragment);
        });

        return view;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
