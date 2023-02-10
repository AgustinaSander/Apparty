package com.example.apparty;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.apparty.databinding.FragmentDetailPurchaseBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.model.Purchase;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.Utils;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class DetailPurchaseFragment extends Fragment {

    private FragmentDetailPurchaseBinding binding;
    private GestorEvent gestorEvent = GestorEvent.getInstance();
    private Purchase purchase;

    public DetailPurchaseFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailPurchaseBinding.inflate(inflater, container, false);

        String ticketJson = getArguments().getString("ticket");
        purchase = Utils.getGsonParser().fromJson(ticketJson, Purchase.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        setValues();
        setClickEvents();
    }

    private void setClickEvents() {

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.detailBtn:
                    binding.paymentLayout.setVisibility(View.GONE);
                    binding.detailLayout.setVisibility(View.VISIBLE);
                    return true;

                case R.id.paymentBtn:
                    binding.detailLayout.setVisibility(View.GONE);
                    binding.paymentLayout.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        });
        
        binding.payBtn.setOnClickListener(e -> {doPayment();});
    }

    private void doPayment() {
        new CompletePurchaseDialogFragment().show(getChildFragmentManager(), null);
    }

    private void setValues() {
        List<Pair<Integer, Integer>> ticketsList = purchase.getPurchases();
        double servicePrice = 3000;
        AtomicReference<Double> total = new AtomicReference<>((double) servicePrice);
        ticketsList.stream().forEach(t -> {
            Ticket ticket= gestorEvent.getTicketByIdByEvent(t.first, purchase.getEvent().getId());
            total.updateAndGet(v -> new Double((double) (v + ticket.getPrice() * t.second)));
            //Create row
            TableRow tableRow = new TableRow(getActivity());
            tableRow.setPadding(0,10,0,10);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);

            TextView title = new TextView(getActivity());
            title.setTextSize(16);
            title.setWidth(100);
            title.setText(ticket.getType());

            TextView quantity = new TextView(getActivity());
            quantity.setText(String.valueOf(t.second));
            quantity.setTextSize(16);
            quantity.setGravity(Gravity.CENTER);

            TextView price = new TextView(getActivity());
            price.setText("$ "+ticket.getPrice());
            price.setTextSize(16);
            price.setGravity(Gravity.CENTER);

            TextView totalText = new TextView(getActivity());
            double totalPrice = t.second * ticket.getPrice();
            totalText.setText("$ "+totalPrice);
            totalText.setTextSize(16);
            totalText.setGravity(Gravity.CENTER);

            tableRow.addView(title);
            tableRow.addView(quantity);
            tableRow.addView(price);
            tableRow.addView(totalText);
            binding.tableTickets.addView(tableRow);
        });

        binding.servicePrice.setText("$ "+servicePrice);
        binding.totalPrice.setText("$ "+total);
    }
}