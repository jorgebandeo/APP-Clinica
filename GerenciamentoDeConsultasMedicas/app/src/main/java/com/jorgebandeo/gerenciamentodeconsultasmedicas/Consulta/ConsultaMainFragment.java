package com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;


public class ConsultaMainFragment extends Fragment {

    public ConsultaMainFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_consulta_main, container , false);

        if(savedInstanceState == null){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta, new ConsultaAddFragment()).commit();
        }

        Button add = v.findViewById(R.id.buttonAdicionarConsulta);
        Button list = v.findViewById(R.id.buttonListarConsulta);

        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta, new ConsultaAddFragment()).commit();
            }
        });

        list.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta, new ConsultaListFragment()).commit();
            }
        });
    return v;
    }
}