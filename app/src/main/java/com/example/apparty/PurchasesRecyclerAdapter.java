package com.example.apparty;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.EventItemBinding;
import com.example.apparty.databinding.PurchaseItemBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.gestores.GestorPurchase;
import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;

import java.util.List;

import lombok.NonNull;

public class PurchasesRecyclerAdapter extends RecyclerView.Adapter<PurchasesRecyclerAdapter.PurchasesViewHolder> {

    private List<Purchase> purchaseList;
    private OnNoteListener onNoteListener;
    private GestorPurchase gestorPurchase;

    public PurchasesRecyclerAdapter(List<Purchase> purchaseList, OnNoteListener onNoteListener) {
        this.purchaseList = purchaseList;
        this.onNoteListener = onNoteListener;
    }

    public class PurchasesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView card;
        TextView name;
        TextView date;
        TextView dressCode;

        OnNoteListener onNoteListener;

        public PurchasesViewHolder(PurchaseItemBinding binding, OnNoteListener onNoteListener){
            super(binding.getRoot());
            this.card = binding.cardPurchase;
            this.name = binding.textEventName;
            this.date = binding.textEventDate;
            this.dressCode = binding.textEventDressCode;

            this.card.setOnClickListener(this);
            this.onNoteListener = onNoteListener;
        }

        @Override
        public void onClick (View v){
            this.onNoteListener.onNoteClick(purchaseList.get(getBindingAdapterPosition()).getId());
        }

    }

    @NonNull
    @Override
    public PurchasesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        gestorPurchase = GestorPurchase.getInstance(parent.getContext());
        return new PurchasesViewHolder (PurchaseItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), this.onNoteListener);
    }


    @Override
    public void onBindViewHolder(PurchasesViewHolder purchasesHolder, int position) {
        Purchase purchase = this.purchaseList.get(position);
        purchasesHolder.name.setText(purchase.getEvent().getName());
        purchasesHolder.date.setText(purchase.getEvent().getDate().toString());
        purchasesHolder.dressCode.setText(purchase.getEvent().getDressCode().getDressCode());
    }

    @Override
    public int getItemCount() {
        return this.purchaseList.size();
    }

    public interface OnNoteListener{
        void onNoteClick(int idEvent);
    }
}
