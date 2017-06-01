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

import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText editTextUser;
    EditText editTextPassword;
    Button buttonLogin;
    TextView singUp;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = (EditText) findViewById(R.id.userLogin);
        editTextPassword = (EditText) findViewById(R.id.passwordLogin);
        buttonLogin = (Button) findViewById(R.id.loginButton);
        db = DBHelper.getInstance(this);
        singUp=(TextView)findViewById(R.id.loginSingUpTv);
        buttonLogin.setOnClickListener(this);
        singUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            String name = editTextUser.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(this, "Please enter a user name ", Toast.LENGTH_SHORT).show();
                return;
            }
            String password = editTextPassword.getText().toString().trim();
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter a password ", Toast.LENGTH_SHORT).show();
                return;
            }

            List<User> listaUser=db.getAllUsers();
            User user=new User(0,name,password);
            if(listaUser.contains(user)){
                Intent intent=new Intent(Login.this,MainActivity.class);
                intent.putExtra("EXTRA_NAME", user.getName());
                startActivity(intent);
            }
            else{
                Toast.makeText(this,"There is no user with this credentials ", Toast.LENGTH_SHORT).show();
                return;
            }

        }
        if(v==singUp){
            Intent intent=new Intent(Login.this,SingUp.class);
            startActivity(intent);
        }
    }

}
