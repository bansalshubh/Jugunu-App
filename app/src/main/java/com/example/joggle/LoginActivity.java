package com.example.joggle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    public FirebaseAuth auth;
    public int counter = 30;
    String Phone;
    String otpid;
    EditText phone;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Button loginbtn;
//    TextView createbtn;
    CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        phone = (EditText)findViewById(R.id.phone);
        ccp = findViewById(R.id.ccp);
        ccp.registerCarrierNumberEditText(phone);
        loginbtn = (Button) findViewById(R.id.loginbtn);
//        createbtn = (TextView) findViewById(R.id.createbtn);
//        createbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this,CreateAccount.class));
//            }
//        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Phone = ccp.getFullNumberWithPlus().trim();
//                Toast.makeText(LoginActivity.this, Phone, Toast.LENGTH_SHORT).show();
                if(Phone.isEmpty())
                    Toast.makeText(LoginActivity.this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
//                else if(Phone.length() != 12)
//                    Toast.makeText(LoginActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                else
                {
                    initiateotp();
                    final Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.setContentView(R.layout.verify_popup);
                    dialog.setCancelable(false);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    final EditText edotp = dialog.findViewById(R.id.edotp);
                    final TextView tv = dialog.findViewById(R.id.tv);
                    final TextView tev = dialog.findViewById(R.id.tv);
                    final ProgressBar pb = dialog.findViewById(R.id.pb);
                    Button otpbtn = dialog.findViewById(R.id.otpbtn);
                    new CountDownTimer(30000, 1000){
                        public void onTick(long millisUntilFinished){
                            tv.setText(String.valueOf(counter));
                            counter--;
                        }
                        public  void onFinish(){
                            tev.setVisibility(View.GONE);
                            tv.setText("Please Verify Again");
                            tv.setTextColor(Color.parseColor("#FF0000"));
                        }
                    }.start();
                    dialog.show();
                    dialog.setOnKeyListener(new Dialog.OnKeyListener() {

                        @Override
                        public boolean onKey(DialogInterface arg0, int keyCode,
                                             KeyEvent event) {
                            // TODO Auto-generated method stub
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                finish();
                                dialog.dismiss();
                            }
                            return true;
                        }
                    });
                    otpbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(edotp.getText().toString().isEmpty())
                                Toast.makeText(getApplicationContext(),"Blank Field can not be processed",Toast.LENGTH_LONG).show();
                            else if(edotp.getText().toString().length()!=6)
                                Toast.makeText(getApplicationContext(),"INvalid OTP",Toast.LENGTH_LONG).show();
                            else
                            {
                                pb.setVisibility(View.VISIBLE);
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                pb.setVisibility(View.GONE);
                                PhoneAuthCredential credential=PhoneAuthProvider.getCredential(otpid,edotp.getText().toString().trim());
                                signInWithPhoneAuthCredential(credential);
                            }
                        }
                    });
                }
//                    PhoneAuthProvider.getInstance().verifyPhoneNumber("91" + Phone, 60, TimeUnit.SECONDS, LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                        @Override
//                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                            signInUser(phoneAuthCredential);
//                        }
//
//                        @Override
//                        public void onVerificationFailed(@NonNull FirebaseException e) {
//                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//
//                        @Override
//                        public  void onCodeSent(@NonNull final String verificationId, PhoneAuthProvider.ForceResendingToken forceResendingToken)
//                        {
//                            super.onCodeSent(verificationId, forceResendingToken);
//                            Dialog dialog = new Dialog(LoginActivity.this);
//                            dialog.setContentView(R.layout.verify_popup);
//
//                            final EditText edotp = dialog.findViewById(R.id.edotp);
//                            Button otpbtn = dialog.findViewById(R.id.otpbtn);
//                            otpbtn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    String OTP = edotp.getText().toString();
//                                    if(verificationId.isEmpty())return;
//                                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,OTP);
//                                    signInUser(credential);
//                                }
//                            });
//                            dialog.show();
//                        }
//                    });
//                }
            }
        });
    }

    private void initiateotp()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                Phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        System.out.println(s);
                        otpid=s;
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "This is storing your information", Toast.LENGTH_SHORT).show();
                            firebaseDatabase = FirebaseDatabase.getInstance();
                            reference = firebaseDatabase.getReference("Users");
                            UserHelperClass helperClass = new UserHelperClass(null,null,Phone,null);
                            DbHandler handler = new DbHandler(getApplicationContext(),"userdb",null,1);
//                            if(handler.getData(Phone)==0) {
//                                Intent intent = new Intent(LoginActivity.this, indexActivity.class);
//                                intent.putExtra("message", Phone);
//                                startActivity(intent);
//                                finish();
//                            }
                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.hasChild(Phone)) {
                                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                        finish();
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(LoginActivity.this, indexActivity.class);
                                        intent.putExtra("message", Phone);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Signin Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        updateUI(currentUser);
//    }

//    public void signInUser(PhoneAuthCredential credential)
//    {
//        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    startActivity(new Intent(LoginActivity.this,indexActivity.class));
//                    finish();
//                }else {
////                    Log.d(TAG, "onComplete:"+task.getException().getLocalizedMessage());
//                    Toast.makeText(LoginActivity.this, "Task Failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}