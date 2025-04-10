package com.example.eventsmanagementapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    EditText loginUsername;
    EditText loginPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginUsername = findViewById(R.id.editLoginUsername);
        loginPassword = findViewById(R.id.editLoginPassword);
        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_ACCOUNT, MODE_PRIVATE);
        String username = sharedPreferences.getString(KeyStore.KEY_USER_NAME,"");
        loginUsername.setText(username);
    }

    public void onLogin1Click(View view){

        String GetUsername = loginUsername.getText().toString();
        String GetPassword = loginPassword.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences(KeyStore.FILE_ACCOUNT, MODE_PRIVATE);

        String UsernameRestored = sharedPreferences.getString(KeyStore.KEY_USER_NAME, "Unknown");
        String PasswordRestored = sharedPreferences.getString(KeyStore.KEY_PASSWORD, " ");

        if(!GetUsername.equals(UsernameRestored) || !GetPassword.equals(PasswordRestored)){
            Toast.makeText(this,"Invalid username or password.",Toast.LENGTH_SHORT).show();
        }

        else {
            Toast.makeText(this,"Login successfully.",Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, Dashboard.class);
            startActivity(i);
        }
    }

    public void onRegister1Click(View view){
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
    }
}