package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

import com.example.sanguineaid.R;

import java.util.ArrayList;

public class DonationHistoryFragment extends Fragment {

    private ListView listDonations;
    private ArrayList<String> donationHistory;

    public DonationHistoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_donation_history, container, false);

        listDonations = rootView.findViewById(R.id.list_donations);

        donationHistory = new ArrayList<>();
        donationHistory.add("Donation 1: 10th March 2025 - Left Arm - 500ml");
        donationHistory.add("Donation 2: 1st February 2025 - Right Arm - 450ml");
        donationHistory.add("Donation 3: 25th December 2024 - Left Arm - 500ml");
        donationHistory.add("Donation 4: 15th November 2024 - Right Arm - 450ml");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, donationHistory);

        listDonations.setAdapter(adapter);

        return rootView;
    }
}
