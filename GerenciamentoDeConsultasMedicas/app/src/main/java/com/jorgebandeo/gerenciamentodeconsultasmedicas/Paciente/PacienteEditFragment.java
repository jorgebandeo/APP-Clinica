package com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico.Medico;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico.MedicoListFragment;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;

import java.util.ArrayList;


public class PacienteEditFragment extends Fragment {
    EditText ETnome;
    Spinner ETgrup_s;
    EditText ETcelular;
    EditText ETfixo;
    ArrayList<String> grps;
    database db;
    Paciente p;

    public PacienteEditFragment(){ }

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_paciente_edit, container, false);
        db = new database(getActivity());
        Bundle bundle = getArguments();
        int id_medico = bundle.getInt("id");
        p = db.getPaciente(id_medico);
        ETnome = v.findViewById(R.id.CampoPacienteNomeEDIT);
        ETgrup_s = v.findViewById(R.id.spinnerGrupSEDIT);
        ETcelular = v.findViewById(R.id.CampoPacienteCelularEDIT);
        ETfixo = v.findViewById(R.id.CampoPacienteFixoEDIT);
        //carregando o spinner
        ETgrup_s = v.findViewById(R.id.spinnerGrupSEDIT);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinnerItemsGrupS, android.R.layout.simple_spinner_item);
        ETgrup_s.setAdapter(adapter);
        //---------------------(coloca os dados da consulta selecionada)-------------------------//
        ETnome.setText(p.getNome());
        ETgrup_s.setSelection(adapter.getPosition(p.getGrp_sanguineo()));
        ETcelular.setText(p.getCelular()+"");
        ETfixo.setText(p.getFixo()+"");
        //--------------------------------(Buttons de ação)--------------------------------------//
        Button Salvar = v.findViewById(R.id.ButtonPacienteSalvarEDIT);
        Button Excluir = v.findViewById(R.id.ButtonPacienteExluirEDIT);

        Salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Salvar(id_medico);
            }
        });
        Excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("tem certeza que desea excluir os paciente");
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
    private void Salvar ( int id ){
        if(ETnome.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        }else if(ETcelular.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o número de celular!", Toast.LENGTH_LONG).show();
        }else if(ETfixo.getText().toString().equals("")){
            Toast.makeText(getActivity(), "Por favor, informe o número fixo!", Toast.LENGTH_LONG).show();
        }else {

            Paciente p = new Paciente();
            p.setId(id);
            db.deletePaciente(p);//remove o paciente antigo para não ter repetições de id
            p.setNome(ETnome.getText().toString());
            p.setGrp_sanguineo(ETgrup_s.getSelectedItem().toString());
            p.setCelular(Integer.parseInt(ETcelular.getText().toString()));
            p.setFixo(Integer.parseInt(ETfixo.getText().toString()));
            db.createPaciente(p);
            Toast.makeText(getActivity(), "Pacinete salvo!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new PacienteListFragment()).commit();
        }
    }
    //-----------------------(função responsável por remover um elemento)--------------------------//
    private void Excluir (int id) {
        p = new Paciente();
        p.setId(id);
        db.deletePaciente(p);
        Toast.makeText(getActivity(), "Paciete  excluído!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new PacienteListFragment()).commit();
    }
}