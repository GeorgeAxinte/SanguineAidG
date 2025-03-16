package com.example.sanguineaid;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etEmail, etFirstName, etLastName, etAge, etDateOfBirth, etCity, etAddress;
    private Button btnRegister;
    private CheckBox hasdonatedbloodbeforeCheckBox;
    private RadioGroup genderRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etEmail = findViewById(R.id.etEmail);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAge = findViewById(R.id.etAge);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        etDateOfBirth = findViewById(R.id.etDateOfBirth);
        etCity = findViewById(R.id.etCity);
        etAddress = findViewById(R.id.etAddress);
        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> registerUser());
        hasdonatedbloodbeforeCheckBox = findViewById(R.id.hasdonatedbloodbeforeCheckBox);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                    etDateOfBirth.setText(selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void registerUser() {
        try {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            String email = etEmail.getText().toString();
            String first_name = etFirstName.getText().toString();
            String last_name = etLastName.getText().toString();
            String ageString = etAge.getText().toString();
            String date_of_birth = etDateOfBirth.getText().toString();
            String city = etCity.getText().toString();
            String address = etAddress.getText().toString();
            boolean hasdonatedbloodbefore = hasdonatedbloodbeforeCheckBox.isChecked();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty() || first_name.isEmpty() || last_name.isEmpty() ||
                    ageString.isEmpty() || date_of_birth.isEmpty() || city.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_SHORT).show();
                return;
            }

            int age = Integer.parseInt(ageString);
            if (age <= 0) {
                Toast.makeText(this, "Invalid age", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyy-MM-dd");
            Date parsedDate;
            try {
                parsedDate = sdfInput.parse(date_of_birth);
                date_of_birth = sdfOutput.format(parsedDate);
            } catch (ParseException e) {
                Toast.makeText(this, "Invalid Date Format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                return;
            }

            if (genderRadioGroup.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return;
            }

            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            String gender = (selectedGenderId == R.id.maleRadioButton) ? "Male" : "Female";

            User user = new User(username, password, email, first_name, last_name, age, gender, date_of_birth, city, address, hasdonatedbloodbefore);

            Log.d("RegisterActivity", "User Data: " + new Gson().toJson(user));

            ApiClient.getApiService().registerUser(user).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Toast.makeText(this, "Invalid Input: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isValidDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
