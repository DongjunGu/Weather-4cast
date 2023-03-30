package com.mobileapp.a4cast;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.clothes_recyclerview, parent, false);
                break;
            case 2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerview, parent, false);
                break;
            case 3:
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

        int resource = userList.get(position).getImageview1();
        String name = userList.get(position).getTextview1();
        String link = userList.get(position).getItemLink();

        holder.setData(resource,name,link);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;
        private String itemLink;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.itemImage);
            textView= itemView.findViewById(R.id.itemNameTextView);

            itemView.findViewById(R.id.shopButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(itemLink);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    v.getContext().startActivity(intent);
                }
            });

        }

        public void setData(int resource, String name, String link) {
            imageView.setImageResource(resource);
            textView.setText(name);
            itemLink = link;
        }

    }
}
