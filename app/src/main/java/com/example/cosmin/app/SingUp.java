package com.example.cosmin.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cosmin.app.Model.User;

public class SingUp extends AppCompatActivity implements View.OnClickListener{
    EditText editTextUser;
    EditText editTextPassword;
    Button buttonSingUp;
    TextView textViewLogin;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        editTextUser=(EditText)findViewById(R.id.userSingUp);
        editTextPassword=(EditText)findViewById(R.id.passwordSingUp);
        buttonSingUp=(Button)findViewById(R.id.singUpButton);
        textViewLogin=(TextView)findViewById(R.id.singUpLoginTV);

        db=DBHelper.getInstance(this);
        buttonSingUp.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==buttonSingUp){
            String name=editTextUser.getText().toString().trim();
            if(TextUtils.isEmpty(name)) {
                Toast.makeText(this,"Please enter a user name ", Toast.LENGTH_SHORT).show();
                return;
            }
            String password=editTextPassword.getText().toString().trim();
            if(TextUtils.isEmpty(password)) {
                Toast.makeText(this,"Please enter a password ", Toast.LENGTH_SHORT).show();
                return;
            }

            User user=new User(0,name,password);
            db.insertUser(user);
            Intent intent=new Intent(SingUp.this,MainActivity.class);
            intent.putExtra("EXTRA_NAME", user.getName());
            startActivity(intent);

        }
        if(v==textViewLogin){
            Intent intent=new Intent(SingUp.this,Login.class);
            startActivity(intent);
        }

    }
}
