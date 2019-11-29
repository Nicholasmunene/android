package com.nicholas.schoolproject.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nicholas.schoolproject.R;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private CardView profilecard,productscard,accountcard,bidcard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //defining cards

        profilecard = (CardView) findViewById(R.id.profile_card);
        productscard = (CardView) findViewById(R.id.products_card);
        accountcard = (CardView) findViewById(R.id.account_card);
        bidcard = (CardView) findViewById(R.id.bid_card);

        //adding onclick listeners

        profilecard.setOnClickListener(this);
        productscard.setOnClickListener(this);
        accountcard.setOnClickListener(this);
        bidcard.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Intent i = null;

        switch (v.getId()){

            case R.id.profile_card : i = new Intent(this,ProfileActivity.class);startActivity(i);break;
            case R.id.products_card: i = new Intent(this,ProductsActivity.class);startActivity(i);break;
            case R.id.account_card: i = new Intent(this,AccountActivity.class);startActivity(i);break;
            case R.id.bid_card: i = new Intent(this,BidActivity.class);startActivity(i);break;
            default:break;

        }

    }
}
