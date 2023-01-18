package com.example.apparty;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apparty.databinding.FragmentEventDetailBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Address;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class EventDetailFragment extends Fragment {

    private FragmentEventDetailBinding binding;
    private boolean showTickets = false;
    private GestorEvent gestorEvent = GestorEvent.getInstance();
    private Event event;
    private List<Integer> quantityList = new ArrayList<>();

    public EventDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEventDetailBinding.inflate(inflater, container, false);

        //int idEvent = getArguments().getInt("idEvent");
        event = gestorEvent.getEventById(1);

        return binding.getRoot();
    }

    private void setScrollViewHeight() {
        //TODO
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        setValues();
        setScrollViewHeight();
        setClickEvents();
    }

    private void setValues() {
        binding.eventTitle.setText(event.getName());
        binding.eventComment.setText(event.getComments());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String date = event.getDate().format(dateFormat);
        date = date.substring(0, 1).toUpperCase() + date.substring(1);
        binding.eventDate.setText(date);
        binding.eventTime.setText(event.getTime().toString() + " hs");
        binding.eventDresscode.setText("Dresscode " + event.getDressCode().getDressCode());
        Address address = event.getAddress();
        binding.eventStreet.setText(address.getAddress());
        binding.eventAddress.setText(address.getLocalty()+", "+address.getProvince()+", "+address.getCountry());
        binding.eventOrganizer.setText(event.getOrganizer().getName() + " " + event.getOrganizer().getSurname());

        setTicketsInfo();
    }

    private void setTicketsInfo() {
        List<Stock> stock = event.getTickets();

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        for(int i=0; i < stock.size(); i++){
            Stock s = stock.get(i);
            View view =  inflater.inflate(R.layout.fragment_detail_event_item, null);
            TextView ticketName = view.findViewById(R.id.ticketName);
            ticketName.setText(s.getType());
            TextView ticketPrice = view.findViewById(R.id.ticketPrice);
            ticketPrice.setText("$ "+s.getPrice());

            quantityList.add(0);
            FloatingActionButton addBtn = view.findViewById(R.id.addBtn);
            int finalI = i;
            addBtn.setOnClickListener(e -> addQuantity(view, finalI));

            FloatingActionButton minusBtn = view.findViewById(R.id.minusBtn);
            minusBtn.setOnClickListener(e -> minusQuantity(view, finalI));

            binding.ticketLayout.addView(view);
        };
    }

    private void addQuantity(View view, int idItem){
        int quantity = quantityList.get(idItem);
        quantity++;
        quantityList.set(idItem,quantity);
        FloatingActionButton addBtn = view.findViewById(R.id.addBtn);
        addBtn.setEnabled(true);
        TextView ticketQuantity = view.findViewById(R.id.ticketQuantity);
        ticketQuantity.setText(String.valueOf(quantity));
    }

    private void minusQuantity(View view, int idItem){
        int quantity = quantityList.get(idItem);
        FloatingActionButton minusBtn = view.findViewById(R.id.minusBtn);

        if(quantity > 0){
            quantity--;
            quantityList.set(idItem,quantity);
        } else {
            quantity = 0;
            quantityList.set(idItem, quantity);
            minusBtn.setEnabled(false);
        }

        minusBtn.setEnabled(true);
        TextView ticketQuantity = view.findViewById(R.id.ticketQuantity);
        ticketQuantity.setText(String.valueOf(quantity));
    }

    //CUANDO NO HAY MAS ENTRADAS MOSTRAR UN MENSAJE DE ERROR

    private void setClickEvents() {
        binding.ticketBtn.setOnClickListener(e -> showTickets());
        binding.profileBtn.setOnClickListener(e -> Snackbar.make(getView(), "Funcionalidad de Perfil Organizador no implementada", Snackbar.LENGTH_SHORT).show());
        binding.getTicketBtn.setOnClickListener(e -> getSelectedTickets());
    }

    private void getSelectedTickets() {
        //Mostrar dialog con detalle de compra
        new PurchaseDetailDialogFragment().show(getChildFragmentManager(), null);
        //Snackbar.make(getView(), "Funcionalidad de Adquirir Entradas no implementada", Snackbar.LENGTH_SHORT).show();
    }

    private void showTickets() {
        showTickets = !showTickets;
        if(showTickets){
            binding.ticketDivider.setVisibility(View.VISIBLE);
            binding.ticketContainer.setVisibility(View.VISIBLE);
            binding.ticketBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_up_24), null);

        } else {
            binding.ticketDivider.setVisibility(View.GONE);
            binding.ticketContainer.setVisibility(View.GONE);
            binding.ticketBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.ic_baseline_keyboard_arrow_down_24), null);
        }
    }
}