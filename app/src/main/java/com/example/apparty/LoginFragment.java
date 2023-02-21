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
import com.example.apparty.persistence.repos.AddressRepositoryImpl;


public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private AddressRepositoryImpl addressRepository;

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
            Intent intent = new Intent(getActivity(),MainActivity.class);
            startActivity(intent);

            /*new Thread( () -> {
                addressRepository = new AddressRepositoryImpl(this.getContext());
                Address address1 = new Address(4, "Breas 6475", "Argentina", "Santa Fe", "Santa Fe");
                //addressRepository.insertAddress(address1);
                System.out.println(addressRepository.getAddressById(4));
                Log.d("LOGINFRAG",addressRepository.getAddressById(4).toString());
            }).start();*/
        });

        return binding.getRoot();
    }


    public void onViewCreated (@NonNull View view, Bundle savedInstanceState){
        binding.registryBtn.setOnClickListener(v -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.registryFragment);
        });
    }

}