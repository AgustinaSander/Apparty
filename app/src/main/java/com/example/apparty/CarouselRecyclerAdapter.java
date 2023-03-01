package com.example.apparty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.CarouselItemBinding;
import com.example.apparty.databinding.EventItemBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Event;
import com.example.apparty.model.Utils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import lombok.NonNull;

    public class CarouselRecyclerAdapter extends RecyclerView.Adapter<CarouselRecyclerAdapter.CarouselViewHolder> {

    private List<Event> eventList;
    private OnNoteListener onNoteListener;
    private GestorEvent gestorEvent;

    public CarouselRecyclerAdapter(List<Event> eventList, OnNoteListener onNoteListener) {
        this.eventList = eventList;
        this.onNoteListener = onNoteListener;
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card;
        ImageView image;
        TextView name;
        TextView date;

        OnNoteListener onNoteListener;

        public CarouselViewHolder(CarouselItemBinding binding, OnNoteListener onNoteListener){
            super(binding.getRoot());
            this.card = binding.cardResult;
            this.image = binding.imageResult;
            this.name = binding.textEventName;
            this.date = binding.textEventDate;

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
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        gestorEvent = GestorEvent.getInstance(parent.getContext());
        return new CarouselViewHolder (CarouselItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), this.onNoteListener);
    }


    @Override
    public void onBindViewHolder(CarouselViewHolder resultHolder, int position) {

        Event event = this.eventList.get(position);
        resultHolder.name.setText(event.getName());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String date = event.getDate().format(dateFormat);
        date = date.substring(0, 1).toUpperCase() + date.substring(1);
        resultHolder.date.setText(date);
        if(event.getImage() != null){
            resultHolder.image.setImageBitmap(Utils.getBitmapFromString(event.getImage()));
        } else {
            resultHolder.image.setImageResource(R.drawable.party1);
        }
    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int idEvent);
    }
}
