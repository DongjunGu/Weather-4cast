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
                case "LEGGINGS":
                    //displayList.add(new ModelClass(R.drawable.IMAGE, "MAIN_TEXT", dbItem.getLink())); <------
                    displayList.add(new ModelClass(R.drawable.leggings, "Leggings", dbItem.getLink()));
                    break;
                case "EARMUFFS":
                    displayList.add(new ModelClass(R.drawable.earmuffs, "Earmuffs", dbItem.getLink()));
                    break;
                case "GLOVES":
                    displayList.add(new ModelClass(R.drawable.gloves, "Gloves", dbItem.getLink()));
                    break;
                case "WOOLEN HAT":
                    displayList.add(new ModelClass(R.drawable.woolenhat, "Woolen Hat", dbItem.getLink()));
                    break;
                case "SWEATER":
                    displayList.add(new ModelClass(R.drawable.sweater, "Sweater", dbItem.getLink()));
                    break;
                case "PADDED JACKET":
                    displayList.add(new ModelClass(R.drawable.paddedjacket, "Padded Jacket", dbItem.getLink()));
                    break;
                case "FLEECE-LINED PANTS":
                    displayList.add(new ModelClass(R.drawable.fleecelinedpants, "Fleece-lined Pants", dbItem.getLink()));
                    break;
                case "LONG UNDERWEAR":
                    displayList.add(new ModelClass(R.drawable.longunderwear, "Long Underwear", dbItem.getLink()));
                    break;
                case "COAT":
                    displayList.add(new ModelClass(R.drawable.coat, "Coat", dbItem.getLink()));
                    break;
                case "BOOTS":
                    displayList.add(new ModelClass(R.drawable.boots, "Boots", dbItem.getLink()));
                    break;
                case "JEANS":
                    displayList.add(new ModelClass(R.drawable.jeans, "Jeans", dbItem.getLink()));
                    break;
                case "TRENCH COAT":
                    displayList.add(new ModelClass(R.drawable.trenchcoat, "Trench Coat", dbItem.getLink()));
                    break;
                case "HOOD T-SHIRT":
                    displayList.add(new ModelClass(R.drawable.hoodtshirt, "Hood T-Shirt", dbItem.getLink()));
                    break;
                case "SNEAKERS":
                    displayList.add(new ModelClass(R.drawable.sneakers, "Sneakers", dbItem.getLink()));
                    break;
                case "LOAFERS":
                    displayList.add(new ModelClass(R.drawable.loafers, "Loafer", dbItem.getLink()));
                    break;
                case "LEATHER JACKET":
                    displayList.add(new ModelClass(R.drawable.leatherjacket, "Leather Jacket", dbItem.getLink()));
                    break;
                case "CHINO PANTS":
                    displayList.add(new ModelClass(R.drawable.chinopants, "Chino Pants", dbItem.getLink()));
                    break;
                case "T-SHIRTS":
                    displayList.add(new ModelClass(R.drawable.tshirts, "T-Shirts", dbItem.getLink()));
                    break;
                case "JACKET":
                    displayList.add(new ModelClass(R.drawable.jacket, "Jacket", dbItem.getLink()));
                    break;
                case "CARDIGAN":
                    displayList.add(new ModelClass(R.drawable.cardigan, "Cardigan", dbItem.getLink()));
                    break;
                case "DRESS SHIRTS":
                    displayList.add(new ModelClass(R.drawable.dressshirts, "Dress Shirts", dbItem.getLink()));
                    break;
                case "SUNGLASSES":
                    displayList.add(new ModelClass(R.drawable.sunglasses, "Sunglasses", dbItem.getLink()));
                    break;
                case "SLEEVELESS":
                    displayList.add(new ModelClass(R.drawable.sleeveless, "Sleeveless", dbItem.getLink()));
                    break;
                case "SHORTS":
                    displayList.add(new ModelClass(R.drawable.shorts, "Shorts", dbItem.getLink()));
                    break;
                case "ONE PIECE":
                    displayList.add(new ModelClass(R.drawable.onepiece, "One Piece", dbItem.getLink()));
                    break;
                case "SANDAL":
                    displayList.add(new ModelClass(R.drawable.sandal, "Sandals", dbItem.getLink()));
                    break;
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
        adapter = new RecyclerViewAdaptor(displayList);
        adapter.notifyDataSetChanged();
    }
}