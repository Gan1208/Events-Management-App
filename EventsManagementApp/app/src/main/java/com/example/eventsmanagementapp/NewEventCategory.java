package com.example.eventsmanagementapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.eventsmanagementapp.provider.CategoryViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class NewEventCategory extends AppCompatActivity {

    EditText categoryIdET;
    EditText categoryNameET;
    EditText eventCountET;
    Switch isActiveCategoryET;
    EditText categoryLocationET;
    private CategoryViewModel categoryViewModel;

    MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event_category);
        categoryIdET = findViewById(R.id.editCategoryID);
        categoryNameET = findViewById(R.id.editCategoryName);
        eventCountET = findViewById(R.id.editEventCount);
        isActiveCategoryET = findViewById(R.id.switchIsActiveCategory);
        categoryLocationET = findViewById(R.id.editCategoryLocation);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
    }
    public void onSaveCategoryClick(View view) {

        String newCategoryName = categoryNameET.getText().toString();
        String newEventCount = eventCountET.getText().toString();
        int newEventCountNumber = KeyStore.DEFAULT_KEY_NUMBER;
        if(!newEventCount.trim().isEmpty() ) {
            newEventCountNumber = Integer.parseInt(newEventCount);
        }

        if(newCategoryName.matches("^[a-zA-z]+[a-zA-z0-9\\s]*") && newEventCountNumber>=0 ) {
            String newCategoryID = generateCategoryID();
            boolean newIsActiveCategory = isActiveCategoryET.isChecked();
            String newCategoryLocation = categoryLocationET.getText().toString();
            Category c = new Category(newCategoryID, newCategoryName, newEventCountNumber,newIsActiveCategory,newCategoryLocation);
            categoryViewModel.insertCategory(c);
            Toast.makeText(this, "Category saved successfully:" + newCategoryID + ".", Toast.LENGTH_LONG).show();
            finish();
       }
       else {
           Toast.makeText(this, "Unknown or invalid command", Toast.LENGTH_LONG).show();
       }
    }

    public static String generateCategoryID() {
        StringBuilder stringBuilder = new StringBuilder("C");
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            stringBuilder.append((char) ('A' + random.nextInt(26) ));
        }
        stringBuilder.append("-");
        int randomNumber = random.nextInt(10000);
        String fourDigitsRandomNumber = String.format("%04d", randomNumber);
        stringBuilder.append(fourDigitsRandomNumber);
        return stringBuilder.toString();
    }
    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(KeyStore.SMS_MSG_KEY);
            StringTokenizer stringTokenizer = new StringTokenizer(msg, ";");
            if(stringTokenizer.countTokens()!=3){
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_LONG).show();
                return;
            }
            String categoryNameMessage = stringTokenizer.nextToken();
            String eventCountString = stringTokenizer.nextToken();
            String getIsActive = stringTokenizer.nextToken().toLowerCase();
            if (!categoryNameMessage.startsWith("category:") || categoryNameMessage.replace("category:","").trim().isEmpty() || (!getIsActive.equals("true") && !getIsActive.equals("false"))) {
                Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    int eventCountMessage = Integer.parseInt(eventCountString);
                    boolean isActiveMessage = Boolean.parseBoolean(getIsActive);
                    categoryNameET.setText(categoryNameMessage.replaceFirst("category:", ""));
                    eventCountET.setText(String.valueOf(eventCountMessage));
                    isActiveCategoryET.setChecked(isActiveMessage);}
                catch (NumberFormatException e) {
                    Toast.makeText(context, "Unknown or invalid command", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myBroadCastReceiver, new IntentFilter(KeyStore.SMS_FILTER), RECEIVER_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myBroadCastReceiver);
    }
}


