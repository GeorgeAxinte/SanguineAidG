package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.sanguineaid.R;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        Button btnViewAppointments = view.findViewById(R.id.btn_view_appointments);
        Button btnDonationHistory = view.findViewById(R.id.btn_donation_history);
        Button btnEligibilityCheck = view.findViewById(R.id.btn_eligibility_check);
        Button btnFAQs = view.findViewById(R.id.btn_faqs);


        btnViewAppointments.setOnClickListener(v -> {
            Fragment appointmentsFragment = new AppointmentsFragment();
            replaceFragment(appointmentsFragment);
        });

        btnDonationHistory.setOnClickListener(v -> {
            Fragment donationHistoryFragment = new DonationHistoryFragment();
            replaceFragment(donationHistoryFragment);
        });


        btnEligibilityCheck.setOnClickListener(v -> {
            Fragment eligibilityCheckFragment = new EligibilityCheckFragment();
            replaceFragment(eligibilityCheckFragment);
        });

        btnFAQs.setOnClickListener(v -> {
            Fragment faqsFragment = new FaqsFragment();
            replaceFragment(faqsFragment);
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
