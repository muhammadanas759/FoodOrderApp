package com.example.android.foodorderingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

        private TextView mSignUp, mforget;
        private Button mSignIn;
        private EditText mPassword;
        private EditText mEmail;

        private String mStringEmail;
        private String mStringPassword;
        private String mUserID;

        private FirebaseAuth mFirebaseAuth;
        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildEventListener;
        public static SharedPreferences mSharedPrefrences;
        public static SharedPreferences.Editor mEditor;
        private ProgressBar mProgressBar;
        private LinearLayout mLinearLayout;
        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sign_in);
            mEmail = (EditText) findViewById(R.id.email);
            mPassword = (EditText) findViewById(R.id.password);
            mEmail.setText("a@gmail.com");
            mPassword.setText("123456");
            mFirebaseAuth = FirebaseAuth.getInstance();
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference("User");

            mSharedPrefrences = getPreferences(MODE_PRIVATE);
            mEditor = mSharedPrefrences.edit();
            mforget = (TextView) findViewById(R.id.forgetPassword);
            mforget.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    forgetPassword();
                }
            });

            mSignUp = (TextView) findViewById(R.id.register);
            mSignUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("message:", "onCreate: signIN");
                    startActivity(new Intent(getApplicationContext(), SignUp.class)
                    );
                    finish();
                }
            });
            mSignIn = (Button) findViewById(R.id.sign_in);
            mSignIn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("message:", "onCreate: signIN");

                    Toast.makeText(SignIn.this, "sign called", Toast.LENGTH_SHORT).show();
                    loginUser();
                }
            });
        }

        private void loginUser () {
            mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            mLinearLayout = (LinearLayout) findViewById(R.id.layout);

            mStringEmail = mEmail.getText().toString().trim();
            mStringPassword = mPassword.getText().toString().trim();

            boolean chk1 = false, chk2 = false;


            if (mStringEmail.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(mStringEmail).matches()) {
                mEmail.setError("Enter your Email!");
                mEmail.requestFocus();


            } else {
                chk1 = !chk1;
            }
            if (mStringPassword.isEmpty() || mStringPassword.length() < 6) {
                mPassword.setError("Enter your Password!");
                mPassword.requestFocus();
            } else {
                chk2 = !chk2;
            }
            if (chk1 && chk2) {
                Log.d("attached", "loginUser: ");
                mFirebaseAuth.signInWithEmailAndPassword(mStringEmail, mStringPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Toast.makeText(SignIn.this, "in checked", Toast.LENGTH_SHORT).show();
                            attachListener();

                        } else {
                            Toast.makeText(SignIn.this, "No User Found", Toast.LENGTH_SHORT).show();
                        }
//
//                startActivity(
//                        new Intent (
//                                getApplicationContext(),
//                                ParkingPlaces.class
//                        )
//                );
//                finish();
                    }
                });
            }
        }

        private void attachListener () {

            Toast.makeText(getApplicationContext(), "Login Successfull!", Toast.LENGTH_LONG).show();


//            mUserID="ZXXCwJcqSfX6KgqrtMKRZPOpwNg1";
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    mUserID = mFirebaseAuth.getUid();
                    for (DataSnapshot snap : dataSnapshot.getChildren()) {

                        if (snap.getKey().toString().equals(mUserID)) {

                            Customer currentUser = snap.getValue(Customer.class);

                            Toast.makeText(getApplicationContext(), "Login Successfull!", Toast.LENGTH_LONG).show();
                             Intent homeintent = new Intent(getApplicationContext(), Home.class);
                            homeintent.putExtra("name", currentUser.getName());
                            homeintent.putExtra("email", currentUser.getEmail());
                            startActivity(homeintent);
                            finish();


                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//
//            mDatabaseReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for (DataSnapshot snap:snapshot.getChildren())
//                        if(snap.getKey().toString().equals(mUserID)) {
//
//                            User currentUser = snap.getValue(User.class);
//
//                            Toast.makeText(getApplicationContext(), "Login Successfull!", Toast.LENGTH_SHORT).show();
//                            Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
//                            intent.putExtra("name",currentUser.getName());
//                            intent.putExtra("email",currentUser.getEmail());
//                            startActivity(intent);
//                            finish();
//
//
//                        }
//                }
//
//                @Override
//                public void onCancelled(FirebaseError firebaseError) {
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
        }
        public void forgetPassword () {
            AlertDialog.Builder alert = new AlertDialog.Builder(getApplicationContext());
            alert.setTitle("Forget Password");
            LayoutInflater inflater = getLayoutInflater();
            final View forgetLayout = inflater.inflate(R.layout.forgetpassword, null);
            alert.setView(R.layout.forgetpassword);
            alert.setIcon(R.drawable.hrlogo);

            alert.setMessage("Write your primary Email and check your Email");

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            Button submitbutton = forgetLayout.findViewById(R.id.submitbutton);
            submitbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editTextpassword = forgetLayout.findViewById(R.id.forgetpass);
                    String password = editTextpassword.getText().toString();

                    Toast.makeText(getApplicationContext(), "pass:" + password, Toast.LENGTH_SHORT).show();
                }

            });

            alert.show();
        }


    }

