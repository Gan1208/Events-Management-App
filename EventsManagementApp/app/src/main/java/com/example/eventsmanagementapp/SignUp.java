package com.example.eventsmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    EditText registerUsername;
    EditText registerPassword;
    EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ActivityCompat.requestPermissions(this, new String[]{
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS
        }, 0);

        registerUsername = findViewById(R.id.editRegisterUsername);
        registerPassword = findViewById(R.id.editRegisterPassword);
        confirmPassword = findViewById(R.id.editConfirmPassword);
    }

    public void onRegisterClick(View view){
        String GetUsername = registerUsername.getText().toString();
        String GetPassword = registerPassword.getText().toString();
        String GetConfirmPassword = confirmPassword.getText().toString();

        if(GetUsername.trim().isEmpty() || GetPassword.trim().isEmpty() ){
            Toast.makeText(this,"Invalid username or password.",Toast.LENGTH_SHORT).show();
        }

        else if(!GetConfirmPassword.equals(GetPassword)){
            Toast.makeText(this,"Please confirm your password.",Toast.LENGTH_SHORT).show();
        }

        else {
            SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_ACCOUNT, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(KeyStore.KEY_USER_NAME, GetUsername);
            editor.putString(KeyStore.KEY_PASSWORD, GetPassword);
            editor.apply();

            registerUsername.setText("");
            registerPassword.setText("");
            Toast.makeText(this,"Registration successfully.",Toast.LENGTH_SHORT).show();

            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
    }

    public void onLogInClick(View view){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        }
}


