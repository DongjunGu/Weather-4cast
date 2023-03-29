package com.mobileapp.a4cast.ui.recommendations;

import android.content.Intent;
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
        TextView text4 = view.findViewById(R.id.clothesText4);
        TextView text5 = view.findViewById(R.id.clothesText5);
        TextView text6 = view.findViewById(R.id.clothesText6);
        TextView text7 = view.findViewById(R.id.clothesText7);
        TextView text8 = view.findViewById(R.id.clothesText8);
        TextView text9 = view.findViewById(R.id.clothesText9);

        ImageView image1 = view.findViewById(R.id.clothesImage1);
        ImageView image2 = view.findViewById(R.id.clothesImage2);
        ImageView image3 = view.findViewById(R.id.clothesImage3);
        ImageView image4 = view.findViewById(R.id.clothesImage4);
        ImageView image5 = view.findViewById(R.id.clothesImage5);
        ImageView image6 = view.findViewById(R.id.clothesImage6);
        ImageView image7 = view.findViewById(R.id.clothesImage7);
        ImageView image8 = view.findViewById(R.id.clothesImage8);
        ImageView image9 = view.findViewById(R.id.clothesImage9);

        Button button1 = view.findViewById(R.id.clothesButton1);
        Button button2 = view.findViewById(R.id.clothesButton2);
        Button button3 = view.findViewById(R.id.clothesButton3);
        Button button4 = view.findViewById(R.id.clothesButton4);
        Button button5 = view.findViewById(R.id.clothesButton5);
        Button button6 = view.findViewById(R.id.clothesButton6);
        Button button7 = view.findViewById(R.id.clothesButton7);
        Button button8 = view.findViewById(R.id.clothesButton8);
        Button button9 = view.findViewById(R.id.clothesButton9);


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

        //String tempSTR = "";
        /**
         for (int i = 0; i < clothesList.size(); i++) {
         tempSTR += clothesList.get(i).getName() + "\n";
         }
         */

        //text1.setText(clothesList.get(0).getName() + " " + clothesList.get(0).getMaxTemp() +" " +clothesList.get(0).getConditions());

        if (clothesList.size() > 1) {
            text1.setText(clothesList.get(0).getName());
            String clothes = clothesList.get(0).getName();
            switch (clothes) {
                case "LEGGINGS":
                    image1.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image1.setImageResource(R.drawable.earmuffs);
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
                default:
                    image1.setImageResource(R.drawable.clouds);
                    break;
            }
        } else {
            text1.setVisibility(View.INVISIBLE);
            image1.setVisibility(View.INVISIBLE);
            button1.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 2) {
            text2.setText(clothesList.get(1).getName());
            String clothes1 = clothesList.get(1).getName();
            switch (clothes1) {
                case "LEGGINGS":
                    image2.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image2.setImageResource(R.drawable.earmuffs);
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
        } else {
            text2.setVisibility(View.INVISIBLE);
            image2.setVisibility(View.INVISIBLE);
            button2.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 3) {
            text3.setText(clothesList.get(2).getName());
            String clothes2 = clothesList.get(2).getName();
            switch (clothes2) {
                case "LEGGINGS":
                    image3.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image3.setImageResource(R.drawable.earmuffs);
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

        } else {
            text3.setVisibility(View.INVISIBLE);
            image3.setVisibility(View.INVISIBLE);
            button3.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 4) {
            text4.setText(clothesList.get(3).getName());
            String clothes3 = clothesList.get(3).getName();
            switch (clothes3) {
                case "LEGGINGS":
                    image4.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image4.setImageResource(R.drawable.earmuffs);
                    break;
                case "GLOVES":
                    image4.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image4.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image4.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image4.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image4.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image4.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image4.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image4.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image4.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image4.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image4.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image4.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image4.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image4.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image4.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image4.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image4.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image4.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image4.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image4.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image4.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image4.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image4.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image4.setImageResource(R.drawable.sandal);
                    break;
            }
        } else {
            text4.setVisibility(View.INVISIBLE);
            image4.setVisibility(View.INVISIBLE);
            button4.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 5) {
            text5.setText(clothesList.get(4).getName());
            String clothes4 = clothesList.get(4).getName();
            switch (clothes4) {
                case "LEGGINGS":
                    image5.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image5.setImageResource(R.drawable.earmuffs);
                    break;
                case "GLOVES":
                    image5.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image5.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image5.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image5.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image5.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image5.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image5.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image5.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image5.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image5.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image5.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image5.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image5.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image5.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image5.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image5.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image5.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image5.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image5.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image5.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image5.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image5.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image5.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image5.setImageResource(R.drawable.sandal);
                    break;
            }
        } else {
            text5.setVisibility(View.INVISIBLE);
            image5.setVisibility(View.INVISIBLE);
            button5.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 6) {
            text6.setText(clothesList.get(5).getName());
            String clothes5 = clothesList.get(5).getName();
            switch (clothes5) {
                case "LEGGINGS":
                    image6.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image6.setImageResource(R.drawable.earmuffs);
                    break;
                case "GLOVES":
                    image6.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image6.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image6.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image6.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image6.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image6.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image6.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image6.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image6.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image6.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image6.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image6.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image6.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image6.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image6.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image6.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image6.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image6.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image6.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image6.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image6.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image6.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image6.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image6.setImageResource(R.drawable.sandal);
                    break;
            }
        } else {
            text6.setVisibility(View.INVISIBLE);
            image6.setVisibility(View.INVISIBLE);
            button6.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 7) {
            text7.setText(clothesList.get(6).getName());
            String clothes6 = clothesList.get(6).getName();
            switch (clothes6) {
                case "LEGGINGS":
                    image7.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image7.setImageResource(R.drawable.earmuffs);
                    break;
                case "GLOVES":
                    image7.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image7.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image7.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image7.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image7.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image7.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image7.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image7.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image7.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image7.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image7.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image7.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image7.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image7.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image7.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image7.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image7.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image7.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image7.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image7.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image7.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image7.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image7.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image7.setImageResource(R.drawable.sandal);
                    break;
            }
        } else {
            text7.setVisibility(View.INVISIBLE);
            image7.setVisibility(View.INVISIBLE);
            button7.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 8) {
            text8.setText(clothesList.get(7).getName());
            String clothes7 = clothesList.get(7).getName();
            switch (clothes7) {
                case "LEGGINGS":
                    image8.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image8.setImageResource(R.drawable.earmuffs);
                    break;
                case "GLOVES":
                    image8.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image8.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image8.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image8.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image8.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image8.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image8.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image8.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image8.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image8.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image8.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image8.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image8.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image8.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image8.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image8.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image8.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image8.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image8.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image8.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image8.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image8.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image8.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image8.setImageResource(R.drawable.sandal);
                    break;
            }
        } else {
            text8.setVisibility(View.INVISIBLE);
            image8.setVisibility(View.INVISIBLE);
            button8.setVisibility(View.INVISIBLE);
        }

        if (clothesList.size() > 9) {
            text9.setText(clothesList.get(8).getName());
            String clothes8 = clothesList.get(8).getName();
            switch (clothes8) {
                case "LEGGINGS":
                    image9.setImageResource(R.drawable.leggings);
                    break;
                case "EARMUFFS":
                    image9.setImageResource(R.drawable.earmuffs);
                    break;
                case "GLOVES":
                    image9.setImageResource(R.drawable.gloves);
                    break;
                case "WOOLEN HAT":
                    image9.setImageResource(R.drawable.woolenhat);
                    break;
                case "SWEATER":
                    image9.setImageResource(R.drawable.sweater);
                    break;
                case "PADDED JACKET":
                    image9.setImageResource(R.drawable.paddedjacket);
                    break;
                case "FLEECE-LINED PANTS":
                    image9.setImageResource(R.drawable.fleecelinedpants);
                    break;
                case "LONG UNDERWEAR":
                    image9.setImageResource(R.drawable.longunderwear);
                    break;
                case "COAT":
                    image9.setImageResource(R.drawable.coat);
                    break;
                case "BOOTS":
                    image9.setImageResource(R.drawable.boots);
                    break;
                case "JEANS":
                    image9.setImageResource(R.drawable.jeans);
                    break;
                case "TRENCH COAT":
                    image9.setImageResource(R.drawable.trenchcoat);
                    break;
                case "HOOD T-SHIRT":
                    image9.setImageResource(R.drawable.hoodtshirt);
                    break;
                case "SNEAKERS":
                    image9.setImageResource(R.drawable.sneakers);
                    break;
                case "LOAFERS":
                    image9.setImageResource(R.drawable.loafers);
                    break;
                case "LEATHER JACKET":
                    image9.setImageResource(R.drawable.leatherjacket);
                    break;
                case "CHINO PANTS":
                    image9.setImageResource(R.drawable.chinopants);
                    break;
                case "T-SHIRTS":
                    image9.setImageResource(R.drawable.tshirts);
                    break;
                case "JACKET":
                    image9.setImageResource(R.drawable.jacket);
                    break;
                case "CARDIGAN":
                    image9.setImageResource(R.drawable.cardigan);
                    break;
                case "DRESS SHIRTS":
                    image9.setImageResource(R.drawable.dressshirts);
                    break;
                case "SUNGLASSES":
                    image9.setImageResource(R.drawable.sunglasses);
                    break;
                case "SLEEVELESS":
                    image9.setImageResource(R.drawable.sleeveless);
                    break;
                case "SHORTS":
                    image9.setImageResource(R.drawable.shorts);
                    break;
                case "ONE PIECE":
                    image9.setImageResource(R.drawable.onepiece);
                    break;
                case "SANDAL":
                    image9.setImageResource(R.drawable.sandal);
                    break;
            }
        } else {
            text9.setVisibility(View.INVISIBLE);
            image9.setVisibility(View.INVISIBLE);
            button9.setVisibility(View.INVISIBLE);
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

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(3).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(4).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(5).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(6).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(7).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(clothesList.get(8).getLink());
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
