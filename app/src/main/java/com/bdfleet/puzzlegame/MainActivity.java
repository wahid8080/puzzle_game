package com.bdfleet.puzzlegame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_RECEIVER = 5;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_NUMBERS)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG) !=PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.READ_PHONE_NUMBERS,
                    Manifest.permission.READ_CALL_LOG
            },PERMISSION_REQUEST_RECEIVER);

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case PERMISSION_REQUEST_RECEIVER:
                    if (3<=grantResults.length
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED
                            && grantResults[1] == PackageManager.PERMISSION_GRANTED
                            && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(MainActivity.this,"Thank Yu permission",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Well I can't do anything until you permit me",Toast.LENGTH_LONG).show();
                    }
                    return;

            }
    }
}
