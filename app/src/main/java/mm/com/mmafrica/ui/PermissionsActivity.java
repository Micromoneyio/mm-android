package mm.com.mmafrica.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import mm.com.mmafrica.R;

public class PermissionsActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 1003;

    private Button mAllowButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (allPermissionsGranted()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_permissions);

        mAllowButton = findViewById(R.id.allow);
        mAllowButton.setOnClickListener(v -> {
            requestPermissions();
        });
    }

    private boolean allPermissionsGranted() {
        boolean readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        boolean accessLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean readContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;

        return readPhoneState && accessLocation && readContacts;
    }

    private void requestPermissions() {
        boolean readPhoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        boolean accessLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean readContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED;

        if (!readPhoneState || !accessLocation || !readContacts) {
            List<String> permissions = new ArrayList<>();
            if (!readPhoneState)
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            if (!accessLocation)
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            if (!readContacts)
                permissions.add(Manifest.permission.READ_CONTACTS);
            ActivityCompat.requestPermissions(this, permissions.toArray(new String[permissions.size()]), PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (allPermissionsGranted())
            startActivity(new Intent(this, MainActivity.class));
    }
}
