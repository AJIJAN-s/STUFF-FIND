package com.example.asus.sfadmin;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class DaftarBarang extends ArrayAdapter<Isidata> {
    private Activity context;
    List<Isidata> barang;

    public DaftarBarang(Activity context, List<Isidata> barang) {
        super(context, R.layout.layout_daftar_barang, barang);
        this.context = context;
        this.barang = barang;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_daftar_barang, null, true);

        TextView textViewNama = (TextView) listViewItem.findViewById(R.id.textViewNama);
        TextView textViewHarga = (TextView) listViewItem.findViewById(R.id.textViewHarga);

        Isidata brg = barang.get(position);
        textViewNama.setText(brg.getNamaBarang());
        textViewHarga.setText("Rp. "+brg.getHargaBarang());

        return listViewItem;
    }
}
