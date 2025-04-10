package com.example.eventsmanagementapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    public int getCategoryListId() {
        return categoryListId;
    }

    public void setCategoryListId(int categoryListId) {
        this.categoryListId = categoryListId;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryListId")
    private int categoryListId;
    @ColumnInfo(name = "categoryID")
    private String categoryID;

    @ColumnInfo(name = "categoryName")
    private String categoryName;
    @ColumnInfo(name = "eventCount")
    private int eventCount;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @ColumnInfo(name = "isActive")
    private boolean isActive;

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }
    @ColumnInfo(name = "eventLocation")
    private String eventLocation;

    public Category(String categoryID, String categoryName, int eventCount, boolean isActive, String eventLocation) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.eventCount = eventCount;
        this.isActive = isActive;
        this.eventLocation = eventLocation;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public int getEventCount() {
        return eventCount;
    }



    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setEventCount(int eventCount) {
        this.eventCount = eventCount;
    }


}
