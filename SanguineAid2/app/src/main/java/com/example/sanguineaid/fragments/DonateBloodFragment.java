package com.example.sanguineaid.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sanguineaid.ApiClient;
import com.example.sanguineaid.R;
import com.example.sanguineaid.model.AppointmentViewModel;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import okhttp3.ResponseBody;import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonateBloodFragment extends Fragment {
    private Spinner spinnerCities, spinnerBloodType;
    private Button btnPickDate, btnSchedule;
    private TextView txtSelectedDate;
    private RadioGroup radioGroupArm;
    private CheckBox checkboxKnowsBloodType;
    private ListView listAppointments;
    private AppointmentViewModel appointmentViewModel;


    private List<String> cityList = Arrays.asList(
            "Arad", "Baia Mare", "Bacău", "Botoșani", "Brașov", "Brăila", "Bucharest",
            "Cluj-Napoca", "Constanța", "Craiova", "Drobeta-Turnu Severin", "Galați", "Iași",
            "Oradea", "Piatra Neamț", "Pitești", "Ploiești", "Râmnicu Vâlcea", "Satu Mare",
            "Sibiu", "Suceava", "Târgu Jiu", "Târgu Mureș", "Timișoara"
    );
    private List<String> bloodTypes = Arrays.asList("O", "A", "B", "AB");
    private List<String> appointmentList = new ArrayList<>();
    private ArrayAdapter<String> appointmentAdapter;
    private String selectedDate = "No date selected";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.donate_blood_fragment, container, false);
        appointmentViewModel = new ViewModelProvider(requireActivity()).get(AppointmentViewModel.class);
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
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        String[] selectedDateParts = selectedDate.split("/");
        int selectedDay = Integer.parseInt(selectedDateParts[0]);
        int selectedMonth = Integer.parseInt(selectedDateParts[1]) - 1;
        int selectedYear = Integer.parseInt(selectedDateParts[2]);

        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.set(selectedYear, selectedMonth, selectedDay);

        if (selectedCalendar.before(calendar)) {
            Toast.makeText(requireContext(), "You cannot select a past date!", Toast.LENGTH_SHORT).show();
            return;
        }

        String selectedCity = spinnerCities.getSelectedItem().toString();
        int selectedArmId = radioGroupArm.getCheckedRadioButtonId();
        String selectedArm = selectedArmId == R.id.radio_left_arm ? "Left" :
                (selectedArmId == R.id.radio_right_arm ? "Right" : null);
        String bloodType = checkboxKnowsBloodType.isChecked() ? spinnerBloodType.getSelectedItem().toString() : "Unknown";

        if (selectedCity.isEmpty() || selectedArm == null || selectedDate.equals("No date selected") ||
                (checkboxKnowsBloodType.isChecked() && bloodType.equals("Unknown"))) {
            Toast.makeText(requireContext(), "Please fill in all the fields correctly!", Toast.LENGTH_SHORT).show();
            return;
        }

        String appointment = "City: " + selectedCity +
                "\nDate: " + selectedDate +
                "\nArm: " + selectedArm +
                "\nBlood Type: " + bloodType;

        appointmentViewModel.addAppointment(appointment);

        Toast.makeText(requireContext(), "Appointment saved successfully!", Toast.LENGTH_SHORT).show();

        appointmentList.add(appointment);
        appointmentAdapter.notifyDataSetChanged();
        String loggedInUsername = getLoggedInUsername();
        if (loggedInUsername == null) {
            Toast.makeText(getContext(), "Error: User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }
        addPointsToUser(loggedInUsername);

    }

    private void addPointsToUser(String username) {
        ApiClient.getApiService().addPoints(username).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "5 points added!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to add points", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error adding points", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getLoggedInUsername() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sharedPreferences.getString("logged_in_username", null);
    }

}