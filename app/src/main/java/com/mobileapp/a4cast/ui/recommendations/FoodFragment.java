package com.mobileapp.a4cast.ui.recommendations;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.ModelClass;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.RecyclerViewAdaptor;

import java.util.ArrayList;
import java.util.List;

public class FoodFragment extends Fragment { // FOOD
    View view;
    RecyclerViewAdaptor adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>displayList;
    List<DatabaseItem> foodList;
    List<DatabaseItem> conditions;
    List<DatabaseItem> temps;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_food, container, false);
        Button backButton = view.findViewById(R.id.foodBackButton);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);


        // get the conditions list from another fragment
        conditions = GlobalData.getInstance().getConditions();
        temps = GlobalData.getInstance().getTemps();
        Log.d("DEBUG", "FOOD FRAGMENT: condition.size: " + conditions.size());
        Log.d("DEBUG", "FOOD FRAGMENT: temp.size: " + temps.size());
        foodList = new ArrayList<>();

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
        displayList = new ArrayList<>();
        initData();
        initRecyclerView();
        for (int i = 0; i < conditions.size(); i++) {
            if (conditions.get(i).getName().equals("POT ROAST")) {
                Log.d("DEBUG", "FOOD FRAGMENT: ICECREAM RECIPE: " + conditions.get(i).getRecipe());
            }
        }

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapter = new RecyclerViewAdaptor(this, colorsNames);
        recyclerView.setAdapter(adapter);

        /**
         * Example:
         * foodList.get(INT).getName()
         * foodList.get(INT).getMinTemp()
         * foodList.get(INT).getMaxTemp()
         * foodList.get(INT).getType()
         * etc...
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "FOOD FRAGMENT: Back Button Pressed");
                NavDirections action = FoodFragmentDirections.actionFoodFragmentToNavigationRecommendations();
                Navigation.findNavController(view).navigate(action);
            }
        });
        return view;
    }
    @Override
    public void onDestroyView () {
        Log.d("DEBUG", "FOOD FRAGMENT: ON_DESTROY");
        super.onDestroyView();

    }

    private void initData(){ //CHANGE HERE
        for(int i = 0; i < foodList.size(); i++) {
            DatabaseItem dbItem = foodList.get(i);
            switch (dbItem.getName()) {
                case "POT ROAST":
                    displayList.add(new ModelClass(R.drawable.pot_roast, "Pot Roast", dbItem.getLink()));
                    break;
                case "BEEF STEW":
                    displayList.add(new ModelClass(R.drawable.beef_stew, "Beef Stew", dbItem.getLink()));
                    break;
                case "RAMEN":
                    displayList.add(new ModelClass(R.drawable.ramen, "Ramen", dbItem.getLink()));
                    break;
                case "CRUMBLE":
                    displayList.add(new ModelClass(R.drawable.crumble, "Crumble", dbItem.getLink()));
                    break;
                case "SUSHI":
                    displayList.add(new ModelClass(R.drawable.sushi, "Sushi", dbItem.getLink()));
                    break;
                case "GRATIN":
                    displayList.add(new ModelClass(R.drawable.gratin, "Gratin", dbItem.getLink()));
                    break;
                case "HOT TEA":
                    displayList.add(new ModelClass(R.drawable.hot_tea, "Hot Tea", dbItem.getLink()));
                    break;
                case "CHINESE":
                    displayList.add(new ModelClass(R.drawable.chinese, "Chinese", dbItem.getLink()));
                    break;
                case "PASTA":
                    displayList.add(new ModelClass(R.drawable.pasta, "Pasta", dbItem.getLink()));
                    break;
                case "SALAD":
                    displayList.add(new ModelClass(R.drawable.salad, "Salad", dbItem.getLink()));
                    break;
                case "POTATO FOOD":
                    displayList.add(new ModelClass(R.drawable.potato, "Potato Food", dbItem.getLink()));
                    break;
                case "MEXICAN":
                    displayList.add(new ModelClass(R.drawable.mexicans, "Mexican", dbItem.getLink()));
                    break;
                case "CARROT CAKE":
                    displayList.add(new ModelClass(R.drawable.carrot_cake, "Carrot Cake", dbItem.getLink()));
                    break;
                case "SALMON":
                    displayList.add(new ModelClass(R.drawable.salmon, "Salmon", dbItem.getLink()));
                    break;
                case "LOBSTER":
                    displayList.add(new ModelClass(R.drawable.lobster, "Lobster", dbItem.getLink()));
                    break;
                case "PIE":
                    displayList.add(new ModelClass(R.drawable.pie, "Pie", dbItem.getLink()));
                    break;
                case "WATERMELON":
                    displayList.add(new ModelClass(R.drawable.watermelon, "Watermelon", dbItem.getLink()));
                    break;
                case "GRILLED CHICKEN":
                    displayList.add(new ModelClass(R.drawable.grilled_chicken, "Grilled Chicken", dbItem.getLink()));
                    break;
                case "BBQ":
                    displayList.add(new ModelClass(R.drawable.bbq, "BBQ", dbItem.getLink()));
                    break;
                case "ICE CREAM":
                    displayList.add(new ModelClass(R.drawable.icecream, "Ice cream", dbItem.getLink()));
                    break;
                case "FISH TACO":
                    displayList.add(new ModelClass(R.drawable.fish_taco, "Fish Taco", dbItem.getLink()));
                    break;
                case "FRUIT SALAD":
                    displayList.add(new ModelClass(R.drawable.fruit_salad, "Fruit Salad", dbItem.getLink()));
                default:
                    displayList.add(new ModelClass(R.drawable.sun, "DEFAULT", dbItem.getLink()));
                    break;
            }
        }
    }

    private void initRecyclerView() {
        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdaptor(displayList, 3);
        adapter.notifyDataSetChanged();
    }
}