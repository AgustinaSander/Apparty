package com.example.apparty;

import static android.content.Context.MODE_PRIVATE;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apparty.databinding.FragmentDetailPurchaseBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.gestores.GestorPurchase;
import com.example.apparty.gestores.GestorTicket;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.Purchase;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.User;
import com.example.apparty.model.Utils;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class DetailPurchaseFragment extends Fragment {

    private FragmentDetailPurchaseBinding binding;
    private GestorPurchase gestorPurchase;
    private GestorTicket gestorTicket;
    private GestorUser gestorUser;
    private Purchase purchase;

    public DetailPurchaseFragment(){};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getChildFragmentManager().setFragmentResultListener("purchaseCompleted", this, (requestKey, bundle) -> {
            String result = bundle.getString("purchase");
            purchase = Utils.getGsonParser().fromJson(result, Purchase.class);
        });
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
        gestorUser = GestorUser.getInstance(this.getContext());
        gestorPurchase = GestorPurchase.getInstance(this.getContext());
        gestorTicket = GestorTicket.getInstance(this.getContext());
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
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("loginInfo",MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", 0);

        User user = gestorUser.getUserById(idUser);
        purchase.setUser(user);

        purchase = gestorPurchase.savePurchase(purchase);

        Bundle bundle = new Bundle();
        bundle.putString("purchase", Utils.getGsonParser().toJson(purchase));
        CompletePurchaseDialogFragment completePurchaseDialogFragment = new CompletePurchaseDialogFragment();
        completePurchaseDialogFragment.setArguments(bundle);
        completePurchaseDialogFragment.show(getChildFragmentManager(), null);
    }

    private void setValues() {
        setEventValues();
        setTicketPrices();
    }

    private void setTicketPrices() {
        List<Pair<Integer, Integer>> ticketsList = purchase.getPurchases();
        double servicePrice = 3000;
        AtomicReference<Double> total = new AtomicReference<>((double) servicePrice);
        ticketsList.stream().forEach(t -> {
            Ticket ticket= gestorTicket.getTicketById(t.first);
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
        purchase.setPrice(total.get());
    }

    private void setEventValues() {
        binding.textEventName.setText(purchase.getEvent().getName());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String date = purchase.getEvent().getDate().format(dateFormat);
        date = date.substring(0, 1).toUpperCase() + date.substring(1);
        binding.textEventDate.setText(date);
        binding.textEventDressCode.setText("Dresscode "+purchase.getEvent().getDressCode().getDressCode());
        if(purchase.getEvent().getImage() != null){
            binding.imageResult.setImageBitmap(Utils.getBitmapFromString(purchase.getEvent().getImage()));
        }
    }
}