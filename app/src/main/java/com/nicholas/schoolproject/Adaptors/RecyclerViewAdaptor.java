package com.nicholas.schoolproject.Adaptors;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nicholas.schoolproject.R;

public class RecyclerViewAdaptor extends RecyclerView.ViewHolder {
   public TextView txtTitle , txtCategory,txtDescription,txtPrices;
    public  ImageView img_thumbnail;


    public RecyclerViewAdaptor(@NonNull View itemView) {
        super(itemView);

        txtTitle=itemView.findViewById(R.id.anime_title);
        txtCategory=itemView.findViewById(R.id.categories);
        txtDescription=itemView.findViewById(R.id.description);
        txtPrices=itemView.findViewById(R.id.prices);
        img_thumbnail=itemView.findViewById(R.id.thumbnail);

    }
}
