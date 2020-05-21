package com.example.hospital.ui.gallery;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.entidades.Doctor;
import com.example.hospital.ui.share.Hopital_dialog;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment implements Doctor_dialog.Doctor_dialog_OnCreate {
    SQLconectionHelper conn;
    SearchView buscar;
    ArrayAdapter<CharSequence> adaptador;
    ListView mi_lista;
    Button add_h;
    Doctor_dialog diag;
    View root;

    public ArrayList<String> Lista_doctore;
    public ArrayList<Doctor> Lista_objetos;
    public ArrayList<String> Lista_ids;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mi_lista = root.findViewById(R.id.lista_hospitales);

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        diag = new Doctor_dialog();
        diag.setListener(this);
        add_h = root.findViewById(R.id.add_doctor);
        add_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag.show(getFragmentManager() ,"gialog hospital");
            }
        });


        load_doctores("");
        buscar = root.findViewById(R.id.buscar_doctores);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar.setIconified(false);
            }
        });
        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                load_doctores(query);

                Toast.makeText(root.getContext(),
                        "buscar "+ query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                load_doctores(newText);
                return false;
            }
        });

        return root;
    }

    private void load_doctores(String palabra) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Lista_doctore = new ArrayList<String>();
        //Lista_hospitales.add("Seleccione");
        palabra = "\'%%"+palabra+"%%\'";
        Cursor cursor = db.rawQuery(Utilidades.SQL_search_doctor+palabra, null);

        while(cursor.moveToNext()){
            Integer ident =cursor.getInt(0);
            String idents = ident.toString();
            String nameh= cursor.getString(1);
            String address= cursor.getString(2);
            String num= cursor.getString(3);
            Lista_doctore.add(nameh+"  --  dir: "+address +"  --  num: "+num);
        }
        adaptador = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1,Lista_doctore);
        mi_lista.setAdapter(adaptador);
    }

    @Override
    public void applyTests() {
        load_doctores("");
    }
}