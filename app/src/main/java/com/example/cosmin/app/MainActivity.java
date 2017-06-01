package com.example.cosmin.app;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.cosmin.app.Adapter.UserAdapter;
import com.example.cosmin.app.Model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DBHelper db;
    private TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db=DBHelper.getInstance(this);
        user=(TextView)findViewById(R.id.user);
        List<User> listaUser=db.getAllUsers();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            user.setText(extras.getString("EXTRA_NAME"));
        }

        adapter=new UserAdapter(listaUser,this);
        recyclerView.setAdapter(adapter);
    }
}
