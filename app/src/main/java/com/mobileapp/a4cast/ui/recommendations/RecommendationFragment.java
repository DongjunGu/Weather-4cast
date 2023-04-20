package com.mobileapp.a4cast.ui.recommendations;

import android.media.Image;
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
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.databinding.FragmentRecommendationBinding;

import java.util.List;
import java.util.Locale;


public class RecommendationFragment extends Fragment {

    private FragmentRecommendationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendation, container, false);
        RecommendationViewModel recommendationViewModel = new ViewModelProvider(this).get(RecommendationViewModel.class);
        binding = FragmentRecommendationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        double currentTemp = GlobalData.getInstance().getLocationTemp();



        TextView cityText = binding.todayText;
        TextView cityTempText = binding.todayTempText;

        cityText.setText(GlobalData.getInstance().getLocationCity());


        if(!GlobalData.getInstance().getFahrenheit()) {
            double cel = (GlobalData.getInstance().getLocationTemp() - 32) * (0.55556);
            cityTempText.setText(String.format(Locale.getDefault(), "%.0f°C", cel));
        } else {
            cityTempText.setText(String.format(Locale.getDefault(), "%.0f°F", GlobalData.getInstance().getLocationTemp()));
        }
        //Change image based on temperature
        double temp = GlobalData.getInstance().getCurrentTemp() + GlobalData.getInstance().getPersonalTemp();
        System.out.println(temp);
        if(temp < 11) {
            binding.outfitButton.setImageResource(R.drawable.outfits1);
            binding.foodButton.setImageResource(R.drawable.food1);
            binding.activityButton.setImageResource(R.drawable.activity1);
        }else if(temp > 10 && temp < 24){
            binding.outfitButton.setImageResource(R.drawable.outfits2);
            binding.foodButton.setImageResource(R.drawable.food2);
            binding.activityButton.setImageResource(R.drawable.activity2);
        }else if(temp > 23 && temp < 33){
            binding.outfitButton.setImageResource(R.drawable.outfits3);
            binding.foodButton.setImageResource(R.drawable.food3);
            binding.activityButton.setImageResource(R.drawable.activity3);
        }else if(temp > 32 && temp < 42){
            binding.outfitButton.setImageResource(R.drawable.outfits4);
            binding.foodButton.setImageResource(R.drawable.food4);
            binding.activityButton.setImageResource(R.drawable.activity4);
        }else if(temp > 41 && temp < 51){
            binding.outfitButton.setImageResource(R.drawable.outfits5);
            binding.foodButton.setImageResource(R.drawable.food5);
            binding.activityButton.setImageResource(R.drawable.activity5);
        }else if(temp > 50 && temp < 60){
            binding.outfitButton.setImageResource(R.drawable.outfits6);
            binding.foodButton.setImageResource(R.drawable.food6);
            binding.activityButton.setImageResource(R.drawable.activity6);
        }else if(temp > 59 && temp < 69){
            binding.outfitButton.setImageResource(R.drawable.outfits7);
            binding.foodButton.setImageResource(R.drawable.food7);
            binding.activityButton.setImageResource(R.drawable.activity7);
        }else if(temp > 68){
            binding.outfitButton.setImageResource(R.drawable.outfits7);
            binding.foodButton.setImageResource(R.drawable.food8);
            binding.activityButton.setImageResource(R.drawable.activity8);
        }


        binding.outfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "RECOMMENDATION FRAGMENT: Clothes Button Pressed");
                NavDirections action = RecommendationFragmentDirections.actionNavigationRecommendationsToClothesFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.foodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "RECOMMENDATION FRAGMENT: Food Button Pressed");
                NavDirections action = RecommendationFragmentDirections.actionNavigationRecommendationsToFoodFragment();
                Navigation.findNavController(view).navigate(action);
            }
        });

        binding.activityButton.setOnClickListener(new View.OnClickListener() {
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