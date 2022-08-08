package com.example.asus.sfadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Main2Activity extends AppCompatActivity {

    EditText isi1, isi2, isi3, isi4;
    DatabaseReference myRef;
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        myRef = FirebaseDatabase.getInstance().getReference("isidatabarang");

        isi1 = (EditText) findViewById(R.id.li1);
        isi2 = (EditText) findViewById(R.id.li2);
        isi3 = (EditText) findViewById(R.id.li3);
        isi4 = (EditText) findViewById(R.id.li4);
        bt = (Button) findViewById(R.id.insert);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert();
            }
        });
    }

    public void insert() {
        String a = isi1.getText().toString().trim();
        String b = isi2.getText().toString();
        String c = isi3.getText().toString();
        String d = isi4.getText().toString();

        if (!TextUtils.isEmpty(a) || !TextUtils.isEmpty(b) || !TextUtils.isEmpty(c) || !TextUtils.isEmpty(d)){
            String id = myRef.push().getKey();
            Isidata isda = new Isidata(id, a, b, c, d);
            myRef.child(id).setValue(isda);
            isi1.setText("");
            isi2.setText("");
            isi3.setText("");
            isi4.setText("");
            Toast.makeText(this, "Berhasil ditambah", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Isi Dulu", Toast.LENGTH_LONG).show();
        }
    }
}
