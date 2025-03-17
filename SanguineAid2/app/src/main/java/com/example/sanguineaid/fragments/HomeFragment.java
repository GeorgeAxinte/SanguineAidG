package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sanguineaid.R;
import com.example.sanguineaid.model.AppointmentViewModel;
import com.example.sanguineaid.model.Campaign;

import java.util.Calendar;
import java.util.List;

public class HomeFragment extends Fragment {

    private TextView txtSoonestAppointment, txtFirstCampaignDetail;
    private AppointmentViewModel appointmentViewModel;
    private List<String> appointmentList;

    private List<Campaign> campaignList = List.of(
            new Campaign("eMAG Campaign – Donate blood and earn discounts", R.drawable.emag, "01-02-2025 - 28-02-2025", 15),
            new Campaign("FashionDays – Donate blood and get 20% off", R.drawable.fashion_days, "01-03-2025 - 31-03-2025", 20),
            new Campaign("Altex – Get rewarded for donating blood", R.drawable.altex, "05-03-2025 - 15-03-2025", 25)
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        txtSoonestAppointment = view.findViewById(R.id.txt_soonest_appointment_detail);
        txtFirstCampaignDetail = view.findViewById(R.id.txt_first_campaign_detail);

        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);

        appointmentViewModel.getAppointments().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> appointments) {
                appointmentList = appointments;
                displaySoonestAppointment();
            }
        });

        if (campaignList != null && !campaignList.isEmpty()) {
            txtFirstCampaignDetail.setText(campaignList.get(0).getTitle());
        } else {
            txtFirstCampaignDetail.setVisibility(View.GONE);
        }

        return view;
    }

    private void displaySoonestAppointment() {
        if (appointmentList == null || appointmentList.isEmpty()) {
            txtSoonestAppointment.setVisibility(View.GONE);
        } else {
            txtSoonestAppointment.setText(findSoonestAppointment());
        }
    }

    private String findSoonestAppointment() {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar soonestCalendar = null;
        String soonestAppointment = null;

        for (String appointment : appointmentList) {
            if (!appointment.contains("Date: ")) continue;

            String dateStr = appointment.split("Date: ")[1].split("\n")[0];
            String[] dateParts = dateStr.split("/");
            if (dateParts.length < 3) continue;

            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1;
            int year = Integer.parseInt(dateParts[2]);

            Calendar appointmentCalendar = Calendar.getInstance();
            appointmentCalendar.set(year, month, day);

            if (appointmentCalendar.after(currentCalendar)) {
                if (soonestCalendar == null || appointmentCalendar.before(soonestCalendar)) {
                    soonestCalendar = (Calendar) appointmentCalendar.clone();
                    soonestAppointment = appointment;
                }
            }
        }
        return soonestAppointment != null ? soonestAppointment : "No upcoming appointments.";
    }
}
