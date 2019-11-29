package com.nicholas.schoolproject.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.nicholas.schoolproject.R;

public class RegisterActivity extends AppCompatActivity {

    ImageView UserPhoto;
    static  int PReqCode = 1 ;
    static  int REQUESTCODE = 1 ;
    Uri pickedUriImage ;


    private EditText username , useremail , userpassword , userpassword2;
    private ProgressBar loadingprogressbar;
    private Button register ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //find views

        username =findViewById(R.id.edtname);
        useremail = findViewById(R.id.edtemail);
        userpassword = findViewById(R.id.edtpass1);
        userpassword2 = findViewById(R.id.edtpass2);
        register = findViewById(R.id.btnreg);
        loadingprogressbar= findViewById(R.id.progressBar);


        mAuth=FirebaseAuth.getInstance();

        loadingprogressbar.setVisibility(View.INVISIBLE);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                register.setVisibility(View.INVISIBLE);
                loadingprogressbar.setVisibility(View.VISIBLE);

                final String email = useremail.getText().toString().trim();
                final String name = username.getText().toString().trim();
                final String password =userpassword.getText().toString().trim();
                final  String password2 = userpassword2.getText().toString().trim();


                if (email.isEmpty() || name.isEmpty() || password.isEmpty() || !password.equals(password2)){

                    //something goes wrong
                    //we need to display message all fields must be filled

                    showMessage("All fields must be filled !!!");

                    register.setVisibility(View.VISIBLE);
                    loadingprogressbar.setVisibility(View.INVISIBLE);



                }else {

                    //everything is okay we need to create user account
                    // createUserAccount method will try to create user account if the email is valid


                    createUserAccount(email,name,password);

                }
            }
        });


        UserPhoto=findViewById(R.id.profileimg);

        UserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Build.VERSION.SDK_INT>22){

                    checkAndRequestForPermission();
                }else {

                    openGallery();


                }

            }
        });
    }

    private void createUserAccount(String email, final String name, String password) {



        ///this method will create user account using specific email and password



        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){

                    //Account successfully created

                    showMessage("Account created successfully");


                    //we need now to update user profile picture and name

                    updateUserInfo(name,pickedUriImage,mAuth.getCurrentUser());






                }else{
                    //Account creation failed

                    showMessage("Account creation failed "+task.getException());

                    register.setVisibility(View.VISIBLE);
                    loadingprogressbar.setVisibility(View.INVISIBLE);





                }

            }
        });

    }
//update user name and image
    private void updateUserInfo(final String name, Uri pickedUriImage, final FirebaseUser currentUser) {

        //we need to upload user photo to fireBase and get url


        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("Users_photos");
        final StorageReference ImageFilePath= mStorage.child(pickedUriImage.getLastPathSegment());



        ImageFilePath.putFile(pickedUriImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                //image uploaded successfully
                //now we can get image url



                ImageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    //uri contains user image url

                    UserProfileChangeRequest ProfileUpdate= new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(uri)
                            .build();

                    currentUser.updateProfile(ProfileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()){
                                //info update successful

                                showMessage("Update Successful");

                                UpdateUI();

                            }

                        }
                    });


                }
            });



            }
        });




    }

    private void UpdateUI() {

        Intent HomeActivity = new Intent(getApplicationContext(), com.nicholas.schoolproject.Activities.HomeActivity.class);
        startActivity(HomeActivity);
        finish();

    }


    //simple toasted message
    private void showMessage(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();

    }

    private void openGallery() {

        //TODO: open gallery intent and wait for user to pick photo

        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,REQUESTCODE);

    }




    private void checkAndRequestForPermission() {

        if (ContextCompat.checkSelfPermission(RegisterActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(RegisterActivity.this , Manifest.permission.READ_EXTERNAL_STORAGE)){

                Toast.makeText(this, "Please accept for required permissions ", Toast.LENGTH_SHORT).show();

            }else {
                ActivityCompat.requestPermissions(RegisterActivity.this,
                                                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                                                    PReqCode);


            }
        }else {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK || requestCode == REQUESTCODE && data != null ){

            ///user has successfully picked an image
            // we need to save the image to a uri variable

            pickedUriImage= data.getData();
            UserPhoto.setImageURI(pickedUriImage);

        }
    }
}
