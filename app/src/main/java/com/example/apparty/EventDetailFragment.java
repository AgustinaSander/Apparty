package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentEventDetailBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Address;
import com.example.apparty.model.Event;
import com.example.apparty.model.Stock;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class EventDetailFragment extends Fragment {

    private FragmentEventDetailBinding binding;
    private boolean showTickets = false;
    private GestorEvent gestorEvent = GestorEvent.getInstance();
    private Event event;

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
        //SETEAR ENTRADAS
        List<Stock> stock = event.getTickets();
        stock.stream().forEach(s -> {

        });
    }

    //CUANDO NO HAY MAS ENTRADAS MOSTRAR UN MENSAJE DE ERROR

    private void setClickEvents() {
        binding.ticketBtn.setOnClickListener(e -> showTickets());
        binding.profileBtn.setOnClickListener(e -> Snackbar.make(getView(), "Funcionalidad de Perfil Organizador no implementada", Snackbar.LENGTH_SHORT).show());
        binding.getTicketBtn.setOnClickListener(e -> getSelectedTickets());
    }

    private void getSelectedTickets() {
        Snackbar.make(getView(), "Funcionalidad de Adquirir Entradas no implementada", Snackbar.LENGTH_SHORT).show();
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