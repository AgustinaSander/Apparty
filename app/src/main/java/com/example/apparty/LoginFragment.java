package com.example.apparty;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private GestorUser gestorUser;

    public LoginFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);

        binding.loginBtn.setOnClickListener(view -> {
            validateUser();
        });

        return binding.getRoot();
    }


    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        binding.registryBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registryFragment);
        });

        gestorUser = GestorUser.getInstance(this.getContext());
    }


    private void validateUser() {
        String email = String.valueOf(binding.emailInput.getText());
        String password = String.valueOf(binding.passwordInput.getText());

        User user = gestorUser.getUserByEmailByPassword(email, password);
        if(user == null){
            binding.wrongCredentials.setVisibility(View.VISIBLE);

        } else {
            binding.wrongCredentials.setVisibility(View.INVISIBLE);
            ((LoginActivity) getActivity()).setUserLogged(user.getId());
        }
    }
}