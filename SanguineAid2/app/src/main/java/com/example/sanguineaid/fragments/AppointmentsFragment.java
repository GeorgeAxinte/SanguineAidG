package com.example.sanguineaid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.sanguineaid.R;
import com.example.sanguineaid.model.AppointmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsFragment extends Fragment {
    private ListView listAppointments;
    private ArrayAdapter<String> appointmentAdapter;
    private AppointmentViewModel appointmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointments, container, false);

        listAppointments = view.findViewById(R.id.list_appointments);

        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
        appointmentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, new ArrayList<>());
        listAppointments.setAdapter(appointmentAdapter);

        appointmentViewModel.getAppointments().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> appointments) {
                appointmentAdapter.clear();
                appointmentAdapter.addAll(appointments);
                appointmentAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
