package com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;


public class PacienteManiFragment extends Fragment {
    public PacienteManiFragment (){

    }
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_paciente_mani, container, false);

        if(savedInstanceState == null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new PacienteAddFragment()).commit();
        }
        Button Adicionar = v.findViewById(R.id.buttonAdicionarPaciente);
        Adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new PacienteAddFragment()).commit();
            }
        });
        Button List = v.findViewById(R.id.buttonListarPaciente);
        List.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new PacienteListFragment()).commit();
            }
        });


        return v;
    }

}