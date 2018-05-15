package com.example.bepis04.serializacion2.fragmentos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bepis04.serializacion2.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class AlmExtFragment extends Fragment {

    final static String FICHERO="Prueba.txt";
    private static final int REQUEST_EXTERNAL_STORAGE=1;
    //static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL=200;
    File ruta_sd = Environment.getExternalStorageDirectory();

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public static void verifyStoragePermissions(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater,
                             ViewGroup container, Bundle savedInstanceState){
        return layoutInflater.inflate(R.layout.fragment_almext, container, false);
    }

    public void guardarArchivo(String datos, Context context){
        String estadoSD = Environment.getExternalStorageState();
        if(!estadoSD.equals(Environment.MEDIA_MOUNTED)){
            Snackbar.make(getView(),"No se encuentra SD",Snackbar.LENGTH_SHORT).show();
            return;
        }
        try{
            verifyStoragePermissions(getActivity());
            File f = new File(ruta_sd.getAbsolutePath(),FICHERO);
            FileOutputStream fileOutputStream = new FileOutputStream(f,true);
            String texto = datos + "\n";
            fileOutputStream.write(texto.getBytes());
            Toast.makeText(context, "Cadena guardada", Toast.LENGTH_SHORT).show();
            fileOutputStream.close();
        }catch (Exception ex){
            Log.e("App Ficheros", ex.getMessage(),ex);
        }
    }
    public Vector<String> obtenerData(Context context){
        Vector<String> result = new Vector<>();
        String estadoSD = Environment.getExternalStorageState();
        if(!estadoSD.equals(Environment.MEDIA_MOUNTED) && !estadoSD.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            Snackbar.make(getView(),"No se puede leer",Snackbar.LENGTH_SHORT).show();
            return result;
        }
        try{
            verifyStoragePermissions(getActivity());
            File f = new File(ruta_sd.getAbsolutePath(),FICHERO);
            FileInputStream fileInputStream = new FileInputStream(f);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String linea;
            do{
                linea= reader.readLine();
                if (linea != null){
                    result.add(linea);
                }
            }while (linea != null);
            fileInputStream.close();
        }catch (Exception ex){
            Log.e("App Ficheros", ex.getMessage(),ex);
        }
        return result;
    }

    public void actualizarEtiqueta(){
        TextView textView = (TextView)getActivity().findViewById(R.id.txt_view);
        Vector<String> datos = obtenerData(getContext());
        textView.setText(datos.toString());
    }
    @Override
    public void onResume(){
        super.onResume();

        actualizarEtiqueta();
        final EditText editText = (EditText)getActivity().findViewById(R.id.txt_editar);
        final Button button = (Button)getActivity().findViewById(R.id.btn_agregar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarArchivo(editText.getText().toString(),getContext());
                editText.setText("");
                actualizarEtiqueta();
            }
        });
    }
}
