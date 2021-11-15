package com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase.database;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente.Paciente;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente.PacienteListFragment;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;

public class MedicoAddFragment extends Fragment {

    EditText ETnome;
    EditText ETcrm;
    EditText ETcelular;
    EditText ETfixo;
    database db;
    public MedicoAddFragment (){

    }

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
       View v    = inflater.inflate(R.layout.fragment_medico_add, container, false);
       ETnome    = v.findViewById(R.id.CampoMedicoNomeADD);
       ETcrm     = v.findViewById(R.id.CampoMedicoCrmADD);
       ETcelular = v.findViewById(R.id.CampoMedicoCelularADD);
       ETfixo    = v.findViewById(R.id.CampoMedicoFixoADD);
       db = new database(getActivity());

       Button Salve =(Button) v.findViewById(R.id.ButtonMedicoSalvarADD);
        Salve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvar();
            }
        });

        return v;
    }
    //-----------------------(função responsável por verificar e salvar)--------------------------//
    private void Salvar() {
        Medico m = new Medico();
        if(ETnome.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }else if(ETcrm.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        }else if(ETcelular.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o número de celular!", Toast.LENGTH_LONG).show();
        }else if(ETfixo.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o número fixo!", Toast.LENGTH_LONG).show();
        }else {
            m.set_id(0);
            m.setNome(ETnome.getText().toString());
            m.setCrm(ETcrm.getText().toString());
            m.setCelular(Integer.parseInt(ETcelular.getText().toString()));
            m.setFixo(Integer.parseInt(ETfixo.getText().toString()));
            db.createMedico(m);
            Toast.makeText(getActivity(), "Medico salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new MedicoListFragment()).commit();
        }
    }
}