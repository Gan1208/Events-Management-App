package com.example.eventsmanagementapp.provider;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.eventsmanagementapp.Category;
import java.util.List;

@Dao
public interface CategoryDAO {

    @Query("select * from categories")
    LiveData<List<Category>> getAllCategories();

    @Query("select * from categories where categoryID=:id")
    public LiveData<List<Category>> getCategory(String id);

    @Update
    void updateEventCount(Category category);

    @Insert
    void addCategory(Category category);

    @Query("delete FROM categories")
    void deleteAllCategories();
}
