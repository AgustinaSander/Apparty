package com.example.apparty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.apparty.databinding.FragmentRegistryBinding;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.User;


public class RegistryFragment extends Fragment {

    private FragmentRegistryBinding binding;
    private GestorUser gestorUser;

    public RegistryFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        gestorUser = GestorUser.getInstance(this.getContext());

        binding.loginBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(RegistryFragment.this).navigate(R.id.loginFragment);
        });

        binding.buttonEnter.setOnClickListener(v -> {
            createUser();
        });
    }

    private void createUser() {
        String name = String.valueOf(binding.editTextPersonName);
        String surname = String.valueOf(binding.editTextPersonSurname);
        String dni = String.valueOf(binding.editTextDNI);
        String email = String.valueOf(binding.emailInput);
        String password = String.valueOf(binding.passwordInput);

        boolean userWithSameEmailExists = gestorUser.userWithEmailExists(email);
        if(userWithSameEmailExists){
            binding.wrongCredentials.setVisibility(View.VISIBLE);
        } else {
            binding.wrongCredentials.setVisibility(View.INVISIBLE);

            //ANTES VER QUE ME HAYAN LLENADO TODOS LOS CAMPOS!

            User user = new User(name, surname, dni, email, password);
            
        }

    }
}