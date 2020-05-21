package com.example.hospital.ui.slideshow;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.entidades.Doctor;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment implements Paciente_dialog.Paciente_dialog_OnCreate {
    SQLconectionHelper conn;
    SearchView buscar;
    ArrayAdapter<CharSequence> adaptador;
    ListView mi_lista;
    Button add_h;
    Paciente_dialog diag;
    View root;

    public ArrayList<String> Lista_doctore;
    public ArrayList<Doctor> Lista_objetos;
    public ArrayList<String> Lista_ids;

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        mi_lista = root.findViewById(R.id.lista_hospitales);

        load_doctores("");
        diag = new Paciente_dialog();
        diag.setListener(this);
        add_h = root.findViewById(R.id.add_paciente);
        add_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diag.show(getFragmentManager() ,"gialog hospital");
            }
        });
        buscar = root.findViewById(R.id.buscar_pacientes);
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
        Cursor cursor = db.rawQuery(Utilidades.SQL_search_paciente+palabra, null);

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