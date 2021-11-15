package com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta.Consulta;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta.ConsultaListFragment;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase.database;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;


public class MedicoEditFragment extends Fragment {
    EditText ETnome;
    EditText ETcrm;
    EditText ETcelular;
    EditText ETfixo;
    database db;
    Medico m;

    public MedicoEditFragment(){ }

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_medico_edit, container, false);
        Bundle bundle = getArguments();
        int id_medico = bundle.getInt("id");
        db = new database(getActivity());
        m = db.getMedico(id_medico);
        ETnome    = v.findViewById(R.id.CampoMedicoNomeEDIT);
        ETcrm     = v.findViewById(R.id.CampoMedicoCrmEDIT);
        ETcelular = v.findViewById(R.id.CampoMedicoCelularEDIT);
        ETfixo    = v.findViewById(R.id.CampoMedicoFixoEDIT);
        //---------------------(coloca os dados da consulta selecionada)-------------------------//
        ETnome.setText(m.getNome());
        ETcrm.setText(m.getCrm());
        ETcelular.setText(m.getCelular()+"");
        ETfixo.setText(m.getFixo()+"");
        //--------------------------------(Buttons de ação)--------------------------------------//
        Button salvar = v.findViewById(R.id.ButtonMedicoSalvarEDIT);
        Button excluir = v.findViewById(R.id.ButtonMedicoExcluirEDIT);

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvar(id_medico);
            }
        });

        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("tem certeza que deseja exclour este medico");
                builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Excluir(id_medico);
                    }
                });
                builder.setNegativeButton("não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nada
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        return v;
    }
    //-----------------------(função responsável por editar um elemento)--------------------------//
    private void Salvar(int id) {
        if(ETnome.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }else if(ETcrm.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o CRM!", Toast.LENGTH_LONG).show();
        }else if(ETcelular.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o número de celular!", Toast.LENGTH_LONG).show();
        }else if(ETfixo.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o número fixo!", Toast.LENGTH_LONG).show();
        }else {
            Medico m = new Medico();
            m.set_id(id);
            db.deleteMedico(m);//remove o medico antigo para não ter repetições de id
            m.setNome(ETnome.getText().toString());
            m.setCrm(ETcrm.getText().toString());
            m.setCelular(Integer.parseInt(ETcelular.getText().toString()));
            m.setFixo(Integer.parseInt(ETfixo.getText().toString()));
            db.createMedico(m);
            Toast.makeText(getActivity(), "Medico salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new MedicoListFragment()).commit();
        }
    }
    //-----------------------(função responsável por remover um elemento)--------------------------//
    private void Excluir (int id) {
        m = new Medico();
        m.set_id(id);
        db.deleteMedico(m);
        Toast.makeText(getActivity(), "Medico excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameMedico, new MedicoListFragment()).commit();
    }
}