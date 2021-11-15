package com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;


public class MedicoMainFragment extends Fragment {
        public MedicoMainFragment (){ }

        @Override
        public void onCreate(Bundle save){
            super.onCreate(save);
        }

        @Override
        public  View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.fragment_medico_main, container, false);

            if(savedInstanceState == null){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new MedicoAddFragment()).commit();
            }

            Button ADD = v.findViewById(R.id.buttonAdicionarMedico);
            Button LIST = v.findViewById(R.id.buttonListarMedico);

            ADD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new MedicoAddFragment()).commit();
                }
            });

            LIST.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new MedicoListFragment()).commit();
                }
            });
            return v;
        }
}