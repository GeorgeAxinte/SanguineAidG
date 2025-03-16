package com.example.sanguineaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class LoadingScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);

        VideoView videoView = findViewById(R.id.videoView);

        Uri videoPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.loadingscreen);
        videoView.setVideoURI(videoPath);

        videoView.setOnCompletionListener(mp -> goToLoginRegisterActivity());

        videoView.start();
    }

    private void goToLoginRegisterActivity() {
        Intent intent = new Intent(LoadingScreen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
