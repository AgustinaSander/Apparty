package com.example.apparty;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.apparty.databinding.FragmentLoginBinding;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.User;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    //private GestorUser gestorUser = GestorUser.getInstance(this.getContext());

    public LoginFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.loginBtn.setOnClickListener(view -> {
            if (validateUser()) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    private boolean validateUser() {
        String email = (String) binding.userEmail.getText();
        String password = (String) binding.userPassword.getText();

        /* DESCOMENTAR CUANDO ESTE HECHO EL METODO DEL GESTOR

        User user = gestorUser.getUserByEmailByPassword(email, password);
        if(user == null){
            binding.wrongCredentials.setVisibility(View.VISIBLE);
            return false;
        } */

        binding.wrongCredentials.setVisibility(View.INVISIBLE);
        //HAY QUE METER EL ID USER EN PREFERENCES PARA TENERLO EN TODA LA APP

        return true;
    }


    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        binding.registryBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registryFragment);
        });
    }

}