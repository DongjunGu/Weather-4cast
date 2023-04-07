package com.mobileapp.a4cast;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.a4cast.databinding.ActivityMainBinding;
/*
Mobile App Development II -- COMP.4631 Honor Statement
The practice of good ethical behavior is essential for maintaining good order in the classroom, providing an enriching learning experience for students, and training as a practicing computing professional upon graduation. This practice is manifested in the University's Academic Integrity policy. Students are expected to strictly avoid academic dishonesty and adhere to the Academic Integrity policy as outlined in the course catalog. Violations will be dealt with as outlined therein. All programming assignments in this class are to be done by the student alone unless otherwise specified. No outside help is permitted except the instructor and approved tutors.

I certify that the work submitted with this assignment is mine and was generated in a manner consistent with this document, the course academic policy on the course website on Blackboard, and the UMass Lowell academic code.

Date: 28/03/2023
Name: Dongjun Gu, Robert Russell
https://github.com/UML-COMP4630-LIN/project-prerelease-group9_dongjungu_robertrussell
*/
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_recommendations, R.id.navigation_settings)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        getSupportActionBar().hide();

        navView.setVisibility(View.GONE);

        String apiKey = "AIzaSyDNSC1pLhUVjLp9G7J4sm5FrELV-efALdM";

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }
    }
    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        int count = fragmentManager.getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            fragmentManager.popBackStack();
        }
    }

}
