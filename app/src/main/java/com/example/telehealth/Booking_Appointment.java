package com.example.telehealth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Booking_Appointment extends AppCompatActivity {

    Button NewYork;
    Button California;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking__apoointment);
        NewYork = findViewById(R.id.NewYork);
        California = findViewById(R.id.California);
        NewYork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent NY = new Intent(Booking_Appointment.this, NewYork.class);
                startActivity(NY);
            }
        });
        California.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Cal = new Intent(Booking_Appointment.this, Cali.class);
                startActivity(Cal);
            }
        });
    }
};