package dev.rodni.ru.googlemapsandplaces.ui.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationMenu;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import dagger.android.support.DaggerAppCompatActivity;
import dev.rodni.ru.googlemapsandplaces.R;

public class AuthActivity extends DaggerAppCompatActivity implements View.OnClickListener {
    private static final String TAG = "AuthActivity";

    private NavController navController;
    //private NavigationView navigationView;
    private Toolbar toolbar;

    private TextView navLoginTV, navRegisterTV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navLoginTV = findViewById(R.id.login_text_view);
        navRegisterTV = findViewById(R.id.register_text_view);

        //set up the nav controller
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_text_view:
                //nav to the main fragment
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.auth, true)
                        .build();
                navController.navigate(R.id.loginScreen, null, navOptions);
                break;
            case R.id.register_text_view:
                //checking if we already into registrScreen
                if (isValidDestination(R.id.registrScreen)) {
                    navController.navigate(R.id.register_text_view);
                }
                break;
        }
    }

    //this method check if we want to navigate to a current fragment
    private boolean isValidDestination(int destination){
        return destination != Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination().getId();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.auth));
        return true;
    }
}
