package com.example.apparty;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.example.apparty.databinding.FragmentCompletePurchaseDialogBinding;
import com.example.apparty.gestores.GestorPurchase;
import com.example.apparty.model.Purchase;
import com.example.apparty.model.PurchaseInfoQR;
import com.example.apparty.model.Utils;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CompletePurchaseDialogFragment extends DialogFragment {

    private FragmentCompletePurchaseDialogBinding binding;
    private Purchase purchase;
    private String purchaseJson;
    private Bitmap bitmap;
    private PurchaseInfoQR purchaseInfo;
    private String purchaseQR;
    private GestorPurchase gestorPurchase;

    private static final int PERMISSION_REQUEST_CODE = 200;

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
        gestorPurchase = GestorPurchase.getInstance(getContext());

        purchaseInfo = new PurchaseInfoQR(purchase.getId(), purchase.getEvent().getId(),purchase.getUser().getName()+" "+purchase.getUser().getSurname(), purchase.getPurchases());
        purchaseQR = Utils.getGsonParser().toJson(purchaseInfo);

        purchase.setQr(purchaseQR);
        gestorPurchase.updatePurchase(purchase);

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
            bitmap = barcodeEncoder.encodeBitmap(purchaseQR, BarcodeFormat.QR_CODE, 750, 750);
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
        binding.downloadBtn.setOnClickListener(e -> {
            downloadQR();
        });
    }

    private void downloadQR() {
        PdfDocument pdfDocument = new PdfDocument();
        int pageHeight = 1120;
        int pagewidth = 792;
        Bitmap bmp, scaledbmp;

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logoapparty);
        scaledbmp = Bitmap.createScaledBitmap(bmp, 100, 100, false);

        Paint paint = new Paint();
        Paint title = new Paint();

        PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

        PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

        Canvas canvas = myPage.getCanvas();
        canvas.drawBitmap(scaledbmp, 56, 40, paint);

        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        title.setTextSize(18);
        title.setColor(ContextCompat.getColor(getContext(), R.color.dark_blue));

        canvas.drawText("Presenta este codigo QR el dia del evento.", 209, 100, title);
        canvas.drawText("Apparty!", 209, 80, title);

        canvas.drawText("Evento: "+purchase.getEvent().getName(), 200, 200, title);
        canvas.drawText("Dia: "+purchase.getEvent().getDate(), 200, 220, title);
        canvas.drawText("Hora: "+purchase.getEvent().getTime(), 200, 240, title);
        canvas.drawText("Ubicacion: "+purchase.getEvent().getAddress().getAddress()+", "+purchase.getEvent().getAddress().getLocalty()+", "+purchase.getEvent().getAddress().getProvince()+", "+purchase.getEvent().getAddress().getCountry(), 200, 260, title);
        Bitmap scaledQR = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
        canvas.drawBitmap(scaledQR, 200, 300, paint);

        pdfDocument.finishPage(myPage);

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        File file = new File(path, purchaseInfo.getId()+"-EntradaQRApparty.pdf");

        if (!checkPermission()) {
            requestPermission();
        } else {
            try {
                pdfDocument.writeTo(new FileOutputStream(file));

                Toast.makeText(getContext(), "PDF descargado correctamente.", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();
        }

    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
}