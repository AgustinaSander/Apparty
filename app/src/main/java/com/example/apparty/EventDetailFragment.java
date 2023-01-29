package com.example.apparty;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Pair;
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
import java.util.stream.Collectors;

public class EventDetailFragment extends Fragment {

    private FragmentEventDetailBinding binding;
    private boolean showTickets = false;
    private GestorEvent gestorEvent = GestorEvent.getInstance();
    private Event event;
    private int idEvent;
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
        binding.getTicketBtn.setEnabled(false);
        idEvent = getArguments().getInt("idEvent");
        event = gestorEvent.getEventById(idEvent);

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
        //Si no hay tickets disponibles
        if(stock.stream().filter(s -> s.getAvailableQuantity() > 0).collect(Collectors.toList()).size() > 0 ){
            binding.ticketsAvailable.setVisibility(View.VISIBLE);
            binding.notTicketsAvailable.setVisibility(View.GONE);
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            for(int i=0; i < stock.size(); i++){
                //NO MOSTRAR SI NO HAY STOCK DE UN TIPO Y DESHABILITAR ADD CUANDO SE LLEGUE AL MAX
                Stock s = stock.get(i);
                View view =  inflater.inflate(R.layout.fragment_detail_event_item, null);
                TextView ticketName = view.findViewById(R.id.ticketName);
                ticketName.setText(s.getType());
                TextView ticketPrice = view.findViewById(R.id.ticketPrice);
                ticketPrice.setText("$ "+s.getPrice());

                quantityList.add(0);
                FloatingActionButton addBtn = view.findViewById(R.id.addBtn);
                int finalI = i;
                addBtn.setOnClickListener(e -> addQuantity(view, finalI, s.getAvailableQuantity()));

                FloatingActionButton minusBtn = view.findViewById(R.id.minusBtn);
                minusBtn.setOnClickListener(e -> minusQuantity(view, finalI));

                binding.ticketLayout.addView(view);
            };
        } else {
            binding.ticketsAvailable.setVisibility(View.GONE);
            binding.notTicketsAvailable.setVisibility(View.VISIBLE);
        }
    }

    private void addQuantity(View view, int idItem, int availableQuantity){
        int quantity = quantityList.get(idItem);
        FloatingActionButton addBtn = view.findViewById(R.id.addBtn);
        if(quantity+1 > availableQuantity){
            //No hay mas entradas de ese tipo
            new AlertDialog.Builder(getContext())
                    .setTitle("Entradas no disponibles")
                    .setMessage("La cantidad de entradas seleccionadas no se encuentra disponible.")
                    .setPositiveButton("Ok", null)
                    .create()
                    .show();
        } else{
            binding.getTicketBtn.setEnabled(true);
            addBtn.setEnabled(true);
            quantity++;
            quantityList.set(idItem,quantity);
            TextView ticketQuantity = view.findViewById(R.id.ticketQuantity);
            ticketQuantity.setText(String.valueOf(quantity));
        }

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

        List<Integer> tickets = quantityList.stream().filter(q -> q > 0).collect(Collectors.toList());
        binding.getTicketBtn.setEnabled(tickets.size() > 0);

        minusBtn.setEnabled(true);
        TextView ticketQuantity = view.findViewById(R.id.ticketQuantity);
        ticketQuantity.setText(String.valueOf(quantity));
    }

    //CUANDO NO HAY MAS ENTRADAS MOSTRAR UN MENSAJE DE ERROR

    private void setClickEvents() {
        binding.ticketBtn.setOnClickListener(e -> showTickets());
        binding.profileBtn.setOnClickListener(e -> Snackbar.make(getView(), "Funcionalidad de Perfil Organizador no implementada", Snackbar.LENGTH_SHORT).show());
        binding.getTicketBtn.setOnClickListener(e -> getSelectedTickets());
        binding.shareEventBtn.setOnClickListener(e -> shareEvent());
    }

    private void shareEvent() {
        Snackbar.make(getView(), "Funcionalidad de Compartir no implementada", Snackbar.LENGTH_SHORT).show();
    }

    private void getSelectedTickets() {
        Bundle bundle = new Bundle();
        bundle.putInt("idEvent", idEvent);
        ArrayList<Pair<Integer,Integer>> tickets = new ArrayList<>();
        for(int q=0; q < quantityList.size(); q++){
            if(quantityList.get(q) > 0){
                int idStock = event.getTickets().get(q).getId();
                tickets.add(Pair.create(idStock, quantityList.get(q)));
            }
        };
        bundle.putSerializable("tickets", tickets);

        //Ir a fragment de detalle de compra
        NavHostFragment.findNavController(EventDetailFragment.this).navigate(R.id.goToPurchaseFragment, bundle);
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