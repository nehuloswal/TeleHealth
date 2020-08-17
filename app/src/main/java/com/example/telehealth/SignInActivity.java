package com.example.telehealth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignInActivity extends AppCompatActivity {

    EditText emailId, password;
    Button SignIn;
    TextView signup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.S_Email);
        password = findViewById(R.id.S_Password);
        SignIn = findViewById(R.id.S_SignIn);
        signup = findViewById(R.id.S_SignUp);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(SignInActivity.this, "You are logged In", Toast.LENGTH_SHORT).show();
                    //Intent i = new Intent(SignInActivity.this, HomeScreen.class);
                    //startActivity(i);
                }
                else{
                    Toast.makeText(SignInActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };

        SignIn.setOnClickListener(new View.OnClickListener() {
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
                    mFirebaseAuth.signInWithEmailAndPassword(eml, pwd).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignInActivity.this, "Please try Again", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(mFirebaseAuth.getCurrentUser().isEmailVerified()) {
                                    Intent itohome = new Intent(SignInActivity.this, HomeScreen.class);
                                    startActivity(itohome);
                                }
                                else
                                    Toast.makeText(SignInActivity.this, "Please Verify Your Email Address", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(SignInActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intsignup = new Intent(SignInActivity.this, MainActivity.class);
                startActivity(intsignup);
                finish();
            }
        });
    }

   @Override
    protected void onStart(){
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
