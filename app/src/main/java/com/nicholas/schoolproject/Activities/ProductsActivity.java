package com.nicholas.schoolproject.Activities;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nicholas.schoolproject.Adaptors.RecyclerViewAdaptor;
import com.nicholas.schoolproject.Models.Anime;
import com.nicholas.schoolproject.R;

import java.util.ArrayList;



public class ProductsActivity extends AppCompatActivity {

    ArrayList<Anime> arrayList;
    FirebaseRecyclerOptions<Anime> options;
    FirebaseRecyclerAdapter<Anime,RecyclerViewAdaptor> adapter;
    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView recyclerView;

    @Override
    protected void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        adapter.stopListening();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        ///init

        recyclerView=findViewById(R.id.recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        arrayList=new ArrayList<Anime>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("products");


        options=new FirebaseRecyclerOptions.Builder<Anime>().setQuery(reference,Anime.class).build();
        adapter=new FirebaseRecyclerAdapter<Anime, RecyclerViewAdaptor>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewAdaptor holder, int position, @NonNull Anime model) {

                holder.txtPrices.setText(model.getPrice());
                holder.txtCategory.setText(model.getCategory());
                holder.txtDescription.setText(model.getDescription());
                holder.txtTitle.setText(model.getTitle());

                //glide image

                Glide.with(ProductsActivity.this).load(model.getImage()).into(holder.img_thumbnail);

            }

            @NonNull
            @Override
            public RecyclerViewAdaptor onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new RecyclerViewAdaptor(LayoutInflater.from(ProductsActivity.this).inflate(R.layout.anime_row_item,parent,false));
            }
        };

        recyclerView.setAdapter(adapter);





    }


}
