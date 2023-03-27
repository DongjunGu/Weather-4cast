package com.mobileapp.a4cast.ui.recommendations;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.mobileapp.a4cast.databinding.FragmentRecommendationBinding;


public class RecommendationFragment extends Fragment {

    private FragmentRecommendationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        RecommendationViewModel recommendationViewModel = new ViewModelProvider(this).get(RecommendationViewModel.class);
        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ImageButton clothesButton = binding.outfitButton;
        ImageButton foodButton = binding.foodButton;
        ImageButton activityButton = binding.activityButton;


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
}