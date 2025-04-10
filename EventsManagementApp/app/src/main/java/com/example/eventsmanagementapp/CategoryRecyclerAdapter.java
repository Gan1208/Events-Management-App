package com.example.eventsmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryRecyclerAdapter.CustomViewHolderCategory>{
    private List<Category> categories;

    @NonNull
    @Override
    public CustomViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card_view, parent, false);
        return new CustomViewHolderCategory(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderCategory holder, int position) {
        holder.categoryIdCV.setText(categories.get(position).getCategoryID());
        holder.categoryNameCV.setText(categories.get(position).getCategoryName());
        holder.eventCountCV.setText(String.valueOf(categories.get(position).getEventCount()));
        if (categories.get(position).isActive()){
            holder.IsActiveCategoryCV.setText("Yes");
        }
        else{
            holder.IsActiveCategoryCV.setText("No");
        }


        holder.itemView.setOnClickListener(v -> {
            String selectedCategoryLocation = categories.get(position).getEventLocation();

            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, CategoryGoogleMap.class);
            intent.putExtra("selectedCategoryLocation",selectedCategoryLocation);
            intent.putExtra("selectedCategoryName",categories.get(position).getCategoryName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (categories == null)
            return 0;
        else
            return categories.size();
    }
    public void setData(List<Category> newData) {
        this.categories = newData;
    }

    public class CustomViewHolderCategory extends RecyclerView.ViewHolder {

        public TextView categoryIdCV;
        public TextView categoryNameCV;
        public TextView eventCountCV;
        public TextView IsActiveCategoryCV;




        public CustomViewHolderCategory(@NonNull View itemView) {
            super(itemView);
            categoryIdCV = itemView.findViewById(R.id.category_id);
            categoryNameCV = itemView.findViewById(R.id.category_name);
            eventCountCV = itemView.findViewById(R.id.event_count);
            IsActiveCategoryCV = itemView.findViewById(R.id.is_active_category);
        }


    }
}
