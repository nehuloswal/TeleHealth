package com.example.telehealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText emailId, password;
    Button SignUp;
    TextView signin;
    FirebaseAuth mFirebaseAuth;
    private FirebaseFirestore fstore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        emailId = findViewById(R.id.email);
        password = findViewById(R.id.password);
        SignUp = findViewById(R.id.SignUp);
        signin = findViewById(R.id.SignIn);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eml = emailId.getText().toString();
                String pwd = password.getText().toString();

                if (eml.isEmpty()) {
                    emailId.setError("Please enter Email Id");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    password.setError("Please enter Password");
                    password.requestFocus();
                }

                else if (!(pwd.isEmpty() && eml.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(eml, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!(task.isSuccessful())){
                                Toast.makeText(MainActivity.this, "Sign Up Unsuccessful! Please Try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //startActivity(new Intent(MainActivity.this, HomeScreen.class));
                                mFirebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            userID = mFirebaseAuth.getCurrentUser().getUid();
                                            DocumentReference dref = fstore.collection("users").document(userID);
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("Name","");
                                            user.put("Address","");
                                            user.put("Age","0");
                                            user.put("Weight","0");
                                            user.put("Height","0");
                                            user.put("Diseases","");
                                            dref.set(user);
                                            Toast.makeText(MainActivity.this, "Please check email for verification.", Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(MainActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }
                    });


                }
                else{
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
