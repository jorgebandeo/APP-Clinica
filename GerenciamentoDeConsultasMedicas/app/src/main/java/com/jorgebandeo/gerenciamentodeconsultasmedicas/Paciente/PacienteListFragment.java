package com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase.database;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;


public class PacienteListFragment extends Fragment {
    database db;
    public PacienteListFragment (){

    }
    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_paciente_list, container, false);
        ListView LV = v.findViewById(R.id.listViewPaciente);
        db = new database(getContext());
        db.getAllPaciente(getContext(), LV);
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = view.findViewById(R.id.ItemIdPaciente);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));
                PacienteEditFragment edit = new PacienteEditFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                edit.setArguments(b);
                ft.replace(R.id.framePaciente, edit).commit();
            }
        });
        return v;
    }
}