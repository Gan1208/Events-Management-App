package com.example.eventsmanagementapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.eventsmanagementapp.provider.CategoryViewModel;
import com.example.eventsmanagementapp.provider.EventViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dashboard extends AppCompatActivity {

    EditText eventIDET;
    EditText eventNameET;
    EditText categoryIDET;
    EditText ticketAvailableET;
    Switch isActiveET;
    DrawerLayout drawerLayout;
    Toolbar myToolbar;
    NavigationView navigationView;
    TextView gestureTv;
    View touchPad;
    FloatingActionButton fab;

    private EventViewModel eventViewModel;
    private CategoryViewModel categoryViewModel;
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_drawer_layout);

        eventIDET = findViewById(R.id.editTextEventId);
        eventNameET = findViewById(R.id.editTextEventName);
        categoryIDET = findViewById(R.id.editTextCategoryIdEvent);
        ticketAvailableET = findViewById(R.id.editTextTicketAvailable);
        isActiveET = findViewById(R.id.switchIsActiveEvent);

        drawerLayout = findViewById(R.id.drawer_layout);
        myToolbar = findViewById(R.id.toolbarDashboard);
        setSupportActionBar(myToolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, myToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        gestureTv = findViewById(R.id.textViewGesture);
        touchPad = findViewById(R.id.viewGesture);
        MyGestureListener myGestureListener= new MyGestureListener();
        gestureDetector = new GestureDetector(this,myGestureListener);
        touchPad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEventName = eventNameET.getText().toString().trim();
                String newCategoryIDEvent = categoryIDET.getText().toString().trim();
                String newTicketAvailable = ticketAvailableET.getText().toString();
                boolean newIsActiveEvent = isActiveET.isChecked();
                int newTicketAvailableDB = KeyStore.DEFAULT_KEY_NUMBER;
                if (!newTicketAvailable.trim().isEmpty()) {
                    newTicketAvailableDB = Integer.parseInt(newTicketAvailable);
                }

                boolean isValidEventName = newEventName.matches("^[a-zA-z]+[a-zA-z0-9\\s]*");
                boolean isValidTicketAvailable = (newTicketAvailableDB >= 0);
                if (!isValidEventName || !isValidTicketAvailable) {
                    Toast.makeText(Dashboard.this, "Unknown or invalid command", Toast.LENGTH_LONG).show();
                    return;
                }

                int finalNewTicketAvailableDB = newTicketAvailableDB;
                LiveData<List<Category>> getExistedId = categoryViewModel.getCategory(categoryIDET.getText().toString());
                getExistedId.observe(Dashboard.this, existedCategoryId ->{
                    if(!existedCategoryId.isEmpty()){
                        String newEventID = generateEventID();
                        Event e = new Event(newEventID, newEventName, newCategoryIDEvent, finalNewTicketAvailableDB,newIsActiveEvent);
                        eventViewModel.insertEvent(e);
                        existedCategoryId.get(0).setEventCount(existedCategoryId.get(0).getEventCount()+1);
                        categoryViewModel.updateEventCount(existedCategoryId.get(0));

                        Snackbar.make(view, "Event saved successfully:" + newEventID + ".", Snackbar.LENGTH_LONG)
                                .setAction(R.string.undo_string, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                        getExistedId.removeObservers(Dashboard.this);
                    }
                    else{
                        Toast.makeText(Dashboard.this, "Unknown Category ID", Toast.LENGTH_LONG).show();
                        getExistedId.removeObservers(Dashboard.this);
                    }
                });


            }
        });
    }


private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

    @Override
    public void onLongPress(@NonNull MotionEvent e) {
        gestureTv.setText("onLongPress");
        onClearEventFormCLick(null);
        super.onLongPress(e);
    }

    @Override
    public boolean onDoubleTap(@NonNull MotionEvent e) {
        gestureTv.setText("onDoubleTap");
        fab.performClick();
        return true;
    }
}

    public static String generateEventID() {
        StringBuilder stringBuilder = new StringBuilder("E");
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            stringBuilder.append((char) ('A' + random.nextInt(26) ));
        }
        stringBuilder.append("-");
        int randomNumber = random.nextInt(10000);
        String fiveDigitsRandomNumber = String.format("%05d", randomNumber);
        stringBuilder.append(fiveDigitsRandomNumber);
        return stringBuilder.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // match the id of selected item and do something
        if (item.getItemId() == R.id.option_refresh) {

        }else if (item.getItemId() == R.id.option_clear_event_form) {
            onClearEventFormCLick(null);
        }else if (item.getItemId() == R.id.option_delete_all_categories) {
            categoryViewModel.deleteAllCategory();

        }else if (item.getItemId() == R.id.option_delete_all_events) {
            eventViewModel.deleteAllEvent();
        }
        return true;
    }

    public void onClearEventFormCLick(View view){
        eventIDET.setText("");
        eventNameET.setText("");
        categoryIDET.setText("");
        ticketAvailableET.setText("");
        isActiveET.setChecked(false);
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.option_view_all_categories) {
                onViewAllCategoryClick(null);
            } else if (id == R.id.option_add_category) {
                onAddCategoryClick(null);
            }
            else if (id == R.id.option_view_all_events) {
                onViewAllEventClick(null);
            }
            else if (id == R.id.option_logout) {
                onLogoutClick(null);
            }
            drawerLayout.closeDrawers();
            return true;
        }
    }

    public void onAddCategoryClick(View view){
        Intent i = new Intent(this, NewEventCategory.class);
        startActivity(i);
    }

    public void onViewAllEventClick(View view){
        Intent i = new Intent(this, ListEvent.class);
        startActivity(i);
    }

    public void onViewAllCategoryClick(View view){
        Intent i = new Intent(this, ListCategory.class);
        startActivity(i);
    }

    public void onLogoutClick(View view){
        finish();

    }

}