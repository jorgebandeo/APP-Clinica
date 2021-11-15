package com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta;

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


public class ConsultaListFragment extends Fragment {
    database db;
    public ConsultaListFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_consulta_list, container, false);
        db = new database(getContext());
        ListView lv = v.findViewById(R.id.listViewConsulta);
        db.getAllConsulta(getContext(), lv);

       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tvId = view.findViewById(R.id.textViewIdListarConsulta);
                Bundle b = new Bundle();
                b.putInt("id", Integer.parseInt(tvId.getText().toString()));
                ConsultaEditFragment editar = new ConsultaEditFragment ();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                editar.setArguments(b);
                ft.replace(R.id.frameConsulta, editar).commit();
            }
        });
        return v;
    }

}