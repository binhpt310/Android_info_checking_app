package com.example.myapplication;
import android.app.Activity;
import android.content.Context;
import android.hardware.camera2.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Check_Camera extends AppCompatActivity {

    public void getCameraCount() {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        TextView Number = findViewById(R.id.camera_number_txt);
        TextView Error = findViewById(R.id.error_checking_txt);
        int cam_number1;
        try {
            if (cameraManager != null) {
                cam_number1 = cameraManager.getCameraIdList().length;
                String cam_number2 = Integer.toString(cam_number1);
                Number.setText(cam_number2);
            }
        }
        catch (CameraAccessException e) {
            Error.setText("Unable to enumerate cameras");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_camera);
        getCameraCount();
        }



}



