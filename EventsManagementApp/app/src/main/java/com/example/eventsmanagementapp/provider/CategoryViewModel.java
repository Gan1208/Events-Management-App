package com.example.eventsmanagementapp.provider;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.eventsmanagementapp.Category;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private EMARepository mRepository;
    private LiveData<List<Category>> mAllCategory;


    public CategoryViewModel(@NonNull Application application) {
        super(application);
        mRepository = new EMARepository(application);
        mAllCategory = mRepository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategory() {
        return mAllCategory;
    }



    public void insertCategory(Category category) {
        mRepository.insertCategory(category);
    }




    public void deleteAllCategory(){
        mRepository.deleteAllCategory();
    }


    public LiveData<List<Category>> getCategory(String id) {
        return mRepository.getCategory(id);
    }

    public  void updateEventCount(Category category){
        EMADatabase.databaseWriteExecutor.execute(() -> mRepository.updateEventCount(category));
    }
}
