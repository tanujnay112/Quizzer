package com.example.tanuj.quizzer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends Activity implements View.OnClickListener {

    Firebase mref;
    TextView q;
    Button w;
    Button x;
    Button y;
    Button z;
    char correct;
    LinearLayout corr;
    Button bNext;
    int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        corr = (LinearLayout) findViewById(R.id.correct);
        Firebase.setAndroidContext(this);
        mref = new Firebase("https://popping-inferno-1330.firebaseio.com/quizlist");
        q = (TextView) findViewById(R.id.question);
        w = (Button) findViewById(R.id.tvw);
        w.setOnClickListener(this);
        x = (Button) findViewById(R.id.tvx);
        x.setOnClickListener(this);
        y = (Button) findViewById(R.id.tvy);
        y.setOnClickListener(this);
        z = (Button) findViewById(R.id.tvz);
        z.setOnClickListener(this);
        bNext = (Button) findViewById(R.id.bNext);
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion();
                corr.setVisibility(View.GONE);
            }
        });
        mref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList o = (ArrayList) snapshot.getValue();
                int n = o.size()-1;
                int b = (int) Math.random()*n;
                if(current ==b) {
                    if(n!=0)
                    b = (b+1)%n;
                }
                current = b;
                HashMap<String,String> h = (HashMap<String,String>) o.get(b);
                q.setText(h.get("question"));
                w.setText("W) "+h.get("w"));
                x.setText("X) "+h.get("x"));
                y.setText("Y) "+h.get("y"));
                z.setText("Z) "+h.get("z"));
                correct = h.get("correct").charAt(0);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void nextQuestion() {
        mref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList o = (ArrayList) snapshot.getValue();
                int n = o.size()-1;
                int b =(int) Math.round(Math.random()*n);
                if(current ==b&&b!=0) {
                    b = (b+1)%n;
                }
                current = b;
                HashMap<String,String> h = (HashMap<String,String>) o.get(b);
                q.setText(h.get("question"));
                w.setText("W) "+h.get("w"));
                x.setText("X) "+h.get("x"));
                y.setText("Y) "+h.get("y"));
                z.setText("Z) "+h.get("z"));
                correct = h.get("correct").charAt(0);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
    Toast t = Toast.makeText(this,"Incorrect!",Toast.LENGTH_SHORT);
        if(getResources().getResourceEntryName(v.getId()).charAt(2)== (correct)){
            corr.setVisibility(View.VISIBLE);
        }else{
            t.show();
        }

    }
}
