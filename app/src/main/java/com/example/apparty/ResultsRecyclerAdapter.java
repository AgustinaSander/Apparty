package com.example.apparty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.EventItemBinding;
import com.example.apparty.gestor.GestorEvent;
import com.example.apparty.model.Event;

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

        OnNoteListener onNoteListener;

        public ResultsViewHolder(EventItemBinding binding, OnNoteListener onNoteListener){
            super(binding.getRoot());
            this.card = binding.cardResult;
            this.image = binding.imageResult;

            this.card.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick (View v){
            this.onNoteListener.onNoteClick(eventList.get(getAdapterPosition()).getId());
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
        resultHolder.image.setImageResource(R.drawable.party1);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface OnNoteListener{
        void onNoteClick(int idEvent);
    }
}
