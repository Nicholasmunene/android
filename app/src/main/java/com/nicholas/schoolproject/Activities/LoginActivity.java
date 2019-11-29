package com.nicholas.schoolproject.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nicholas.schoolproject.R;

public class LoginActivity extends AppCompatActivity {

    private EditText usermail , userpassword;

    private Button btnlogin;
    private ProgressBar loadingprogressbar;
    private Intent HomeActivity;
    private ImageView loginPhoto;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //find views

        usermail = findViewById(R.id.log_mail);
        userpassword = findViewById(R.id.log_password);
        btnlogin = findViewById(R.id.btn_log);
        loadingprogressbar = findViewById(R.id.log_progressbar);
        loginPhoto = findViewById(R.id.log_img);


        HomeActivity= new Intent(this, com.nicholas.schoolproject.Activities.HomeActivity.class);

        mAuth=FirebaseAuth.getInstance();

loginPhoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent registerActivity = new Intent( getApplicationContext(),RegisterActivity.class);

        startActivity(registerActivity);
        finish();


    }
});

        loadingprogressbar.setVisibility(View.INVISIBLE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadingprogressbar.setVisibility(View.VISIBLE);
                btnlogin.setVisibility(View.INVISIBLE);

                final  String email=usermail.getText().toString();
                final String password  = userpassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()){

                    showMessage("Please verify all fields !!!");
                    btnlogin.setVisibility(View.VISIBLE);
                    loadingprogressbar.setVisibility(View.INVISIBLE);



                }else{


                    signIn(email,password);



                }




            }
        });
    }

    private void signIn(String email, String password) {


mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {

        if (task.isSuccessful()){

            loadingprogressbar.setVisibility(View.INVISIBLE);
            btnlogin.setVisibility(View.VISIBLE);

            upDateUI();
        }else{

            showMessage(task.getException().getMessage());
            btnlogin.setVisibility(View.VISIBLE);
            loadingprogressbar.setVisibility(View.INVISIBLE);
        }


    }
});

    }

    private void upDateUI() {

            startActivity(HomeActivity);
            finish();

    }


    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();


        if (user !=null){


            //user already connected so we need to redirect him to the homPage

            upDateUI();
        }
    }
}
