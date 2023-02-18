package com.example.apparty;

import static android.content.Context.WINDOW_SERVICE;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.apparty.databinding.FragmentCompletePurchaseDialogBinding;
import com.example.apparty.model.Purchase;
import com.example.apparty.model.PurchaseInfoQR;
import com.example.apparty.model.Utils;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CompletePurchaseDialogFragment extends DialogFragment {

    private FragmentCompletePurchaseDialogBinding binding;
    private Purchase purchase;
    private String purchaseJson;

    private PurchaseInfoQR purchaseInfo;
    private String purchaseQR;

    public CompletePurchaseDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletePurchaseDialogBinding.inflate(inflater, container, false);
        purchaseJson = getArguments().getString("purchase");
        purchase = Utils.getGsonParser().fromJson(purchaseJson, Purchase.class);
        purchaseInfo = new PurchaseInfoQR(purchase.getId(), purchase.getEvent().getId(),"PONER NOMBRE USER", purchase.getPurchases());
        purchaseQR = Utils.getGsonParser().toJson(purchaseInfo);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        generateQR();
        setClickEvents();
    }

    private void generateQR() {
        try{
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap(purchaseQR, BarcodeFormat.QR_CODE, 750, 750);
            binding.qrImage.setImageBitmap(bitmap);
            purchase.setQr(String.valueOf(purchaseQR));

            Bundle bundle = new Bundle();
            bundle.putString("purchase", Utils.getGsonParser().toJson(purchase));
            getParentFragmentManager().setFragmentResult("purchaseCompleted", bundle);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setClickEvents() {
        binding.downloadBtn.setOnClickListener(e -> downloadQR());
    }

    private void downloadQR() {
        Snackbar.make(getView(), "Funcionalidad de Descargar no implementada", Snackbar.LENGTH_SHORT).show();
    }
}