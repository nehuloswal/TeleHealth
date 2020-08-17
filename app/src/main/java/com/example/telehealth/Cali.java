package com.example.telehealth;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cali extends AppCompatActivity {

    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebasefirestore;
    private FirestoreRecyclerAdapter adapter;
    Spinner spinslots;
    private Button cBookbutton;
    private EditText ipDocID;
    private String tdref = "";
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cali);
        mFirestoreList = findViewById(R.id.firestore_list);
        firebasefirestore = FirebaseFirestore.getInstance();
        spinslots = (Spinner) findViewById(R.id.spinnercali);
        cBookbutton = (Button)findViewById(R.id.cbookbutton);
        ipDocID = (EditText)findViewById(R.id.editTextcDocid);
        Query query = firebasefirestore.collection("/Doctors/California/Branch");
        FirestoreRecyclerOptions<ModelCAL> options = new FirestoreRecyclerOptions.Builder<ModelCAL>()
                .setQuery(query,ModelCAL.class).build();
        adapter = new FirestoreRecyclerAdapter<ModelCAL, ProductsViewHolder>(options) {
            @NonNull
            @Override
            public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ny,parent,false);
                return new ProductsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductsViewHolder holder, int position, @NonNull ModelCAL model) {
                holder.list_name.setText(model.getName());
                holder.list_hospital.setText(model.getHospital());
                holder.list_ID.setText(model.getID()+"");
                //     holder.test.equals(model.getSlot());
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);


        cBookbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spislot = spinslots.getSelectedItem().toString();
                Boolean s1,s2,s3,s4,s5,s6;
                String DocID = ipDocID.getText().toString();

                if(DocID.equals("1")){
                    tdref = tdref +"NFqeqIxZBnC3n5edA6Bu";
                }else if(DocID.equals("2")){
                    tdref = tdref+"jxoHnV8Alsw7D6O1vWH8";
                }else{
                    Toast.makeText(getApplicationContext(), "Enter Valid Doc ID",Toast.LENGTH_LONG).show();
                    return;
                }//toast message exit*/
             /*   DocumentReference docRef = firebasefirestore.collection("/Doctors/California/Branch").document(tdref);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });*/

                firebasefirestore.collection("/Doctors/California/Branch").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                list.add(document.getId());
                            }
                            Log.d("TAGER", list.toString());
                        } else {
                            Log.d("TAGER", "Error getting documents: ", task.getException());
                        }
                    }
                });
                Log.d("TAGERing", list.toString());
                //tdref = tdref+ list.get(Integer.parseInt(DocID)-1);
                DocumentReference docRef = firebasefirestore.collection("/Doctors/California/Branch").document(tdref);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("TAG", "No such document");
                            }
                            String temp = String.valueOf(document.getData());
                            Log.d("TAG123", temp);
                        } else {
                            Log.d("TAG", "get failed with ", task.getException());
                        }
                    }
                });
                if(DocID.equals("1")||DocID.equals("2")) {
                    if (spislot.equals("10:00 - 11:00")) {
                        Toast.makeText(getApplicationContext(), "Slot is not available", Toast.LENGTH_LONG).show();
                    } else if (spislot.equals("9:00 - 10:00")) {
                        Toast.makeText(getApplicationContext(), "Slot is not available", Toast.LENGTH_LONG).show();
                    } else if (spislot.equals("11:00 - 12:00")) {
                        Toast.makeText(getApplicationContext(), "Slot is not available", Toast.LENGTH_LONG).show();
                    } else if (spislot.equals("12:00 - 13:00")) {
                        Toast.makeText(getApplicationContext(), "Slot is not available", Toast.LENGTH_LONG).show();
                    } else if (spislot.equals("15:00 - 16:00")) {
                        Toast.makeText(getApplicationContext(), "Slot is not available", Toast.LENGTH_LONG).show();
                    } else if (spislot.equals("16:00 - 17:00")) {
                        Toast.makeText(getApplicationContext(), "Slot is not available", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    private class ProductsViewHolder extends RecyclerView.ViewHolder{
        private TextView list_name;
        private TextView list_ID;
        private TextView list_hospital;
        //private Map<String, Boolean> test;
        public ProductsViewHolder(@NonNull View itemView) {
            super(itemView);
            list_name = itemView.findViewById(R.id.list_name);
            list_ID = itemView.findViewById((R.id.list_ID));
            list_hospital = itemView.findViewById((R.id.list_hospital));

        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}
