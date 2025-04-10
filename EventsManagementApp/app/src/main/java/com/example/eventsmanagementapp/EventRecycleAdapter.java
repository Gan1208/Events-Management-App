package com.example.eventsmanagementapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventRecycleAdapter extends RecyclerView.Adapter<EventRecycleAdapter.CustomViewHolderEvent>{

    private List<Event> events;

    @NonNull
    @Override
    public CustomViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_view, parent, false);
        return new CustomViewHolderEvent(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderEvent holder, int position) {
        holder.eventIdCV.setText(events.get(position).getEventID());
        holder.eventNameCV.setText(events.get(position).getEventName());
        holder.categoryIDCV.setText(events.get(position).getCategoryId());
        holder.ticketAvailableCV.setText(String.valueOf(events.get(position).getTicketAvailable()));
        if (events.get(position).isActiveEvent()){
            holder.isActiveEventCV.setText("Yes");
        }
        else{
            holder.isActiveEventCV.setText("No");
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, EventGoogleResult.class);
            intent.putExtra("selectedEventName",events.get(position).getEventName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (events == null)
            return 0;
        else
            return events.size();
    }
    public void setData(List<Event> newData) {
        this.events = newData;
    }

    public class CustomViewHolderEvent extends RecyclerView.ViewHolder {

        public TextView eventIdCV;
        public TextView eventNameCV;
        public TextView categoryIDCV;
        public TextView ticketAvailableCV;
        public TextView isActiveEventCV;

        public CustomViewHolderEvent(@NonNull View itemView) {
            super(itemView);
            eventIdCV = itemView.findViewById(R.id.eventIdCardViewEvent);
            eventNameCV = itemView.findViewById(R.id.eventNameCardViewEvent);
            categoryIDCV = itemView.findViewById(R.id.categoryIdCardViewEvent);
            ticketAvailableCV = itemView.findViewById(R.id.availableTicketCardViewEvent);
            isActiveEventCV = itemView.findViewById(R.id.isActiveCardViewEvent);
        }


    }
}
