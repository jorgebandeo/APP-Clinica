package com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase.database;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;

public class PacienteAddFragment extends Fragment {
    EditText ETnome;
    Spinner ETgrup_s;
    EditText ETcelular;
    EditText ETfixo;
    database db;

    public PacienteAddFragment() { }

    @Override
    public void onCreate(Bundle save) {
        super.onCreate(save);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_paciente_add, container, false);
        db = new database(getActivity());
        ETnome = v.findViewById(R.id.CampoPacienteNomeADD);
        ETgrup_s = v.findViewById(R.id.spinnerGrupSADD);
        ETcelular = v.findViewById(R.id.CampoPacienteCelularADD);
        ETfixo = v.findViewById(R.id.CampoPacienteFixoADD);
        //carregando o spinner
        ETgrup_s = v.findViewById(R.id.spinnerGrupSADD);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerItemsGrupS, android.R.layout.simple_spinner_item);
        ETgrup_s.setAdapter(adapter);
        //--------------------------------(Buttons de ação)--------------------------------------//
        Button salve = v.findViewById(R.id.ButtonPacienteSalvarADD);
        salve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvar();
            }
        });
        return v;
    }
    //-----------------------(função responsável por verificar e salvar)--------------------------//
    private void Salvar() {
        if (ETnome.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (ETcelular.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número de celular!", Toast.LENGTH_LONG).show();
        } else if (ETfixo.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número fixo!", Toast.LENGTH_LONG).show();
        } else {
            Paciente p = new Paciente();
            p.setId(0);
            p.setNome(ETnome.getText().toString());
            p.setGrp_sanguineo(ETgrup_s.getSelectedItem().toString());
            p.setCelular(Integer.parseInt(ETcelular.getText().toString()));
            p.setFixo(Integer.parseInt(ETfixo.getText().toString()));
            db.createPaciente(p);
            Toast.makeText(getActivity(), "Pacinete salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new PacienteListFragment()).commit();
        }
    }
}