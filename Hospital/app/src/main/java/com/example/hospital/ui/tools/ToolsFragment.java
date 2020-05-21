package com.example.hospital.ui.tools;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.entidades.Doctor;
import com.example.hospital.ui.dialog.DatePickerFragment;
import com.example.hospital.ui.gallery.Doctor_dialog;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;
import java.util.Date;

public class ToolsFragment extends Fragment implements Booking_dialog.Booking_dialog_OnCreate {
    SQLconectionHelper conn;
    SearchView buscar;
    ArrayAdapter<CharSequence> adaptador;
    ListView mi_lista;
    Button add_h;
    Booking_dialog diag;
    View root;
    public ArrayList<String> Lista_hospitales;
    public ArrayList<Doctor> Lista_objetos;
    public ArrayList<String> Lista_ids;

    public ArrayList<String> Lista_doctore;
    public ArrayList<String> Lista_ids_d;


    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        root = inflater.inflate(R.layout.fragment_tools, container, false);
        mi_lista = root.findViewById(R.id.lista_hospitales);

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        diag = new Booking_dialog();
        diag.setListener(this);
        add_h = root.findViewById(R.id.add_booking);
        add_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    showDatePickerDialog();
                diag.show(getFragmentManager() ,"gialog hospital");

            }
        });
        load_booking("");
        buscar = root.findViewById(R.id.buscar_booking);
        buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar.setIconified(false);
            }
        });
        buscar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                load_booking(query);

                Toast.makeText(root.getContext(),
                        "buscar "+ query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                load_booking(newText);
                return false;
            }
        });
        return root;
    }
    private void load_booking(String palabra) {
        SQLiteDatabase db = conn.getReadableDatabase();
        Lista_doctore = new ArrayList<String>();
        //Lista_hospitales.add("Seleccione");
        palabra = "\'%%"+palabra+"%%\'";
        Cursor cursor = db.rawQuery(Utilidades.SQL_search_booking+palabra, null);

        while(cursor.moveToNext()){
            Integer ident =cursor.getInt(0);
            String idents = ident.toString();
            String nameh= cursor.getString(1);
            String address= cursor.getString(2);
            String num= cursor.getString(3);
            Lista_doctore.add(nameh+"  --  room: "+address +"  --  bed: "+num);
        }
        adaptador = new ArrayAdapter(root.getContext(),android.R.layout.simple_list_item_1,Lista_doctore);
        mi_lista.setAdapter(adaptador);
    }

    @Override
    public void applyTests() {
        load_booking("");
    }

}