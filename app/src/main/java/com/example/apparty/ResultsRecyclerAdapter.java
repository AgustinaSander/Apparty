package com.example.apparty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.EventItemBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;

import java.util.Date;
import java.util.List;

import lombok.NonNull;

public class ResultsRecyclerAdapter extends RecyclerView.Adapter<ResultsRecyclerAdapter.ResultsViewHolder> {

    private List<Event> eventList;
    private OnNoteListener onNoteListener;
    private GestorEvent gestorEvent;

    public ResultsRecyclerAdapter(List<Event> eventList, OnNoteListener onNoteListener) {
        this.eventList = eventList;
        this.onNoteListener = onNoteListener;
    }

    public class ResultsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card;
        ImageView image;
        TextView name;
        TextView date;
        TextView dressCode;

        OnNoteListener onNoteListener;

        public ResultsViewHolder(EventItemBinding binding, OnNoteListener onNoteListener){
            super(binding.getRoot());
            this.card = binding.cardResult;
            this.image = binding.imageResult;
            this.name = binding.textEventName;
            this.date = binding.textEventDate;
            this.dressCode = binding.textEventDressCode;

            this.card.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick (View v){
            this.onNoteListener.onNoteClick(eventList.get(getBindingAdapterPosition()).getId());
        }

    }

    @NonNull
    @Override
    public ResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        gestorEvent = GestorEvent.getInstance();
        return new ResultsViewHolder (EventItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), this.onNoteListener);
    }

    @Override
    public void onBindViewHolder(ResultsViewHolder resultHolder, int position) {
        Event event = this.eventList.get(position);
        resultHolder.name.setText(event.getName());
        resultHolder.date.setText(event.getDate().toString());
        resultHolder.dressCode.setText(event.getDressCode().getDressCode());
        resultHolder.image.setImageResource(R.drawable.party1);

    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int idEvent);
    }
}
