package com.example.apparty;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.apparty.databinding.FragmentRegisterEventBinding;
import com.example.apparty.gestores.GestorEvent;
import com.example.apparty.gestores.GestorTicket;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.Address;
import com.example.apparty.model.DressCode;
import com.example.apparty.model.Event;
import com.example.apparty.model.Ticket;
import com.example.apparty.model.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class FragmentRegisterEvent extends Fragment {
    private FragmentRegisterEventBinding binding;
    private TextView DateText;
    private TextView TimeText;
    private Date Date;
    private Spinner dresscodeSpinner;
    private ArrayAdapter adapter;
    private List<Ticket> ticketsList = new ArrayList<>();
    private User organizer;

    private GestorEvent gestorEvent;
    private GestorTicket gestorTicket;
    private GestorUser gestorUser;

    private SharedPreferences sharedPreferences;

    public FragmentRegisterEvent() {
    }

    public static FragmentRegisterEvent newInstance(String param1, String param2) {
        FragmentRegisterEvent fragment = new FragmentRegisterEvent();
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
        MaterialButton DateBtn = binding.DateBtn;
        DateText = binding.textViewDate;
        DateBtn.setOnClickListener(e -> showDatePickerDialog());

        MaterialButton TimeBtn = binding.TimeBtn;
        TimeText = binding.textViewTime;
        TimeBtn.setOnClickListener(e -> showTimePickerDialog());

        Button addTicket = binding.addTicketBtn;
        addTicket.setOnClickListener(e -> registerTicket());

        Button addEvent = binding.saveNewEventBtn;
        addEvent.setOnClickListener(e -> createEvent());
    }

    private void createEvent() {
        String eventName = String.valueOf(binding.editTextEventName);
        String address = String.valueOf(binding.editTextEventAddress);
        String localty = String.valueOf(binding.editTextEventLocalty);
        String province = String.valueOf(binding.editTextEventProvince);
        String country = String.valueOf(binding.editTextEventCountry);
        LocalDate date = LocalDate.parse(String.valueOf(binding.textViewDate));
        LocalTime time = LocalTime.parse(String.valueOf(binding.textViewTime));
        DressCode item = (DressCode) adapter.getItem(dresscodeSpinner.getSelectedItemPosition());


        String description = String.valueOf(binding.editTextEventDescription);

        if (eventName.length()>0 && address.length()>0 && localty.length()>0 && province.length()>0 && !ticketsList.isEmpty()
                && country.length()>0 && date!=null && time!=null && description.length()>0 && item!=null){

            Address location = new Address(address, country, province, localty);

            sharedPreferences = getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
            int idUser = sharedPreferences.getInt("idUser", 0);
            organizer = gestorUser.getUserById(idUser);

            List<Ticket> ticketsEventList = null;
            for(int i=0; i < ticketsList.size(); i++) {
                Ticket newTicket = gestorTicket.saveTicket(ticketsList.get(i));
                ticketsEventList.add(newTicket);
            }

            Event newEvent = new Event(eventName,location,ticketsEventList,date,time,item,organizer,description);
            gestorEvent.insertEvent(newEvent);
            Snackbar.make(getView(), "Evento creado correctamente!", Snackbar.LENGTH_SHORT).show();
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
                TimeText.setText(time);
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
        if(Date != null) datePickerBuilder.setSelection(Date.getTime());

        MaterialDatePicker materialDatePicker = datePickerBuilder.build();

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                DateText.setText(""+materialDatePicker.getHeaderText());
                Date dateSelected = new Date((Long) materialDatePicker.getSelection());
                Date = new Date(dateSelected.getTime() + (1000 * 60 * 60 * 24));

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