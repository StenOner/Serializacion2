package com.example.bepis04.serializacion2.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bepis04.serializacion2.R;

import java.io.FileOutputStream;


public class AlmIntFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_almint, container, false);
    }
    @Override
    public void onResume(){
        super.onResume();
        Button btn_save = (Button)getActivity().findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_file = (EditText)getActivity().findViewById(R.id.edt_namefile);
                EditText contenido = (EditText)getActivity().findViewById(R.id.edt_texto);

                if(txt_file.getText().toString().isEmpty()){
                    Snackbar.make(getView(),"Ingrese un fichero",Snackbar.LENGTH_SHORT).show();
                }else if(contenido.getText().toString().isEmpty()){
                    Snackbar.make(getView(),"Ingrese contenido",Snackbar.LENGTH_SHORT).show();
                }else{
                    FileOutputStream fileOutputStream;
                    try{
                        fileOutputStream = getContext().openFileOutput(txt_file.getText().toString(), Context.MODE_PRIVATE);
                        fileOutputStream.write(contenido.getText().toString().getBytes());
                        fileOutputStream.close();
                        Toast.makeText(getActivity(), "EZ", Toast.LENGTH_SHORT).show();
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }
}
