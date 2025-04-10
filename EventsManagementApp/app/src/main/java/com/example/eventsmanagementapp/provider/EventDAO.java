package com.example.eventsmanagementapp.provider;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.eventsmanagementapp.Event;
import java.util.List;

@Dao
public interface EventDAO {

    @Query("select * from events")
    LiveData<List<Event>> getAllEvents();

    @Insert
    void addEvent(Event event);

    @Query("DELETE from events")
    void deleteAllEvents();

}