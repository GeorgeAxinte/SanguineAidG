package com.example.sanguineaid.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sanguineaid.R;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DonateBloodFragment extends Fragment {
    private Spinner spinnerCities, spinnerBloodType;
    private Button btnPickDate, btnSchedule;
    private TextView txtSelectedDate;
    private RadioGroup radioGroupArm;
    private CheckBox checkboxKnowsBloodType;
    private ListView listAppointments;

    private List<String> cityList = Arrays.asList("Bucharest", "Cluj-Napoca", "Timișoara", "Iași", "Constanța",
            "Craiova", "Brașov", "Galați", "Ploiești", "Oradea",
            "Brăila", "Arad", "Pitești", "Sibiu", "Bacău",
            "Târgu Mureș", "Baia Mare", "Buzău", "Botoșani", "Satu Mare",
            "Râmnicu Vâlcea", "Drobeta-Turnu Severin", "Suceava", "Piatra Neamț", "Târgu Jiu");
    private List<String> bloodTypes = Arrays.asList("O", "A", "B", "AB");
    private List<String> appointmentList = new ArrayList<>();
    private ArrayAdapter<String> appointmentAdapter;
    private String selectedDate = "No date selected";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donate_blood_fragment, container, false);

        spinnerCities = view.findViewById(R.id.spinner_cities);
        spinnerBloodType = view.findViewById(R.id.spinner_blood_type);
        btnPickDate = view.findViewById(R.id.btn_pick_date);
        btnSchedule = view.findViewById(R.id.btn_schedule);
        txtSelectedDate = view.findViewById(R.id.txt_selected_date);
        radioGroupArm = view.findViewById(R.id.radio_group_arm);
        checkboxKnowsBloodType = view.findViewById(R.id.checkbox_knows_blood_type);
        listAppointments = view.findViewById(R.id.list_appointments);

        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, cityList);
        spinnerCities.setAdapter(cityAdapter);

        ArrayAdapter<String> bloodAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, bloodTypes);
        spinnerBloodType.setAdapter(bloodAdapter);

        checkboxKnowsBloodType.setOnCheckedChangeListener((buttonView, isChecked) -> spinnerBloodType.setVisibility(isChecked ? View.VISIBLE : View.GONE));

        btnPickDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, year, month, dayOfMonth) -> {
                        selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                        txtSelectedDate.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        appointmentAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, appointmentList);
        listAppointments.setAdapter(appointmentAdapter);

        btnSchedule.setOnClickListener(v -> saveAppointment());

        return view;
    }

    private void saveAppointment() {
        String selectedCity = spinnerCities.getSelectedItem().toString();
        int selectedArmId = radioGroupArm.getCheckedRadioButtonId();
        String selectedArm = selectedArmId == R.id.radio_left_arm ? "Left" : "Right";
        String bloodType = checkboxKnowsBloodType.isChecked() ? spinnerBloodType.getSelectedItem().toString() : "Unknown";

        String appointment = "City: " + selectedCity +
                "\nDate: " + selectedDate +
                "\nArm: " + selectedArm +
                "\nBlood Type: " + bloodType;

        appointmentList.add(appointment);
        appointmentAdapter.notifyDataSetChanged();
    }
}