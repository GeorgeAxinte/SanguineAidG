package com.example.sanguineaid.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentViewModel extends ViewModel {
    private final MutableLiveData<List<String>> appointments = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<String>> getAppointments() {
        return appointments;
    }

    public List<String> getAppointmentList() {
        return appointments.getValue(); // Return the current value of appointments
    }
    public void addAppointment(String appointment) {
        List<String> currentList = appointments.getValue();
        if (currentList != null) {
            currentList.add(appointment);
            appointments.setValue(currentList);
        }
    }
}
