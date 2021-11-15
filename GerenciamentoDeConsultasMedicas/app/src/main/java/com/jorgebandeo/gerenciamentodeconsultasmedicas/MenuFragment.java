package com.jorgebandeo.gerenciamentodeconsultasmedicas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta.ConsultaMainFragment;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico.MedicoMainFragment;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente.PacienteManiFragment;


public class MenuFragment extends Fragment {

    public MenuFragment (){ }

    @Override
    public void onCreate(Bundle s){
        super.onCreate(s);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public  boolean onOptionsItemSelected(MenuItem I){

        switch (I.getItemId()){
            case R.id.menuConsulta:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new ConsultaMainFragment()).commit();
                break;
            case R.id.menuMedico:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new MedicoMainFragment()).commit();
                break;
            case R.id.menuPaciente:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMain, new PacienteManiFragment()).commit();
                break;
        }
        return super.onOptionsItemSelected(I);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

}