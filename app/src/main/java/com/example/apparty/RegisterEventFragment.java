package com.example.apparty;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.apparty.databinding.FragmentRegisterEventBinding;
import com.example.apparty.gestores.GestorAddress;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.gestores.GestorTicket;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.User;
import com.example.apparty.model.Utils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class RegisterEventFragment extends Fragment {
    private FragmentRegisterEventBinding binding;
    private TextView dateText;
    private TextView timeText;
    private Date date;
    private Spinner dresscodeSpinner;
    private ArrayAdapter adapter;
    private List<Ticket> ticketsList = new ArrayList<>();
    private User organizer;
    private byte[] stringImage = null;

    private GestorEvent gestorEvent;
    private GestorTicket gestorTicket;
    private GestorUser gestorUser;
    private GestorAddress gestorAddress;

    private SharedPreferences sharedPreferences;

    public RegisterEventFragment() {
    }

    public static RegisterEventFragment newInstance(String param1, String param2) {
        RegisterEventFragment fragment = new RegisterEventFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterEventBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        gestorEvent = GestorEvent.getInstance(this.getContext());
        gestorTicket = GestorTicket.getInstance(this.getContext());
        gestorUser = GestorUser.getInstance(this.getContext());
        gestorAddress = GestorAddress.getInstance(this.getContext());
        setClickEvents();
        setDresscodeOptions();
    }

    private void setDresscodeOptions() {
        List<DressCode> dresscodes = gestorEvent.getDressCodeList();
        ArrayList<String> dresscodeOptions = new ArrayList<String>();
        for(DressCode d : dresscodes){
            dresscodeOptions.add(d.getDressCode());
        }
        dresscodeSpinner = binding.spinnerDresscode;
        adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, dresscodeOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dresscodeSpinner.setAdapter(adapter);
    }

    private void setClickEvents() {
        dateText = binding.textViewDate;
        binding.DateBtn.setOnClickListener(e -> showDatePickerDialog());

        timeText = binding.textViewTime;
        binding.TimeBtn.setOnClickListener(e -> showTimePickerDialog());

        binding.addTicketBtn.setOnClickListener(e -> registerTicket());

        binding.saveNewEventBtn.setOnClickListener(e -> createEvent());

        //Subir foto
        binding.addImageBtn.setOnClickListener(e -> {
            uploadImage();
        });
    }

    private void uploadImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        uploadPictureLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> uploadPictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    binding.uploadImage.setImageURI(selectedImageUri);
                    ImageDecoder.Source source = ImageDecoder.createSource(getContext().getContentResolver(), selectedImageUri);
                    try {
                        Bitmap bitmap = ImageDecoder.decodeBitmap(source);
                        Bitmap resizedBitmap = Utils.getResizedBitmap(bitmap, 480, 640);
                        stringImage = Utils.getStringFromBitmap(resizedBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });



    private void createEvent() {
        String eventName = String.valueOf(binding.editTextEventName.getText());
        String address = String.valueOf(binding.editTextEventAddress.getText());
        String localty = String.valueOf(binding.editTextEventLocalty.getText());
        String province = String.valueOf(binding.editTextEventProvince.getText());
        String country = String.valueOf(binding.editTextEventCountry.getText());
        DressCode item = gestorEvent.getDressCodeList().get(dresscodeSpinner.getSelectedItemPosition());
        String description = String.valueOf(binding.editTextEventDescription.getText());
        String stringTime = String.valueOf(binding.textViewTime.getText());

        if (eventName.length()>0 && address.length()>0 && localty.length()>0 && province.length()>0 && country.length()>0 && date!=null && stringTime!=null && description.length()>0 && item!=null){

            binding.incompleteFields.setVisibility(View.GONE);

            LocalDate dateEvent = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm a");
            LocalTime time = LocalTime.parse(stringTime, formatterTime);

            Address location = new Address(address, country, province, localty);
            location = gestorAddress.saveAddress(location);

            sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            int idUser = sharedPreferences.getInt("idUser", 0);
            organizer = gestorUser.getUserById(idUser);

            if(!ticketsList.isEmpty()){
                binding.noTickets.setVisibility(View.GONE);

                for(Ticket t : ticketsList){
                    t = gestorTicket.saveTicket(t);
                }

                Event newEvent = new Event(eventName, location, ticketsList, dateEvent, time, item, organizer, description, stringImage);
                gestorEvent.insertEvent(newEvent);

                Snackbar.make(getView(), "Evento creado correctamente!", Snackbar.LENGTH_SHORT).show();
            } else {
                binding.noTickets.setVisibility(View.VISIBLE);
            }
        } else{
            binding.incompleteFields.setVisibility(View.VISIBLE);
        }
    }

    private void registerTicket() {
        String name = String.valueOf(binding.editTextTicketName.getText());
        double price = Double.parseDouble(String.valueOf(binding.editTextNumberTicketPrice.getText()));
        int quantity = Integer.parseInt(String.valueOf(binding.editTextNumberTicketQuantity.getText()));

        if(name.length() > 0 && price >= 0 && quantity > 0){
            Ticket newTicket = new Ticket(name, quantity, quantity, price);
            ticketsList.add(newTicket);
            Snackbar.make(getView(), "Ticket cargado!", Snackbar.LENGTH_SHORT).show();

            binding.editTextTicketName.setText("");
            binding.editTextNumberTicketPrice.setText("");
            binding.editTextNumberTicketQuantity.setText("");
        }
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Calendar c= Calendar.getInstance();
                c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                c.set(Calendar.MINUTE, minute);
                c.setTimeZone(TimeZone.getDefault());
                SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                String time = format.format(c.getTime());
                timeText.setText(time);
            }
        }, hours, minutes, false);
        timePickerDialog.show();
    }

    private void showDatePickerDialog() {
        CalendarConstraints.Builder constraintBuilder = setDatePickerConstraints();
        constraintBuilder.setValidator(DateValidatorPointForward.now());
        MaterialDatePicker.Builder<Long> datePickerBuilder = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecciona la fecha del evento")
                .setCalendarConstraints(constraintBuilder.build());
        if(date != null) datePickerBuilder.setSelection(date.getTime());

        MaterialDatePicker materialDatePicker = datePickerBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                dateText.setText(""+materialDatePicker.getHeaderText());
                Date dateSelected = new Date((Long) materialDatePicker.getSelection());
                date = new Date(dateSelected.getTime() + (1000 * 60 * 60 * 24));
            }
        });
        materialDatePicker.show(getActivity().getSupportFragmentManager(), "TAG");
    }

    private CalendarConstraints.Builder setDatePickerConstraints() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
        long month = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setStart(month);
        return constraintBuilder;
    }

}