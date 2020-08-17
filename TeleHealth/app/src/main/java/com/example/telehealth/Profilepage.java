package com.example.telehealth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


class User_info{
    public String Name;
    public String Address;
    public String Age;
    public String Weight;
    public String Height;
    public String Diseases;
}


public class Profilepage extends AppCompatActivity {

    EditText Name, Address, Age, Weight, Height, Diseases;
    Button Update;
    private FirebaseFirestore fstore;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    DocumentReference dref;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);

        Name = findViewById(R.id.name);
        Address = findViewById(R.id.address);
        Age = findViewById(R.id.age);
        Weight = findViewById(R.id.weight);
        Height = findViewById(R.id.height);
        Diseases = findViewById(R.id.diseases);
        Update = findViewById(R.id.update);
        fstore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        dref = fstore.collection("users").document(userID);
        dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User_info U_obj = documentSnapshot.toObject(User_info.class);
                Name.setText(U_obj.Name.toString());
                Address.setText(U_obj.Address.toString());
                Weight.setText(U_obj.Weight.toString());
                Height.setText(U_obj.Height.toString());
                Age.setText(U_obj.Age.toString());
                Diseases.setText(U_obj.Diseases.toString());
            }
        });



        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String U_Name = Name.getText().toString();
                String U_Age = Age.getText().toString();
                String U_Address = Address.getText().toString();
                String U_Weight = Weight.getText().toString();
                String U_Height = Height.getText().toString();
                String U_Diseases = Diseases.getText().toString();
                userID = mAuth.getCurrentUser().getUid();
                Map<String, Object> user = new HashMap<>();
                user.put("Name",U_Name);
                user.put("Address",U_Address);
                user.put("Age",U_Age);
                user.put("Weight",U_Weight);
                user.put("Height",U_Height);
                user.put("Diseases",U_Diseases);
                dref.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Profilepage.this, "Information Updated Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profilepage.this, "Error Updating Information", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
