package com.example.firebasedatabase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class My_view_holder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView textView;
    public My_view_holder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.image_single_item);
        textView = itemView.findViewById(R.id.image_name_single_item);
    }
}
