package com.example.eventsmanagementapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eventsmanagementapp.Event;

import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EMARepository mRepository;

    private LiveData<List<Event>> mAllEvent;

    public EventViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EMARepository(application);
        mAllEvent = mRepository.getAllEvents();
    }

    public LiveData<List<Event>> getAllEvents() {
        return mAllEvent;
    }

    public void insertEvent(Event event) {
        mRepository.insertEvent(event);
    }
    public void deleteAllEvent(){
        mRepository.deleteAllEvent();
    }
}
