package com.mobileapp.a4cast;

import android.content.Intent;
import android.net.Uri;
import android.util.Dumpable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>{
    private List<ModelClass> userList;
    private int recommendSwitch = 0; //0 = default | 1 = clothes | 2 = activity | 3 = food
    public RecyclerViewAdaptor(List<ModelClass>userList, int intSwitch) {
        recommendSwitch = intSwitch;
        this.userList = userList;
    }
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (recommendSwitch) {
            case 1: //CLOTHES
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_recyclerview, parent, false);
                break;
            case 2: //ACTIVITY
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerview, parent, false);
                break;
            case 3: //FOOD
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_recyclerview, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_recyclerview, parent, false);
                break;
        }
        Log.d("DEBUG", "TEST RECYCLER: SWITCH:" + recommendSwitch);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //Get name and picture for all items
        String link;
        int resource = userList.get(position).getImageview1();
        String name = userList.get(position).getTextview1();

        //Get extra data based on recyclerView
        switch (recommendSwitch) {
            case 1: //CLOTHES
                link = userList.get(position).getItemLink();
                holder.setDataClothes(resource,name,link);
                break;
            case 2: //ACTIVITY
                link = userList.get(position).getItemLink();
                holder.setDataActivity(resource,name,link);
                break;
            case 3: //FOOD
                String recipe = userList.get(position).getItemRecipe(); //getItemRecipe in ModelClass.java
                holder.setDataFood(resource,name,recipe);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView, testView;
        private ImageView imageView;
        private String itemLink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //All Views
            imageView = itemView.findViewById(R.id.itemImage);
            textView= itemView.findViewById(R.id.itemNameTextView);

            /*
            This switch statement is here to initialize any UI elements that are unique to that recycler view
            In the Activity and Clothes fragments, there are shop buttons while in the food fragment there is a recipe textView.
             */
            switch (recommendSwitch) {
                case 1: //CLOTHES
                case 2: //ACTIVITY
                    itemView.findViewById(R.id.shopButton).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Uri uri = Uri.parse(itemLink);
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            v.getContext().startActivity(intent);
                        }
                    });
                    break;
                case 3: //FOOD
                    testView = itemView.findViewById(R.id.testTextView);
                    break;

            }
        }

        /*
        These are for each of the recyclerViews and setting the data of each
         */
        public void setDataClothes(int resource, String name, String link) {
            imageView.setImageResource(resource);
            textView.setText(name);
            itemLink = link;
        }
        public void setDataActivity(int resource, String name, String link) {
            imageView.setImageResource(resource);
            textView.setText(name);
            itemLink = link;
            Log.d("DEBUG", "TEST RECYCLER:" + link + " | " + name);
        }
        public void setDataFood(int resource, String name, String recipe) {
            imageView.setImageResource(resource);
            textView.setText(name);
            try {
                testView.setText(recipe);
            } catch (Exception e) {
                Log.d("DEBUG", "EXCEPTION:" + e);
            }
        }
    }
}
