package com.example.telehealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeScreen extends AppCompatActivity {

    Button logout;
    Button profile;
    CircleImageView profile_pic;
    Uri photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        logout = findViewById(R.id.logout);
        profile = findViewById(R.id.Profile);
        profile_pic = findViewById(R.id.profile_image);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            photoUrl = user.getPhotoUrl();
            if(photoUrl != null)
                Picasso.with(getApplicationContext()).load(photoUrl).into(profile_pic);
        }

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 3);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent InttoMain = new Intent(HomeScreen.this, MainActivity.class);
                startActivity(InttoMain);
                //finish();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent InttoProfile = new Intent(HomeScreen.this, Profilepage.class);
                startActivity(InttoProfile);
                //finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode == 3){
                if (data != null) {
                    Uri contentURI = data.getData();
                    try {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                        //String path = saveImage(bitmap);
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(contentURI).build();
                        user.updateProfile(profileUpdates);
                        Toast.makeText(getApplicationContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
                        Picasso.with(getApplicationContext()).load(contentURI).into(profile_pic);
                        //profile_pic.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
}

