package com.mobileapp.a4cast.ui.recommendations;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobileapp.a4cast.DatabaseItem;
import com.mobileapp.a4cast.GlobalData;
import com.mobileapp.a4cast.ModelClass;
import com.mobileapp.a4cast.R;
import com.mobileapp.a4cast.RecyclerViewAdaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClothesFragment extends Fragment { // CLOTHES

    View view;
    RecyclerViewAdaptor adapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    List<ModelClass>displayList;
    List<DatabaseItem> clothesList;
    List<DatabaseItem> conditions;
    List<DatabaseItem> temps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clothes, container, false);
        Button backButton = view.findViewById(R.id.clothesBackButton);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);


        // get the conditions list from another fragment
        conditions = GlobalData.getInstance().getConditions();
        temps = GlobalData.getInstance().getTemps();
        Log.d("DEBUG", "CLOTHES FRAGMENT: condition.size: " + conditions.size());
        Log.d("DEBUG", "CLOTHES FRAGMENT: temp.size: " + temps.size());
        clothesList = new ArrayList<>();

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
                    if (item1.getType().equals("CLOTHING")) {
                        Log.d("DEBUG", "CLOTHES FRAGMENT: added: " + item1.getName());
                        clothesList.add(item1);
                    }
                    break; // break out of the inner loop to avoid duplicates
                }
            }
        }
        //clothesList
        displayList = new ArrayList<>();
        initData();
        initRecyclerView();



        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //adapter = new RecyclerViewAdaptor(this, colorsNames);
        recyclerView.setAdapter(adapter);

        //String tempSTR = "";
        /**
         for (int i = 0; i < clothesList.size(); i++) {
         tempSTR += clothesList.get(i).getName() + "\n";
         }
         */

        //text1.setText(clothesList.get(0).getName() + " " + clothesList.get(0).getMaxTemp() +" " +clothesList.get(0).getConditions());
        /**
         * Example:
         * clothesList.get(INT).getName()
         * clothesList.get(INT).getMinTemp()
         * clothesList.get(INT).getMaxTemp()
         * clothesList.get(INT).getType()
         * clothesList.get(INT).getLink()
         * etc...
         */
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("DEBUG", "CLOTHES FRAGMENT: Back Button Pressed");
                NavDirections action = ClothesFragmentDirections.actionClothesFragmentToNavigationRecommendations();
                Navigation.findNavController(view).navigate(action);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d("DEBUG", "CLOTHES FRAGMENT: ON_DESTROY");
        super.onDestroyView();

    }

    private void initData(){
        for(int i = 0; i < clothesList.size(); i++) {
            DatabaseItem dbItem = clothesList.get(i);
            switch (dbItem.getName()) {
                case "LEGGINGS":
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
        adapter = new RecyclerViewAdaptor(displayList, 1);
        adapter.notifyDataSetChanged();
    }
}

