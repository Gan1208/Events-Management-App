package com.example.eventsmanagementapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {

    public int getEventListId() {
        return eventListId;
    }

    public void setEventListId(int eventListId) {
        this.eventListId = eventListId;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "eventListId")
    private int eventListId;

    @ColumnInfo(name = "eventID")
    private String eventID;

    @ColumnInfo(name = "eventName")
    private String eventName;

    @ColumnInfo(name = "categoryId")
    private String categoryId;
    @ColumnInfo(name = "ticketAvailable")
    private int ticketAvailable;

    public boolean isActiveEvent() {
        return isActiveEvent;
    }

    public void setActiveEvent(boolean activeEvent) {
        isActiveEvent = activeEvent;
    }

    @ColumnInfo(name = "isActiveEvent")
    private boolean isActiveEvent;

    public Event(String eventID, String eventName, String categoryId, int ticketAvailable, boolean isActiveEvent) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.categoryId = categoryId;
        this.ticketAvailable = ticketAvailable;
        this.isActiveEvent = isActiveEvent;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public int getTicketAvailable() {
        return ticketAvailable;
    }


    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId= categoryId;
    }

    public void setTicketAvailable(int ticketAvailable) {
        this.ticketAvailable = ticketAvailable;
    }


}
