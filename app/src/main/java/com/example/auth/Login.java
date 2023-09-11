package com.example.auth;

import static com.example.auth.URLs.ROOT_URL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Login extends AppCompatActivity{

    TextInputEditText textInputEditTextEmail, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;

    public static final String AES = "AES";
    private static final String TAG = "EncryptionPassword";
    String encryp_password;
    //https://www.youtube.com/watch?v=QwQuro7ekvc = Java Tutorial - Complete User Login and Registration Backend + Email Verification
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected() || !networkInfo.isAvailable()){
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.alart_dialog);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
            Button button = dialog.findViewById(R.id.btn_retry);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    recreate();
                }
            });

            dialog.show();
        }else {

            textInputEditTextEmail = findViewById(R.id.email);
            textInputEditTextPassword = findViewById(R.id.password);
            buttonLogin = findViewById(R.id.buttonLogin);
            textViewSignUp = findViewById(R.id.signupText);
            progressBar = findViewById(R.id.progress);

            textViewSignUp.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),SignUP.class);
                    startActivity(intent);
                    finish();
                }
            });
            buttonLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    final String email, password;
                    email = String.valueOf(textInputEditTextEmail.getText());
                    //password = String.valueOf(textInputEditTextPassword.getText());
                    try {
                        encryp_password = encrypt(textInputEditTextPassword.getText().toString(),textInputEditTextEmail.getText().toString());
                        textInputEditTextPassword.setText(encryp_password);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    password = encryp_password;
                    //System.out.println(password);

                    if( !email.equals("") && !password.equals("")) {
                        progressBar.setVisibility(View.VISIBLE);
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable(){
                            @Override
                            public void run() {
                                String[] field = new String[2];
                                field[0] = "email";
                                field[1] = "password";
                                String[] data = new String[2];
                                data[0] = email;
                                data[1] = password;
//                                PutData putData = new PutData(ROOT_URL+"login.php", "POST", field, data);
                                PutData putData = new PutData("https://kunals-resume.000webhostapp.com/PHP_auth/login.php", "POST", field, data);
                                if (putData.startPut()) {
                                    if (putData.onComplete()) {
                                        progressBar.setVisibility(View.GONE);
                                        String result = putData.getResult();
                                        if(result.equals("Login Success")){
                                            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(getApplicationContext(),"data not push", Toast.LENGTH_SHORT).show();
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),"You r offline", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"All fields required", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE,key);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }

}
