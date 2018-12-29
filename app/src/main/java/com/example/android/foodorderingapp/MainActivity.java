package com.example.android.foodorderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
Button btnsignin,btnsignup;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnsignup=findViewById(R.id.signup);
        btnsignin=findViewById(R.id.signin);
//        textView=findViewById(R.id.textView);
//        Typeface face=Typeface.createFromAsset(getAssets(),"Strato-linked.ttf");
//        textView.setTypeface(face);
    btnsignup.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signup_intent=new Intent(MainActivity.this,SignUp.class);
            startActivity(signup_intent);
        }
    });
    btnsignin.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signin_intent=new Intent(MainActivity.this,SignIn.class);
            startActivity(signin_intent);

        }
    });
    }
}
