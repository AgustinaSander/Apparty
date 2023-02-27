package com.example.apparty;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apparty.databinding.EventItemBinding;
import com.example.apparty.databinding.PurchaseItemBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.gestores.GestorPurchase;
import com.example.apparty.model.Event;
import com.example.apparty.model.Purchase;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import lombok.NonNull;

public class PurchasesRecyclerAdapter extends RecyclerView.Adapter<PurchasesRecyclerAdapter.PurchasesViewHolder> {

    private List<Purchase> purchaseList;
    private boolean isPast;
    private GestorPurchase gestorPurchase;
    private GestorEvent gestorEvent;

    public PurchasesRecyclerAdapter(List<Purchase> purchaseList, boolean isPast) {
        this.purchaseList = purchaseList;
        this.isPast = isPast;
    }

    public class PurchasesViewHolder extends RecyclerView.ViewHolder{

        CardView card;
        TextView name;
        TextView date;
        FloatingActionButton infoBtn;
        boolean isShow = false;
        TableLayout purchaseTable;
        Button showEventBtn;
        Button qrBtn;
        LinearLayout btnContainer;
        View root;

        public PurchasesViewHolder(PurchaseItemBinding binding){
            super(binding.getRoot());
            this.root = binding.getRoot();
            this.card = binding.cardPurchase;
            this.name = binding.textEventName;
            this.date = binding.textEventDate;
            this.infoBtn = binding.infoBtn;
            this.showEventBtn = binding.showEventBtn;
            this.qrBtn = binding.qrBtn;
            infoBtn.setOnClickListener(e -> {
                binding.divider.setVisibility(isShow ? View.GONE : View.VISIBLE);
                binding.detailPurchaseInfo.setVisibility(isShow ? View.GONE : View.VISIBLE);
                binding.infoBtn.setImageResource(isShow ? R.drawable.ic_baseline_keyboard_arrow_down_24 : R.drawable.ic_baseline_keyboard_arrow_up_24);
                isShow = !isShow;
            });

            this.purchaseTable = binding.tableTickets;
        }
    }

    @NonNull
    @Override
    public PurchasesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        gestorPurchase = GestorPurchase.getInstance(parent.getContext());
        gestorEvent = GestorEvent.getInstance(parent.getContext());
        return new PurchasesViewHolder (PurchaseItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }


    @Override
    public void onBindViewHolder(PurchasesViewHolder purchasesHolder, int position) {
        Purchase purchase = this.purchaseList.get(position);
        purchasesHolder.name.setText(purchase.getEvent().getName());
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es","ES"));
        String date = purchase.getEvent().getDate().format(dateFormat);
        date = date.substring(0, 1).toUpperCase() + date.substring(1);
        purchasesHolder.date.setText(date);

        purchasesHolder.qrBtn.setEnabled(!isPast);

        purchasesHolder.showEventBtn.setOnClickListener(e -> {
            Bundle bundle = new Bundle();
            bundle.putInt("idEvent", purchase.getEvent().getId());
            NavHostFragment.findNavController(FragmentManager.findFragment(purchasesHolder.root)).navigate(R.id.action_purchasedEventsFragment_to_eventDetailFragment, bundle);
        });

        purchasesHolder.qrBtn.setOnClickListener(e -> {
            //Generate qr
            try{
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(purchase.getQr(), BarcodeFormat.QR_CODE, 750, 750);

                PdfDocument pdfDocument = new PdfDocument();
                int pageHeight = 1120;
                int pagewidth = 792;
                Bitmap bmp, scaledbmp;

                bmp = BitmapFactory.decodeResource(purchasesHolder.itemView.getResources(), R.drawable.logoapparty);
                scaledbmp = Bitmap.createScaledBitmap(bmp, 100, 100, false);

                Paint paint = new Paint();
                Paint title = new Paint();

                PdfDocument.PageInfo mypageInfo = new PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create();

                PdfDocument.Page myPage = pdfDocument.startPage(mypageInfo);

                Canvas canvas = myPage.getCanvas();
                canvas.drawBitmap(scaledbmp, 56, 40, paint);

                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                title.setTextSize(18);
                title.setColor(ContextCompat.getColor(purchasesHolder.root.getContext(), R.color.dark_blue));

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
                File file = new File(path, purchase.getId()+"-EntradaQRApparty.pdf");

                try {
                    pdfDocument.writeTo(new FileOutputStream(file));

                    Toast.makeText(purchasesHolder.root.getContext(), "PDF descargado correctamente.", Toast.LENGTH_SHORT).show();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
                pdfDocument.close();
            } catch (Exception ex){
                ex.printStackTrace();
            }

        });

        purchase.getPurchases().stream().forEach(t -> {
            Ticket ticket= gestorEvent.getTicketByIdByEvent(t.first, purchase.getEvent().getId());

            TableRow tableRow = new TableRow(purchasesHolder.root.getContext());
            tableRow.setPadding(0,10,0,10);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.setGravity(Gravity.CENTER);

            TextView title = new TextView(purchasesHolder.root.getContext());
            title.setTextSize(16);
            title.setWidth(100);
            title.setText(ticket.getType());

            TextView quantity = new TextView(purchasesHolder.root.getContext());
            quantity.setText(String.valueOf(t.second));
            quantity.setTextSize(16);
            quantity.setGravity(Gravity.CENTER);

            TextView price = new TextView(purchasesHolder.root.getContext());
            price.setText("$ "+ticket.getPrice());
            price.setTextSize(16);
            price.setGravity(Gravity.CENTER);

            TextView totalText = new TextView(purchasesHolder.root.getContext());
            double totalPrice = t.second * ticket.getPrice();
            totalText.setText("$ "+totalPrice);
            totalText.setTextSize(16);
            totalText.setGravity(Gravity.CENTER);

            tableRow.addView(title);
            tableRow.addView(quantity);
            tableRow.addView(price);
            tableRow.addView(totalText);
            purchasesHolder.purchaseTable.addView(tableRow);
        });
    }

    @Override
    public int getItemCount() {
        return this.purchaseList.size();
    }
}
