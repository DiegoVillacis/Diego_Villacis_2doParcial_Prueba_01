package com.deitel.diego_villacis_2doparcial_prueba_01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mRootReference;
    private Button mBotonSubirDatos;
    private  Button buttonLogin;
    private EditText editTextDatosUsuarios;
    private  EditText editTextMail,editTextPassword;
    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootReference = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        //mBotonSelect = findViewById(R.id.button_Select);
        // editTextDatosUsuarios= findViewById(R.id.edtiText_Datos);
        editTextMail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);

        buttonLogin = findViewById(R.id.buttonLogin);

        mAuthStateListener  = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(MainActivity.this,HomeMainActivity.class);
                    intent.putExtra("mail",editTextMail.getText().toString());
                    startActivity(intent);

                } else
                {
                    Toast.makeText(MainActivity.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        };
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                signIn();
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        mAuth.addAuthStateListener(mAuthStateListener);
    }


    private void signIn() {
        String email= editTextMail.getText().toString();
        String password = editTextPassword.getText().toString();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
        // [END sign_in_with_email]
    }




}