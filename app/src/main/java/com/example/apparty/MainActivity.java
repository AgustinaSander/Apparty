package com.example.apparty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.apparty.databinding.ActivityMainBinding;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.User;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.EventDAO;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity{

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private GestorUser gestorUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        SharedPreferences sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", 0);
        gestorUser = GestorUser.getInstance(getApplicationContext());
        user = gestorUser.getUserById(idUser);

        //EventDAO dao = RoomDB.getInstance(this).eventDAO();

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.topAppBar);
        getSupportActionBar().setElevation(0);

        navigationView = binding.navigationView;
        View headerView = navigationView.getHeaderView(0);
        TextView emailHeader = headerView.findViewById(R.id.emailHeader);
        emailHeader.setText(user.getEmail());
        TextView nameHeader = headerView.findViewById(R.id.usernameHeader);
        nameHeader.setText(user.getName()+" "+user.getSurname());
        drawerLayout = binding.drawerLayout;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph())
                        .setDrawerLayout(drawerLayout)
                        .build();

        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.i("chau", String.valueOf(item.getItemId()));
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }
}