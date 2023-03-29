package com.mobileapp.a4cast.ui.recommendations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityFragment extends Fragment { // ACTIVITY
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        Button backButton = view.findViewById(R.id.activityBackButton);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        TextView text1 = view.findViewById(R.id.textView1);

        // get the conditions list from another fragment
        List<DatabaseItem> conditions = GlobalData.getInstance().getConditions();
        List<DatabaseItem> temps = GlobalData.getInstance().getTemps();
        Log.d("DEBUG", "ACTIVITY FRAGMENT: condition.size: " + conditions.size());
        Log.d("DEBUG", "ACTIVITY FRAGMENT: temp.size: " + temps.size());
        List<DatabaseItem> activityList = new ArrayList<>();
        for (int i = 0; i < temps.size(); i++) {
            Log.d("DEBUG", "ACTIVITY FRAGMENT: TEST: " + temps.get(i).getName());
        }
        //CREATE NEW LIST
        for (int i = 0; i < conditions.size(); i++) {
            DatabaseItem item1 = conditions.get(i);
            // iterate over the second list
            for (int j = 0; j < temps.size(); j++) {
                DatabaseItem item2 = temps.get(j);
                // check if the items are equal
                //Log.d("DEBUG", "ACTIVITY FRAGMENT: " + item1.getName() + " COMPAIRED TO " + item2.getName());
                if (item1.getName().equals(item2.getName())) {
                    // add the item to the commonItems list if it's present in both lists
                    if (item1.getType().equals("ACTIVITY")) {
                        Log.d("DEBUG", "ACTIVITY FRAGMENT: added: " + item1.getName());
                        activityList.add(item1);
                    }

                    break; // break out of the inner loop to avoid duplicates
                }
            }
        }

        String tempSTR = "";
        for (int i = 0; i < activityList.size(); i++) {
            tempSTR += activityList.get(i).getName() + "\n";
        }
        text1.setText(tempSTR);

        for (int i = 0; i < activityList.size(); i++) {
            Log.d("DEBUG", "ACTIVITY FRAGMENT: TEST: " + activityList.get(i).getName());
        }

        /**
         * Example:
         * activityList.get(INT).getName()
         * activityList.get(INT).getMinTemp()
         * activityList.get(INT).getMaxTemp()
         * activityList.get(INT).getType()
         * etc...
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "ACTIVITY FRAGMENT: Clothes Button Pressed");
                NavDirections action = ActivityFragmentDirections.actionActivityFragmentToNavigationRecommendations();
                Navigation.findNavController(view).navigate(action);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d("DEBUG", "ACTIVITY FRAGMENT: ON_DESTROY");
        super.onDestroyView();

    }
}