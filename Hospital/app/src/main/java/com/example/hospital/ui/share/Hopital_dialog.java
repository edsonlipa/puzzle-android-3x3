package com.example.hospital.ui.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.hospital.R;
import com.example.hospital.SQLconectionHelper;
import com.example.hospital.utilidades.Utilidades;

public class Hopital_dialog extends AppCompatDialogFragment {
    SQLconectionHelper conn;
    EditText id, nombre; Button boton;
    private Hopital_dialog.Hospital_dialog_OnCreate listener;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View root = inflater.inflate(R.layout.dialog_hospitales, null);

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        //id = root.findViewById(R.id.id_h);
        nombre = root.findViewById(R.id.name_h);


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
                String name = nombre.getText().toString();
                 if(name==null) Log.e("error", "onClick: ESNULLO" );
                 else Log.i("VALIDO", "onClick: ESVALIDO "+name);
                //values.put(Utilidades.CAMPO_ID_HOSPITAL,id.getText().toString());
                values.put(Utilidades.CAMPO_name_HOSPITAL,name);
                for (String val:values.keySet()){
                    if (values.get(val).toString().isEmpty())values.putNull(val);
                }

                long idResult = db.insert(Utilidades.TABLA_HOSPITAL, Utilidades.CAMPO_ID_HOSPITAL,values );
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
    public void setListener(Hopital_dialog.Hospital_dialog_OnCreate onCreate){
        this.listener = onCreate;
    }
    public interface Hospital_dialog_OnCreate{

        void applyTests();
    }
}
