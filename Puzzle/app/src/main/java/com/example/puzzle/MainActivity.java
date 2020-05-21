package com.example.puzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    boolean first;
    int firstid,secondid;
    String firstimg,secondimg;
    TableLayout tabla;
//    String [][] puzzle ={{"h9","h8","h7"},{"h6","h5","h4"},{"h3","h2","h1"}};
    int [][] puzzle =new int[3][3];
    String [][] image_puzzle={{"h9","h8","h7"},{"h6","h5","h4"},{"h3","h2","blank"}};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        first =true;
        tabla = findViewById(R.id.tabla);
        init_matrix();
    }
    public void init_matrix() {
        for (int i = 0; i<3; i++){
            for (int j=0; j<3;j++){
                TableRow tr= (TableRow) tabla.getChildAt(i);
                ImageButton ib = (ImageButton) tr.getChildAt(j);
                puzzle[i][j]=ib.getId();
            }
        }
    }
    public void Display() {
        for (int i = 0; i<3; i++){
            for (int j=0; j<3;j++){

                ImageButton imgBut=findViewById(puzzle[i][j]);
                int imageid=getResources().getIdentifier(image_puzzle[i][j],"drawable",getPackageName());
                imgBut.setImageResource(imageid);
            }
        }
    }
    public void shoufle(View view){
        for (int i = 0; i<10; i++){
            Random rand = new Random();
            int i1 = rand.nextInt(3);
            int j1 = rand.nextInt(3);
            int i2 = rand.nextInt(3);
            int j2 = rand.nextInt(3);
            String a =image_puzzle[i1][j1];
            image_puzzle[i1][j1] = image_puzzle[i2][j2];
            image_puzzle[i2][j2] = a;
        }
        Display();
    }
    public void Click(View view) {
        if (first){
            firstid=view.getId();
            Toast.makeText(getApplicationContext(), "seleccione una segunda ficha", Toast.LENGTH_SHORT).show();
            first=!first;
        }else{
            secondid=view.getId();

            if (Check(firstid, secondid)) {
//                Toast.makeText(getApplicationContext(), "si ", Toast.LENGTH_SHORT).show();
                ImageButton from = findViewById(firstid);
                ImageButton to = findViewById(secondid);

                int imageid1 = getResources().getIdentifier(firstimg, "drawable", getPackageName());
                int imageid2 = getResources().getIdentifier(secondimg, "drawable", getPackageName());
                from.setImageResource(imageid2);
                to.setImageResource(imageid1);
            }
            else
                Toast.makeText(getApplicationContext(), "no se puede realizar esa jugada", Toast.LENGTH_SHORT).show();
            first=!first;
        }

    }
    public boolean Check(int first,int second){
        int i1 = -1,j1=-1,i2=-1,j2=-1;

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3;j++){
                if (puzzle[i][j] == first){
                    Log.d("*******", i+image_puzzle[i][j]+j);
                    if (image_puzzle[i][j].equals("blank")){
                        i1=i;
                        j1=j;
                    }else{
                        Log.d("salida", "inesperado");
                        return false;
                    }


                }else {
                    Log.d("**************", "no encontrado");
                }
                if (puzzle[i][j] == second){
                    i2=i;
                    j2=j;
                }
            }
        }

        if((Math.abs(i1 - i2) + Math.abs(j1 - j2)) == 1){
            firstimg =image_puzzle[i1][j1];
            secondimg =image_puzzle[i2][j2];

            String a =image_puzzle[i1][j1];
            image_puzzle[i1][j1] = image_puzzle[i2][j2];
            image_puzzle[i2][j2] = a;
            int b= Math.abs(i1 - i2) + Math.abs(j1 - j2);
            Toast.makeText(getApplicationContext(), "exito "+b, Toast.LENGTH_SHORT).show();
            print("exito");
            return true;
        }else{
            int a= Math.abs(i1 - i2) + Math.abs(j1 - j2);
            print("error "+i1 +" "+ i2+" "+j1 +" "+ j2);
            Toast.makeText(getApplicationContext(), "error "+a, Toast.LENGTH_SHORT).show();

        return false;}
    }
    public void print(String ss){
        for (int i = 0; i<3; i++){
            for (int j=0; j<3;j++) {
            Log.d(ss, image_puzzle[i][j]);
            }
        }
        Log.d(ss, "----------------");

    }
}


