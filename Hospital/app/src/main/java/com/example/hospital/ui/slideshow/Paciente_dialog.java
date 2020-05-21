package com.example.hospital.ui.slideshow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.entidades.Doctor;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;

public class Paciente_dialog extends AppCompatDialogFragment {
    TextView textid;
    EditText nombre,dir,tel;
    SQLconectionHelper conn;
    Spinner hosps;
    Button boton;
    View root;
    Paciente_dialog.Paciente_dialog_OnCreate listener;
    public ArrayList<String> Lista_doctore;
    public ArrayList<Doctor> Lista_objetos;
    public ArrayList<String> Lista_ids;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.dialog_paciente, null);

        textid = (TextView) root.findViewById(R.id.textid_hospital);

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        hosps = root.findViewById(R.id.Combo_doctores);
        nombre = root.findViewById(R.id.nombre_paciente);
        dir = root.findViewById(R.id.direccion_paciente);
        tel = root.findViewById(R.id.telefono_paciente);

        load_doctores();

        builder.setView(root).setTitle("Crear")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener!=null)
                            listener.applyTests();
                        else Log.i("ERROR!!!!!!!!!!!!!!!!!!", "no hay listner");

                    }
                })
                .setPositiveButton("Crear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SQLiteDatabase db = conn.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(Utilidades.CAMPO_NAME_PACIENTE,nombre.getText().toString());
                        values.put(Utilidades.CAMPO_ADDS_PACIENTE,dir.getText().toString());
                        values.put(Utilidades.CAMPO_TEL_PACIENTE,tel.getText().toString());
                        values.put(Utilidades.CAMPO_DOC_PACIENTE,textid.getText().toString());
                        for (String val:values.keySet()){
                            if (values.get(val).toString().isEmpty())values.putNull(val);
                        }
                        long idResult = db.insert(Utilidades.TABLA_PACIENTE, Utilidades.CAMPO_ID_PACIENTE,values );
                        if (idResult!=-1)Toast.makeText(root.getContext(), "Creado Exitosamente con el id: "+idResult,Toast.LENGTH_SHORT ).show();
                        else Toast.makeText(root.getContext(), "Error al crear", Toast.LENGTH_SHORT ).show();

                        db.close();
//
                        if (listener!=null)
                            listener.applyTests();
                        else Log.i("ERROR!!!!!!!!!!!!!!!!!!", "no hay listner");
                    }
                });
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(root.getContext(),R.layout.support_simple_spinner_dropdown_item,Lista_doctore);
        hosps.setAdapter(adaptador);
        hosps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textid.setText(Lista_ids.get(position));
                Log.i("Psition selected ", "onItemSelected: "+position);
                Log.i("list selected ", "onItemSelected: "+Lista_ids.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //textid.setText(Lista_ids.get(0));
            }
        });



        return builder.create();
    }
    private void load_doctores() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Lista_doctore = new ArrayList<String>();
        Lista_ids= new ArrayList<String>();
        //Lista_hospitales.add("Seleccione");
        Cursor cursor = db.rawQuery("select * from Doctor", null);
        while(cursor.moveToNext()){
            Integer ident =cursor.getInt(0);
            String idents = ident.toString();
            String nameh= cursor.getString(1);
            Log.i("Nombre", idents );
            Log.i("ID", nameh);
            Lista_doctore.add(nameh);
            Lista_ids.add(idents);
        }
    }
    public void setListener(Paciente_dialog.Paciente_dialog_OnCreate onCreate){
        this.listener = onCreate;
    }
    public interface Paciente_dialog_OnCreate{

        void applyTests();
    }
}
