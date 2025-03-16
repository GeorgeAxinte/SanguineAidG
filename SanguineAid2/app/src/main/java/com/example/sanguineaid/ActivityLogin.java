package com.example.sanguineaid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import okhttp3.ResponseBody;

public class ActivityLogin extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> loginUser());
    }
    private void loginUser() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        ApiClient.getApiService().loginUser(new UserLogin(username, password)).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    startActivity(new Intent(ActivityLogin.this, DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(ActivityLogin.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ActivityLogin.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}