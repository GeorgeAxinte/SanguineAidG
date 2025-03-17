package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sanguineaid.R;

public class PointsFragment extends Fragment {
    private ProgressBar progressBar;
    private TextView pointsDescription;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.points_fragment, container, false);

        progressBar = view.findViewById(R.id.points_progress_bar);
        pointsDescription = view.findViewById(R.id.points_description);

        progressBar.setProgress(25);

        pointsDescription.setText("Earn points by donating blood, participating in campaigns, and more!\n" +
                "The more you donate, the more points you earn.");

        return view;
    }
}
