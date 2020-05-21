package com.example.hospital.ui.home;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class HomeFragment extends Fragment {
    SQLconectionHelper conn;
    EditText id, nombre; Button boton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        conn = new SQLconectionHelper(root.getContext(),Utilidades.HOSPITAL_DATABASE,null,1);
        //id = root.findViewById(R.id.id_h);

        SQLiteDatabase db = conn.getWritableDatabase();
        Log.i("Database", "onCreateView: "+db.getPath());
        //        Cursor c = db.rawQuery("\.database", null);
        Cursor c = db.rawQuery("SELECT ID FROM Hospital WHERE name IS NULL", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                //Toast.makeText(activityName.this, "Table Name=> "+c.getString(0), Toast.LENGTH_LONG).show();
                Log.i("TABLAS", "Table Name=> "+c.getString(0));
                c.moveToNext();
            }
        }else{
            Log.w("NADA", "onCreateView: vacio");
        }

        return root;
    }

}