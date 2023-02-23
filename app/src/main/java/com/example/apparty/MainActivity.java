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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.apparty.databinding.ActivityMainBinding;
import com.example.apparty.gestores.GestorUser;
import com.example.apparty.model.User;
import com.example.apparty.persistence.room.RoomDB;
import com.example.apparty.persistence.room.daos.EventDAO;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private GestorUser gestorUser;
    private User user;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        sharedPreferences = getSharedPreferences("loginInfo",MODE_PRIVATE);
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

        Menu menu = navigationView.getMenu();
        MenuItem closeSessionBtn = menu.findItem(R.id.nav_logout);
        closeSessionBtn.setOnMenuItemClickListener(v -> {
            closeSession();
            return false;
        });

        drawerLayout = binding.drawerLayout;

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void closeSession() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("idUser");
        editor.commit();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

}