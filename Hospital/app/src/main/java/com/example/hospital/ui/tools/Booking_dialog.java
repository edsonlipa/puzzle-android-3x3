package com.example.hospital.ui.tools;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.entidades.Doctor;
import com.example.hospital.ui.dialog.DatePickerFragment;
import com.example.hospital.ui.gallery.Doctor_dialog;
import com.example.hospital.utilidades.Utilidades;

import java.util.ArrayList;

public class Booking_dialog extends AppCompatDialogFragment {

    View root;

    TextView textid,textid2;
    EditText fecha,room_n,bed_n;
    SQLconectionHelper conn;
    Spinner hosps,docs;
    Button boton;
    Booking_dialog.Booking_dialog_OnCreate listener;
    public ArrayList<String> Lista_hospitales;
    public ArrayList<Doctor> Lista_objetos;
    public ArrayList<String> Lista_ids;

    public ArrayList<String> Lista_doctore;
    public ArrayList<String> Lista_ids_d;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.dialog_booking, null);

         conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        hosps = root.findViewById(R.id.Combo_hospitales_book);
        docs = root.findViewById(R.id.Combo_doctores_book);
        textid = (TextView) root.findViewById(R.id.textid_hospital_book);
        textid2 = (TextView) root.findViewById(R.id.textid_doctor_book);
        fecha = root.findViewById(R.id.fecha);
        room_n = root.findViewById(R.id.numero_cuarto);
        bed_n = root.findViewById(R.id.numero_cama);

        load_hospitale();


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

        load_pacientes();
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

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
//                        String date = "2019-10-10 ";
                        values.put(Utilidades.CAMPO_DATE_BOOKING,fecha.getText().toString());
//                        values.put(Utilidades.CAMPO_DATE_BOOKING,date);
                        values.put(Utilidades.CAMPO_ROOM_BOOKING,room_n.getText().toString());
                        values.put(Utilidades.CAMPO_BED_BOOKING,bed_n.getText().toString());
                        values.put(Utilidades.CAMPO_HOSP_BOOKING,textid.getText().toString());
                        values.put(Utilidades.CAMPO_PATIENT_BOOKING,textid2.getText().toString());
                        for (String val:values.keySet()){
                            if (values.get(val).toString().isEmpty())values.putNull(val);
                        }
                        long idResult = db.insert(Utilidades.TABLA_BOOKING, Utilidades.CAMPO_ID_BOOKING,values );
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
    private void load_pacientes() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Lista_doctore = new ArrayList<String>();
        Lista_ids_d= new ArrayList<String>();
        //Lista_hospitales.add("Seleccione");
        Cursor cursor = db.rawQuery("select * from Patient", null);
        while(cursor.moveToNext()){
            Integer ident =cursor.getInt(0);
            String idents = ident.toString();
            String nameh= cursor.getString(1);
            Log.i("Nombre", idents );
            Log.i("ID", nameh);
            Lista_doctore.add(nameh);
            Lista_ids_d.add(idents);
        }
        ArrayAdapter<CharSequence> adaptador2 = new ArrayAdapter(root.getContext(),R.layout.support_simple_spinner_dropdown_item,Lista_doctore);
        docs.setAdapter(adaptador2);
        docs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                textid2.setText(Lista_ids_d.get(position));
                Log.i("Psition selected ", "onItemSelected: "+position);
                Log.i("list selected ", "onItemSelected: "+Lista_ids_d.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //textid.setText(Lista_ids.get(0));
            }
        });
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
        Log.i("fin load hospitales", Lista_hospitales.get(0) );

    }
    public void setListener(Booking_dialog.Booking_dialog_OnCreate onCreate){
        this.listener = onCreate;
    }
    public interface Booking_dialog_OnCreate{

        void applyTests();
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = year + " - " + (month+1) + " - " + day;
                fecha.setText(selectedDate);
                Log.i("DATE TODAY", "onDateSet: "+selectedDate);
            }
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

}
