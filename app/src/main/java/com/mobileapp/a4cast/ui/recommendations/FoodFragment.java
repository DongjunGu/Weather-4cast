package com.mobileapp.a4cast.ui.recommendations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.R;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment { // FOOD
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clothes, container, false);

        TextView text1 = view.findViewById(R.id.textView1);

        // get the conditions list from another fragment
        List<DatabaseItem> conditions = GlobalData.getInstance().getConditions();
        List<DatabaseItem> temps = GlobalData.getInstance().getTemps();
        Log.d("DEBUG", "FOOD FRAGMENT: condition.size: " + conditions.size());
        Log.d("DEBUG", "FOOD FRAGMENT: temp.size: " + temps.size());
        List<DatabaseItem> foodList = new ArrayList<>();
        //CREATE NEW LIST
        for (int i = 0; i < conditions.size(); i++) {
            DatabaseItem item1 = conditions.get(i);
            // iterate over the second list
            for (int j = 0; j < temps.size(); j++) {
                DatabaseItem item2 = temps.get(j);
                // check if the items are equal
                //Log.d("DEBUG", "CLOTHES FRAGMENT: " + item1.getName() + " COMPAIRED TO " + item2.getName());
                if (item1.getName().equals(item2.getName())) {
                    // add the item to the commonItems list if it's present in both lists
                    if (item1.getType().equals("FOOD")) {
                        Log.d("DEBUG", "FOOD FRAGMENT: added: " + item1.getName());
                        foodList.add(item1);
                    }

                    break; // break out of the inner loop to avoid duplicates
                }
            }
        }

        String tempSTR = "";
        for (int i = 0; i < foodList.size(); i++) {
                tempSTR += foodList.get(i).getName() + "\n";
        }
        text1.setText(tempSTR);

        /**
         * Example:
         * foodList.get(INT).getName()
         * foodList.get(INT).getMinTemp()
         * foodList.get(INT).getMaxTemp()
         * foodList.get(INT).getType()
         * etc...
         */

        return view;
    }
    @Override
    public void onDestroyView () {
        Log.d("DEBUG", "FOOD FRAGMENT: ON_DESTROY");
        super.onDestroyView();

    }
}