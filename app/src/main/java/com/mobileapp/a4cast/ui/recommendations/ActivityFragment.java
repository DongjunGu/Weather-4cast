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

public class ActivityFragment extends Fragment { // ACTIVITY
    View view;
    RecyclerViewAdaptor adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>displayList;
    List<DatabaseItem> activityList;
    List<DatabaseItem> conditions;
    List<DatabaseItem> temps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_activity, container, false);
        Button backButton = view.findViewById(R.id.activityBackButton);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);


        // get the conditions list from another fragment
        conditions = GlobalData.getInstance().getConditions();
        temps = GlobalData.getInstance().getTemps();
        Log.d("DEBUG", "ACTIVITY FRAGMENT: condition.size: " + conditions.size());
        Log.d("DEBUG", "ACTIVITY FRAGMENT: temp.size: " + temps.size());
        activityList = new ArrayList<>();
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

        displayList = new ArrayList<>();
        initData();
        initRecyclerView();

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapter = new RecyclerViewAdaptor(this, colorsNames);
        recyclerView.setAdapter(adapter);

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

    private void initData(){ //CHANGE HERE
        for(int i = 0; i < activityList.size(); i++) {
            DatabaseItem dbItem = activityList.get(i);
            switch (dbItem.getName()) {
                case "CAFE":
                    //displayList.add(new ModelClass(R.drawable.IMAGE, "MAIN_TEXT", dbItem.getLink())); <------
                    displayList.add(new ModelClass(R.drawable.cafe, "Cafe", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "SKI":
                    displayList.add(new ModelClass(R.drawable.ski, "Ski", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "ICE FISHING":
                    displayList.add(new ModelClass(R.drawable.icefishing, "Ice Fishing", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "ICE SKATE":
                    displayList.add(new ModelClass(R.drawable.iceskate, "Ice Skating", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "MUSEUM":
                    displayList.add(new ModelClass(R.drawable.museum, "Museum", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "PLAY":
                    displayList.add(new ModelClass(R.drawable.play, "Play", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "BOWLING":
                    displayList.add(new ModelClass(R.drawable.bowling, "Bowling", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "MOVIE":
                    displayList.add(new ModelClass(R.drawable.movie, "Movie", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "HIKE":
                    displayList.add(new ModelClass(R.drawable.hike, "Hike", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "BIKE":
                    displayList.add(new ModelClass(R.drawable.bike, "Bike", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "AMUSEMENT PARK":
                    displayList.add(new ModelClass(R.drawable.amusementpark, "Amusement Park", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "PICNIC":
                    displayList.add(new ModelClass(R.drawable.picnic, "Picnic", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "BEACH":
                    displayList.add(new ModelClass(R.drawable.beach, "Beach", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "CONCERT":
                    displayList.add(new ModelClass(R.drawable.concert, "Concert", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "WATER PARK":
                    displayList.add(new ModelClass(R.drawable.waterpark, "Water Park", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                case "WATER SPORTS":
                    displayList.add(new ModelClass(R.drawable.watersports, "Water Sports", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
                default:
                    displayList.add(new ModelClass(R.drawable.sun, "DEFAULT", dbItem.getLink(),dbItem.getRecipe(), dbItem.getComment()));
                    break;
            }
        }
    }

    private void initRecyclerView() {
        recyclerView=view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(view.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdaptor(displayList, 2);
        adapter.notifyDataSetChanged();
    }
}