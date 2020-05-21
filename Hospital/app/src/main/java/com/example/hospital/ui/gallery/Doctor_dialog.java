package com.example.hospital.ui.gallery;

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
import com.example.hospital.ui.share.Hopital_dialog;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;

public class Doctor_dialog extends AppCompatDialogFragment {
    TextView textid;
    EditText nombre,dir,tel;
    SQLconectionHelper conn;
    Spinner hosps;
    Button boton;
    private Doctor_dialog.Doctor_dialog_OnCreate listener;

    public ArrayList<String> Lista_hospitales;
    public ArrayList<Doctor> Lista_objetos;
    public ArrayList<String> Lista_ids;
    View root;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.dialog_doctor, null);

        textid = (TextView) root.findViewById(R.id.textid);

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        hosps = root.findViewById(R.id.Combo_hospitales);

        nombre = root.findViewById(R.id.nombre_doctot);
        dir = root.findViewById(R.id.direccion_doctor);
        tel = root.findViewById(R.id.telefono_doctor);
        load_hospitale();

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
                        values.put(Utilidades.CAMPO_NAME_DOCTOR,nombre.getText().toString());
                        values.put(Utilidades.CAMPO_ADDS_DOCTOR,dir.getText().toString());
                        values.put(Utilidades.CAMPO_TEL_DOCTOR,tel.getText().toString());
                        values.put(Utilidades.CAMPO_HOsp_DOCTOR,textid.getText().toString());
                        for (String val:values.keySet()){
                            if (values.get(val).toString().isEmpty())values.putNull(val);
                        }
                        long idResult = db.insert(Utilidades.TABLA_DOCTOR, Utilidades.CAMPO_ID_DOCTOR,values );
                        if (idResult!=-1)Toast.makeText(root.getContext(), "Creado Exitosamente con el id: "+idResult,Toast.LENGTH_SHORT ).show();
                        else Toast.makeText(root.getContext(), "Error al crear", Toast.LENGTH_SHORT ).show();
                        db.close();

                        if (listener!=null)
                            listener.applyTests();
                        else Log.i("ERROR!!!!!!!!!!!!!!!!!!", "no hay listner");
                    }
                });

        return builder.create();
    }
    private void load_hospitale() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Lista_hospitales = new ArrayList<String>();
        Lista_ids= new ArrayList<String>();
        //Lista_hospitales.add("Seleccione");
        Cursor cursor = db.rawQuery("select * from Hospital", null);
        while(cursor.moveToNext()){
            Integer ident =cursor.getInt(0);
            String idents = ident.toString();
            String nameh= cursor.getString(1);
            Log.i("Nombre", idents );
            Log.i("ID", nameh);
            Lista_hospitales.add(nameh);
            Lista_ids.add(idents);
        }
        ArrayAdapter<CharSequence> adaptador = new ArrayAdapter(root.getContext(),R.layout.support_simple_spinner_dropdown_item,Lista_hospitales);
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
    }
    public void setListener(Doctor_dialog.Doctor_dialog_OnCreate onCreate){
        this.listener = onCreate;
    }
    public interface Doctor_dialog_OnCreate{

        void applyTests();
    }
}
