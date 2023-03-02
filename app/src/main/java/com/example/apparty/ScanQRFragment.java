package com.example.apparty;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apparty.databinding.FragmentScanQRBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.gestores.GestorPurchase;
import com.example.apparty.gestores.GestorTicket;
import com.example.apparty.model.Event;
import com.example.apparty.model.PurchaseInfoQR;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.Utils;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ScanQRFragment extends Fragment {

    private FragmentScanQRBinding binding;
    private Event event;
    private GestorEvent gestorEvent;
    private GestorTicket gestorTicket;
    private GestorPurchase gestorPurchase;

    public ScanQRFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentScanQRBinding.inflate(inflater, container, false);
        int idEvent = getArguments().getInt("idEvent");
        gestorEvent = GestorEvent.getInstance(getContext());
        gestorTicket = GestorTicket.getInstance(getContext());
        event = gestorEvent.getEventById(idEvent);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        //escanear QR
        gestorPurchase = GestorPurchase.getInstance(getContext());
        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setOrientationLocked(false);
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("");
        options.setBarcodeImageEnabled(true);

        binding.scanQR.setOnClickListener(e -> {
            barcodeLauncher.launch(options);
        });

        setValues();

    }

    private void setValues() {
        binding.eventTitle.setText(event.getName());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String date = event.getDate().format(dateFormat);
        date = date.substring(0, 1).toUpperCase() + date.substring(1);
        binding.eventDate.setText(date);
        binding.eventTime.setText(event.getTime().toString() + " hs");
        binding.eventDresscode.setText("Dresscode " + event.getDressCode().getDressCode());
    }

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if(result.getContents() == null) {
        } else {
            String scanned = result.getContents();
            PurchaseInfoQR purchaseInfoQR = new PurchaseInfoQR();
            purchaseInfoQR.setId(-1);
            try{
                purchaseInfoQR = Utils.getGsonParser().fromJson(scanned, PurchaseInfoQR.class);
            } catch (Exception e){
                binding.ticketInfo.setVisibility(View.VISIBLE);
                binding.invalidQR.setVisibility(View.VISIBLE);
                binding.headerTickets.setVisibility(View.GONE);
                binding.ticketContainer.removeAllViews();
            }

            if(purchaseInfoQR.getIdEvent() == event.getId()){
                completeScannedInfo(purchaseInfoQR);
            } else {
                binding.ticketInfo.setVisibility(View.VISIBLE);
                binding.invalidQR.setVisibility(View.VISIBLE);
                binding.headerTickets.setVisibility(View.GONE);
                binding.ticketContainer.removeAllViews();
            }

        }
    });

    private void completeScannedInfo(PurchaseInfoQR purchaseInfoQR){
        boolean isScanned = gestorPurchase.isPurchaseScannedById(purchaseInfoQR.getId());
        binding.alreadyScanned.setVisibility(isScanned ? View.VISIBLE : View.GONE);
        binding.ticketInfo.setVisibility(View.VISIBLE);
        binding.headerTickets.setVisibility(View.VISIBLE);
        binding.invalidQR.setVisibility(View.GONE);

        List<Pair<Integer, Integer>> tickets = purchaseInfoQR.getPurchases();
        binding.ticketContainer.removeAllViews();
        tickets.stream().forEach(t -> {
            Ticket ticket = gestorTicket.getTicketById(t.first);

            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0,0,20,0);
            TextView ticketName = new TextView(getContext());
            ticketName.setText(ticket.getType());
            ticketName.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView quantity = new TextView(getContext());
            quantity.setText(String.valueOf(t.second));
            quantity.setGravity(Gravity.END);
            quantity.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(ticketName);
            linearLayout.addView(quantity);

            binding.ticketContainer.addView(linearLayout);
        });

        gestorPurchase.updateQrToScanned(purchaseInfoQR.getId());

    }

}