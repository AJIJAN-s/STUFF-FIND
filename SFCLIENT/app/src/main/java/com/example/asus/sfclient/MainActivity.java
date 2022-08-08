package com.example.asus.sfclient;


import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText isi, isi2;
    //TextView la;
    ListView li;
    List<Isidata> isidatabarang;
    ImageView im;
    LinearLayout at;
    int h;

    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myRef = FirebaseDatabase.getInstance().getReference("isidatabarang");

        li = (ListView) findViewById(R.id.dlist);
        isi = (EditText) findViewById(R.id.li1);
//        isi2 = (EditText) findViewById(R.id.li2);
        im = (ImageView) findViewById(R.id.img);
        at = (LinearLayout) findViewById(R.id.atas);

        isidatabarang = new ArrayList<>();

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //isi.clearFocus();
        //isi.setFocusableInTouchMode(false);
        //isi.setFocusable(false);
        //isi.setFocusableInTouchMode(true);
        //isi.setFocusable(true);

//        at.post(new Runnable(){
//            public void run(){
//                h = at.getHeight();
//                if (h < 500){
//                    im.setImageDrawable(null);
//                    Toast.makeText(getApplicationContext(), "kurang", Toast.LENGTH_LONG).show();
//                }
//                else {
//                    im.setBackgroundResource(R.mipmap.stuff);
//                    Toast.makeText(getApplicationContext(), "banyak", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        isi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                im.setBackgroundResource(android.R.color.transparent);
            }
        });

//        isi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//                if (hasFocus) {
//                    im.setImageDrawable(null);
//                    //im.setBackgroundResource(R.mipmap.stuff);
//                    //Toast.makeText(getApplicationContext(), "Got the focus", Toast.LENGTH_LONG).show();
//                } else {
//                    im.setBackgroundResource(R.mipmap.stuff);
//                    //Toast.makeText(getApplicationContext(), "Lost the focus", Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        isi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                im.setImageDrawable(null);

                isi = (EditText) findViewById(R.id.li1);
                String kata = isi.getText().toString();

                myRef.orderByChild("namaBarang").startAt(kata).endAt(kata + "\uf8ff").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //clearing the previous Stuff list
                        isidatabarang.clear();

                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting Stuff
                            Isidata brg = postSnapshot.getValue(Isidata.class);
                            //adding Stuff to the list
                            isidatabarang.add(brg);
                        }

                        //creating adapter
                        DaftarBarang brgAdapter = new DaftarBarang(MainActivity.this, isidatabarang);
                        //attaching adapter to the listview
                        li.setAdapter(brgAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {
                im.setBackgroundResource(0);
            }
        });

        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Isidata brg = isidatabarang.get(i);
                showDialog(brg.getIdBarang(), brg.getNamaBarang(), brg.getHargaBarang(), brg.getStatusBarang(), brg.getLokasiBarang());
                //return true;
            }
        });
    }

    private void showDialog(final String idBarang, String namaBarang, String hargaBarang, String statusBarang, String lokasiBarang) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.show_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextNama = (EditText) dialogView.findViewById(R.id.editTextName);
        final EditText editTextHarga = (EditText) dialogView.findViewById(R.id.editTextSuBName);
        final EditText editTextStatus = (EditText) dialogView.findViewById(R.id.editTextSuBName2);
        final EditText editTextLokasi = (EditText) dialogView.findViewById(R.id.editTextSuBName3);

        editTextNama.setText(namaBarang);
        editTextHarga.setText("Rp. "+hargaBarang);
        editTextStatus.setText(statusBarang);
        editTextLokasi.setText(lokasiBarang);

        String s = new String(namaBarang);
        dialogBuilder.setTitle("DETAIL BARANG "+s.toUpperCase());
        final AlertDialog b = dialogBuilder.create();
        b.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous Stuff list
                isidatabarang.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Stuff
                    Isidata brg = postSnapshot.getValue(Isidata.class);
                    //adding Stuff to the list
                    isidatabarang.add(brg);
                }

                //creating adapter
                DaftarBarang brgAdapter = new DaftarBarang(MainActivity.this, isidatabarang);
                //attaching adapter to the listview
                li.setAdapter(brgAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cari(View view) {
        View viewview = this.getCurrentFocus();
        if (viewview !=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(viewview.getWindowToken(), 0);
        }
        im.setBackgroundResource(R.mipmap.stuff);

        isi = (EditText) findViewById(R.id.li1);
        String kata = isi.getText().toString();

//        Query search = myRef.orderByChild("StuffName").startAt(kata).endAt(kata + "\uf8ff");

        myRef.orderByChild("namaBarang").equalTo(kata).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous Stuff list
                isidatabarang.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting Stuff
                    Isidata brg = postSnapshot.getValue(Isidata.class);
                    //adding Stuff to the list
                    isidatabarang.add(brg);
                }

                //creating adapter
                DaftarBarang brgAdapter = new DaftarBarang(MainActivity.this, isidatabarang);
                //attaching adapter to the listview
                li.setAdapter(brgAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
