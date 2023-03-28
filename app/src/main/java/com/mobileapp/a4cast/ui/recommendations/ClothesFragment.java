package com.mobileapp.a4cast.ui.recommendations;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

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
import com.mobileapp.a4cast.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClothesFragment extends Fragment { // CLOTHES

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_clothes, container, false);
        Button backButton = view.findViewById(R.id.clothesBackButton);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.GONE);

        TextView text1 = view.findViewById(R.id.clothesText1);
        TextView text2 = view.findViewById(R.id.clothesText2);
        TextView text3 = view.findViewById(R.id.clothesText3);
        ImageView image1 = view.findViewById(R.id.clothesImage1);
        ImageView image2 = view.findViewById(R.id.clothesImage2);
        ImageView image3 = view.findViewById(R.id.clothesImage3);
        Button button1 = view.findViewById(R.id.clothesButton1);
        Button button2 = view.findViewById(R.id.clothesButton2);
        Button button3 = view.findViewById(R.id.clothesButton3);


        // get the conditions list from another fragment
        List<DatabaseItem> conditions = GlobalData.getInstance().getConditions();
        List<DatabaseItem> temps = GlobalData.getInstance().getTemps();
        Log.d("DEBUG", "CLOTHES FRAGMENT: condition.size: " + conditions.size());
        Log.d("DEBUG", "CLOTHES FRAGMENT: temp.size: " + temps.size());
        List<DatabaseItem> clothesList = new ArrayList<>();

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

        String tempSTR = "";
        /**
         for (int i = 0; i < clothesList.size(); i++) {
         tempSTR += clothesList.get(i).getName() + "\n";
         }
         */

        //text1.setText(clothesList.get(0).getName() + " " + clothesList.get(0).getMaxTemp() +" " +clothesList.get(0).getConditions());
        text1.setText(clothesList.get(0).getName());
            String clothes = clothesList.get(0).getName();
            switch (clothes) {
                case "LEGGINGS":
                    image1.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image1.setImageResource(R.drawable.clouds);
                    break;
                case "GLOVES":
                    image1.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image1.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image1.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image1.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image1.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image1.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image1.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image1.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image1.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image1.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image1.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image1.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image1.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image1.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image1.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image1.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image1.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image1.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image1.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image1.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image1.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image1.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image1.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image1.setImageResource(R.drawable.sandal);
                    break;
            }

            text2.setText(clothesList.get(1).getName());
        String clothes1 = clothesList.get(1).getName();
        switch (clothes1) {
            case "LEGGINGS":
                image2.setImageResource(R.drawable.leggings);
                break;
            case "EARMUFFS":
                image2.setImageResource(R.drawable.clouds);
                break;
            case "GLOVES":
                image2.setImageResource(R.drawable.gloves);
                break;
            case "WOOLEN HAT":
                image2.setImageResource(R.drawable.woolenhat);
                break;
            case "SWEATER":
                image2.setImageResource(R.drawable.sweater);
                break;
            case "PADDED JACKET":
                image2.setImageResource(R.drawable.paddedjacket);
                break;
            case "FLEECE-LINED PANTS":
                image2.setImageResource(R.drawable.fleecelinedpants);
                break;
            case "LONG UNDERWEAR":
                image2.setImageResource(R.drawable.longunderwear);
                break;
            case "COAT":
                image2.setImageResource(R.drawable.coat);
                break;
            case "BOOTS":
                image2.setImageResource(R.drawable.boots);
                break;
            case "JEANS":
                image2.setImageResource(R.drawable.jeans);
                break;
            case "TRENCH COAT":
                image2.setImageResource(R.drawable.trenchcoat);
                break;
            case "HOOD T-SHIRT":
                image2.setImageResource(R.drawable.hoodtshirt);
                break;
            case "SNEAKERS":
                image2.setImageResource(R.drawable.sneakers);
                break;
            case "LOAFERS":
                image1.setImageResource(R.drawable.loafers);
                break;
            case "LEATHER JACKET":
                image2.setImageResource(R.drawable.leatherjacket);
                break;
            case "CHINO PANTS":
                image2.setImageResource(R.drawable.chinopants);
                break;
            case "T-SHIRTS":
                image2.setImageResource(R.drawable.tshirts);
                break;
            case "JACKET":
                image2.setImageResource(R.drawable.jacket);
                break;
            case "CARDIGAN":
                image2.setImageResource(R.drawable.cardigan);
                break;
            case "DRESS SHIRTS":
                image2.setImageResource(R.drawable.dressshirts);
                break;
            case "SUNGLASSES":
                image2.setImageResource(R.drawable.sunglasses);
                break;
            case "SLEEVELESS":
                image2.setImageResource(R.drawable.sleeveless);
                break;
            case "SHORTS":
                image2.setImageResource(R.drawable.shorts);
                break;
            case "ONE PIECE":
                image2.setImageResource(R.drawable.onepiece);
                break;
            case "SANDAL":
                image2.setImageResource(R.drawable.sandal);
                break;
        }
        text2.setText(clothesList.get(1).getName());

        text3.setText(clothesList.get(2).getName());
        String clothes2 = clothesList.get(2).getName();
        switch (clothes2) {
            case "LEGGINGS":
                image3.setImageResource(R.drawable.leggings);
                break;
            case "EARMUFFS":
                image3.setImageResource(R.drawable.clouds);
                break;
            case "GLOVES":
                image3.setImageResource(R.drawable.gloves);
                break;
            case "WOOLEN HAT":
                image3.setImageResource(R.drawable.woolenhat);
                break;
            case "SWEATER":
                image3.setImageResource(R.drawable.sweater);
                break;
            case "PADDED JACKET":
                image3.setImageResource(R.drawable.paddedjacket);
                break;
            case "FLEECE-LINED PANTS":
                image3.setImageResource(R.drawable.fleecelinedpants);
                break;
            case "LONG UNDERWEAR":
                image3.setImageResource(R.drawable.longunderwear);
                break;
            case "COAT":
                image3.setImageResource(R.drawable.coat);
                break;
            case "BOOTS":
                image3.setImageResource(R.drawable.boots);
                break;
            case "JEANS":
                image3.setImageResource(R.drawable.jeans);
                break;
            case "TRENCH COAT":
                image3.setImageResource(R.drawable.trenchcoat);
                break;
            case "HOOD T-SHIRT":
                image3.setImageResource(R.drawable.hoodtshirt);
                break;
            case "SNEAKERS":
                image3.setImageResource(R.drawable.sneakers);
                break;
            case "LOAFERS":
                image3.setImageResource(R.drawable.loafers);
                break;
            case "LEATHER JACKET":
                image3.setImageResource(R.drawable.leatherjacket);
                break;
            case "CHINO PANTS":
                image3.setImageResource(R.drawable.chinopants);
                break;
            case "T-SHIRTS":
                image3.setImageResource(R.drawable.tshirts);
                break;
            case "JACKET":
                image3.setImageResource(R.drawable.jacket);
                break;
            case "CARDIGAN":
                image3.setImageResource(R.drawable.cardigan);
                break;
            case "DRESS SHIRTS":
                image3.setImageResource(R.drawable.dressshirts);
                break;
            case "SUNGLASSES":
                image3.setImageResource(R.drawable.sunglasses);
                break;
            case "SLEEVELESS":
                image3.setImageResource(R.drawable.sleeveless);
                break;
            case "SHORTS":
                image3.setImageResource(R.drawable.shorts);
                break;
            case "ONE PIECE":
                image3.setImageResource(R.drawable.onepiece);
                break;
            case "SANDAL":
                image3.setImageResource(R.drawable.sandal);
                break;
        }



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
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(0).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(1).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(2).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        Log.d("DEBUG", "CLOTHES FRAGMENT: ON_DESTROY");
        super.onDestroyView();

    }
}
