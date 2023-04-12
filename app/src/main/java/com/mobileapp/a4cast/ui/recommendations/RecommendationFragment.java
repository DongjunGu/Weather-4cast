package com.mobileapp.a4cast.ui.recommendations;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.databinding.FragmentRecommendationBinding;

import java.util.Locale;


public class RecommendationFragment extends Fragment {

    private FragmentRecommendationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        RecommendationViewModel recommendationViewModel = new ViewModelProvider(this).get(RecommendationViewModel.class);
        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        double currentTemp = GlobalData.getInstance().getLocationTemp();

        ImageButton clothesButton = binding.outfitButton;
        ImageButton foodButton = binding.foodButton;
        ImageButton activityButton = binding.activityButton;

        TextView cityText = binding.todayText;
        TextView cityTempText = binding.todayTempText;

        cityText.setText(GlobalData.getInstance().getLocationCity());


        if(!GlobalData.getInstance().getFahrenheit()) {
            double cel = (GlobalData.getInstance().getLocationTemp() - 32) * (0.55556);
            cityTempText.setText(String.format(Locale.getDefault(), "%.0f°C", cel));
        } else {
            cityTempText.setText(String.format(Locale.getDefault(), "%.0f°F", GlobalData.getInstance().getLocationTemp()));
        }


        clothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "RECOMMENDATION FRAGMENT: Clothes Button Pressed");
                NavDirections action = RecommendationFragmentDirections.actionNavigationRecommendationsToClothesFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "RECOMMENDATION FRAGMENT: Food Button Pressed");
                NavDirections action = RecommendationFragmentDirections.actionNavigationRecommendationsToFoodFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "RECOMMENDATION FRAGMENT: Activities Button Pressed");
                NavDirections action = RecommendationFragmentDirections.actionNavigationRecommendationsToActivityFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}