package com.jorgebandeo.gerenciamentodeconsultasmedicas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta.ConsultaMainFragment;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente.PacienteManiFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new ConsultaMainFragment()).commit();
        }
        
    }
}