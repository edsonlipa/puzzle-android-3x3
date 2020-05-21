package com.example.hospital.ui.share;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;

public class ShareFragment extends Fragment implements Hopital_dialog.Hospital_dialog_OnCreate{
    SQLconectionHelper conn;
    SearchView buscar;
    ArrayAdapter<CharSequence> adaptador;
    ListView mi_lista;
    Button add_h;
    Hopital_dialog diag;
    private ShareViewModel shareViewModel;
    public ArrayList<String> Lista_hospitales;
    private View root;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shareViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);
        root = inflater.inflate(R.layout.fragment_share, container, false);

        mi_lista = root.findViewById(R.id.lista_hospitales);

        diag = new Hopital_dialog();
            diag.setListener(this);
        add_h = root.findViewById(R.id.add_hospital);
        add_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag.show(getFragmentManager() ,"gialog hospital");
            }
        });

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);

        load_hospitale("");


        buscar = root.findViewById(R.id.buscar_hospitales);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar.setIconified(false);
            }
        });

        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                load_hospitale(query);
                adaptador = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1,Lista_hospitales);
                mi_lista.setAdapter(adaptador);

                Toast.makeText(root.getContext(),
                        "buscar "+ query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                load_hospitale(newText);
                adaptador = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1,Lista_hospitales);
                mi_lista.setAdapter(adaptador);
                return false;
            }
        });

        return root;
    }
    private void load_hospitale(String palabra) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Lista_hospitales = new ArrayList<String>();
        //Lista_hospitales.add("Seleccione");
        palabra = "\'%%"+palabra+"%%\'";
        Cursor cursor = db.rawQuery(Utilidades.SQL_search_hospital+palabra, null);
        while(cursor.moveToNext()){
            Integer ident =cursor.getInt(0);
            String idents = ident.toString();
            String nameh= cursor.getString(1);
            Log.i("Nombre", idents );
            Log.i("ID", nameh);
            Lista_hospitales.add(nameh);
        }
        adaptador = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1,Lista_hospitales);
        mi_lista.setAdapter(adaptador);
    }

    @Override
    public void applyTests() {
        Log.i("APLLLY", "applyTests: ");
        load_hospitale("");
    }
}