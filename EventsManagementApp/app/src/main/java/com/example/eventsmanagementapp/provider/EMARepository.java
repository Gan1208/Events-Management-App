package com.example.eventsmanagementapp.provider;


import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.eventsmanagementapp.Category;
import com.example.eventsmanagementapp.Event;
import java.util.List;
public class EMARepository {
    private CategoryDAO categoryDAO;
    private EventDAO eventDAO;

    private LiveData<List<Category>> allCategoriesLiveData;
    private LiveData<List<Event>> allEventsLiveData;

    EMARepository(Application application){
        EMADatabase db = EMADatabase.getDataBase(application);
        categoryDAO = db.categoryDAO();
        eventDAO = db.eventDAO();
        allCategoriesLiveData = categoryDAO.getAllCategories();
        allEventsLiveData = eventDAO.getAllEvents();
    }

    LiveData<List<Category>> getAllCategories(){
        return allCategoriesLiveData;
    }

    LiveData<List<Event>> getAllEvents(){
        return allEventsLiveData;
    }

    void insertCategory(Category category) {
        // Executes the database operation to insert the item in a background thread.
        EMADatabase.databaseWriteExecutor.execute(() -> categoryDAO.addCategory(category));
    }

    void insertEvent(Event event) {
        // Executes the database operation to insert the item in a background thread.
        EMADatabase.databaseWriteExecutor.execute(() -> eventDAO.addEvent(event));
    }
    void deleteAllCategory(){
        EMADatabase.databaseWriteExecutor.execute(()->{
            categoryDAO.deleteAllCategories();
        });
    }
    void deleteAllEvent(){
        EMADatabase.databaseWriteExecutor.execute(()->{
            eventDAO.deleteAllEvents();
        });
    }

    public LiveData<List<Category>> getCategory(String id){
            return categoryDAO.getCategory(id);
    }

    void updateEventCount(Category category){
        EMADatabase.databaseWriteExecutor.execute(() -> categoryDAO.updateEventCount(category));
    }
}
