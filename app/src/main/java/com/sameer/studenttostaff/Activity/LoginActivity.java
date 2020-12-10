package com.sameer.studenttostaff.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sameer.studenttostaff.R;
//import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mLoginEmail;
    private TextView mLoginPassword;
    private Button mLogin_btn;

    private TextView RecoverPass;


    private DatabaseReference mUSERDATABASE;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        try{
            getSupportActionBar().setTitle("Login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException n){
            Toast.makeText(LoginActivity.this,"Unable to set title...",Toast.LENGTH_SHORT).show();
        }


        mUSERDATABASE = FirebaseDatabase.getInstance().getReference().child("Users");

        mLoginEmail = (TextView)findViewById(R.id.login_email);
        mLoginPassword = (TextView) findViewById(R.id.login_password);

        RecoverPass = (TextView)findViewById(R.id.forgot_password);

        RecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final EditText resetMail = new EditText(v.getContext());
               AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
               passwordResetDialog.setTitle("Reset Password ?");
               passwordResetDialog.setMessage("Enter Your Mail To Reset Password.");

               passwordResetDialog.setView(resetMail);
               passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       //Extract email send reset link

                       String mail = resetMail.getText().toString().trim();
                       mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {

                               Toast.makeText(LoginActivity.this, "Reset Link Sent To Your Mail. ", Toast.LENGTH_SHORT).show();

                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {

                               Toast.makeText(LoginActivity.this, "Error! Reset Link Is Not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show();

                           }
                       });

                   }
               });

               passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });

               passwordResetDialog.create().show();
            }
        });




        mLogin_btn = (Button) findViewById(R.id.login_btn);
        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mLoginEmail.getText().toString();
                String password = mLoginPassword.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
                    if(email.endsWith("vit.edu")){
                        loginUser(email,password);

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Please Login with vit.edu Email Address ", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            private void loginUser(String email, String password) {

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        try{
                            if (task.isSuccessful()){

                                String current_user_id = mAuth.getCurrentUser().getUid();
                                String deviceToken = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
                                mProgressDialog = new ProgressDialog(LoginActivity.this);
                                mProgressDialog.setTitle("Login......");
                                mProgressDialog.setMessage("Please wait,Login is in progress");
                                //mProgressDialog.setCanceledOnTouchOutside(false);
                                mProgressDialog.show();

                                mUSERDATABASE.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                    }
                                });

                            }

                            else {
                                mProgressDialog.dismiss();
                                Toast.makeText(LoginActivity.this,"Cannot Sign in.Please check the form and try again",Toast.LENGTH_LONG).show();
                            }

                        }
                        catch (NullPointerException n){
                            Toast.makeText(LoginActivity.this,"Unable to get current user..",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
            }

        });
    }

   /* private void ShowRecoverPasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover Title");

        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        final EditText emailEt = new EditText(this);
        emailEt.setHint("Email");
        emailEt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEt.setMinEms(10);

        constraintLayout.addView(emailEt);
        constraintLayout.setPadding(10,10,10,10);
        builder.setView(constraintLayout);

        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String email = emailEt.getText().toString().trim();
                beginRecover(email);


            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        builder.create().show();
    }

    */

   /* private void beginRecover(String email) {
        mProgressDialog.setMessage("Sending Email....");
        mProgressDialog.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mProgressDialog.dismiss();

                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    */
}
