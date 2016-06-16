package com.example.tanuj.quizzer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    Firebase mref;
    TextView q;
    TextView w;
    TextView x;
    TextView y;
    TextView z;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://popping-inferno-1330.firebaseio.com/quizlist");
        q = (TextView) findViewById(R.id.question);
        w = (TextView) findViewById(R.id.tvw);
        x = (TextView) findViewById(R.id.tvx);
        y = (TextView) findViewById(R.id.tvy);
        z = (TextView) findViewById(R.id.tvz);
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList o = (ArrayList) snapshot.getValue();
                HashMap<String,String> h = (HashMap<String,String>) o.get(0);
                q.setText(h.get("question"));
                w.setText(h.get("w"));
                x.setText(h.get("x"));
                y.setText(h.get("y"));
                z.setText(h.get("z"));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
