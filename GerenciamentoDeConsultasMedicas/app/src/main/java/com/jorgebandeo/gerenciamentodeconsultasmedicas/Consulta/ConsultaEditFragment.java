package com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase.database;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;

import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class ConsultaEditFragment extends Fragment {

    EditText obserbacao;
    TextView DIA;
    TextView hdi;
    TextView hdf;
    Spinner medico;
    Spinner paciente;
    ArrayList<Integer> medico_id_list;
    ArrayList<String> medico_list;
    ArrayList<Integer> paciente_id_list;
    ArrayList<String> paciente_list;
    Consulta c;
    database db;
     public ConsultaEditFragment(){

     }

     @Override
    public void onCreate(Bundle save){
         super.onCreate(save);
     }

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_consulta_edit, container, false);
         Bundle bundle = getArguments();
         int id_consulta = bundle.getInt("id");
         db = new database(getActivity());
         c = db.getConsulta(id_consulta);
         DIA = v.findViewById(R.id.CampoDiaConsultaViewEDIT);
         hdi = v.findViewById(R.id.CampoHoraInicioConsultaViewEDIT);
         hdf = v.findViewById(R.id.CampoHoraFimConsultaViewEDIT);
         medico = v.findViewById(R.id.SpinnerMedicoConsultaViewEDIT);
         paciente = v.findViewById(R.id.SpinnerPacienteConsultaViewEDIT);
         obserbacao = v.findViewById(R.id.CampoComentarioConsultaViewEDIT);
         //pegando os id's com os nomes dos médicos do DataBase
         medico_id_list = new ArrayList<>();
         medico_list = new ArrayList<>();
         db.getNameAllMedico(medico_id_list, medico_list);
         //pegando os ids com os nomes dos pacientes do DataBase
         paciente_id_list = new ArrayList<>();
         paciente_list = new ArrayList<>();
         db.getNameAllPaciente(paciente_id_list, paciente_list);
         //carregando o spinner medíco
         ArrayAdapter<String> MedicoArrayAdap = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, medico_list);
         medico.setAdapter(MedicoArrayAdap);
         //carregando o spinner paciente
         ArrayAdapter<String> PacienteArrayAdap = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, paciente_list);
         paciente.setAdapter(PacienteArrayAdap);

         //---------------------(coloca os dados da consulta selecionada)-------------------------//
         medico.setSelection(medico_id_list.indexOf(c.getMedico_id()));
         paciente.setSelection(paciente_id_list.indexOf(c.getPaciente_id()));
         DIA.setText(c.getDia());
         hdi.setText(c.getdata_hora_inicio());
         hdf.setText(c.getdata_hora_fim());
         obserbacao.setText(c.getObservacao());
         //--------------------------------(Buttons de ação)--------------------------------------//
         Button SelectDia        = (Button) v.findViewById(R.id.buttonSelectDiaEDIT);
         Button SelectInicioHora = (Button) v.findViewById(R.id.buttonSelectInicioEDIT);
         Button SelectFimHora    = (Button) v.findViewById(R.id.buttonSelectFinEDIT);

         SelectDia.setOnClickListener(new View.OnClickListener() {//adiciona o dia
             @Override
             public void onClick(View v) {
                 DiaSelect(DIA);
             }//chamada de função
         });

         SelectInicioHora.setOnClickListener(new View.OnClickListener() {//adiciona a hora de início
             @Override
             public void onClick(View v) {
                 HoraSelect(hdi);
             }//chamada de função
         });

         SelectFimHora.setOnClickListener(new View.OnClickListener() {//adiciona a hora de fim
             @Override
             public void onClick(View v) {
                 HoraSelect(hdf);
             }//chamada de função
         });

         Button edit = v.findViewById(R.id.ButtonSalvarConsultaEDIT);
         edit.setOnClickListener(new View.OnClickListener() {//salva as alterações
             @Override
             public void onClick(View v) {
                 editar( id_consulta );
             }//chamada de função
         });

         Button Excluir = v.findViewById(R.id.ButtonExcluirConsultaEDIT);
         Excluir.setOnClickListener(new View.OnClickListener() {//remove o elemento do Database
             @Override
             public void onClick(View v) {
                 //mensagem de solicitação de confirmação de remoção
                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                 builder.setTitle("tem certeza de que quer excluir");
                 builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         excluir(id_consulta);//chamada de função
                     }
                 });
                 builder.setNegativeButton("nao", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         // Não faz nada
                     }
                 });
                 AlertDialog alert = builder.create();
                 alert.show();
             }
         });

         return v;
     }
    //--------------(função responsável por retornar o dia da semana por meio da data)------------//
    public static String getDayOfWeek(String data) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        DayOfWeek dow = DayOfWeek.from(parser.parse(data));
        return dow.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")).toUpperCase();
    }
    //-----------------------(função responsável por editar um elemento)--------------------------//
    public void editar (int id){
        int intHI=0;//auxiliar para cálculo de consistência
        int intHF=0;//auxiliar para cálculo de consistência
        String auxD = (getDayOfWeek(DIA.getText().toString()));//auxiliar que recebe se o dia da semana

        //para evitar falhas os auxiliares de cálculo só recebem os valores se existirem valores
        if(hdi.getText().toString() != "" || hdf.getText().toString() != "") {
            //retira a separação da hora e possíveis espaços e retorna inteiros
            intHI = Integer.parseInt(hdi.getText().toString().replace(":","").replace(" ",""));
            intHF = Integer.parseInt(hdf.getText().toString().replace(":","").replace(" ",""));
        }

        if(hdi.getText().toString().equals("")){
            //TextView da hora de inícia
            Toast.makeText(getActivity(), "Por favor, informe o horario de inicio!", Toast.LENGTH_LONG).show();
        }else if (hdf.getText().toString().equals("")){
            //TextView da hora de fim
            Toast.makeText(getActivity(), "Por favor, informe o horario de fim!", Toast.LENGTH_LONG).show();
        }else if(DIA.getText().toString().equals("")) {
            //TextView do dia
            Toast.makeText(getActivity(), "Por favor, informe o Dia!", Toast.LENGTH_LONG).show();
        }else if (obserbacao.getText().toString().equals("")){
            //TextView da obs:.
            Toast.makeText(getActivity(), "Por favor, desreva a ocaciao da consulta!", Toast.LENGTH_LONG).show();
        }else if(auxD.equals("DOM.")) {
            //verifica se o dia for domingo
            Toast.makeText(getActivity(), "O dia escolhido não pode ser num domingo!", Toast.LENGTH_LONG).show();
        }else if(auxD.equals("SÁB.")){
            //verifica se o dia for sábado
            Toast.makeText(getActivity(), "O dia escolhido não pode ser num sábado!", Toast.LENGTH_LONG).show();
        }else if(intHI > intHF){
            //se o início for maior que o fim
            Toast.makeText(getActivity(), "O horario de inicio tem que ser maior que o de fim!", Toast.LENGTH_LONG).show();
        }else if(intHI > 1730 && intHI < 800) {
            //se o início for no intervalo das 17:30 as 8:00
            Toast.makeText(getActivity(), "Nâo pode selecioar um horario de inciono das 17:30 as 8:00!", Toast.LENGTH_LONG).show();
        }else if(intHI > 1200 && intHI < 1330){
            //se o início for no intervalo das 12:00 as 13:30
            Toast.makeText(getActivity(), "Nâo pode selecioar um horario de inciono das 12:00 as 13:30!", Toast.LENGTH_LONG).show();
        }else if(intHF > 1730 && intHF < 800) {
            //se o fim for no intervalo das 17:30 as 8:00
            Toast.makeText(getActivity(), "Nâo pode selecioar um horario de fim das 17:30 as 8:00!", Toast.LENGTH_LONG).show();
        }else if(intHF > 1200 && intHF < 1330){
            //se o fim for no intervalo das 12:00 as 13:30
            Toast.makeText(getActivity(), "Nâo pode selecioar um horario de fim das 12:00 as 13:30!", Toast.LENGTH_LONG).show();
        }else{

            db = new database(getActivity());
            Consulta c = new Consulta();
            c.set_id(id);
            db.deleteConsulta(c);//remove a consulta antiga para não ter repetições de id
            String NomeMedico = medico.getSelectedItem()+"";
            c.setMedico_id(medico_id_list.get(medico_list.indexOf(NomeMedico)));
            String NomePaciente = paciente.getSelectedItem().toString();
            c.setPaciente_id(paciente_id_list.get(paciente_list.indexOf(NomePaciente)));
            c.setDia(DIA.getText().toString());
            c.setdata_hora_inicio(hdi.getText().toString());
            c.setdata_hora_fim(hdf.getText().toString());
            c.setObservacao(obserbacao.getText().toString());
            db.createConsulta(c);
            Toast.makeText(getActivity(), "Consulta editado!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta, new ConsultaListFragment()).commit();
        }

    }
    //-----------(função responsável por criar o seletor da data e setar num TextView)------------//
    private  void  DiaSelect (TextView textdiaView){
        int ANO = Integer.parseInt(textdiaView.getText().toString().substring(6,10));
        int MES = Integer.parseInt(textdiaView.getText().toString().substring(3,5)) - 1;
        int DIA = Integer.parseInt(textdiaView.getText().toString().substring(0,2));
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                mes++;
                String Sdia = dia + "", Smes = mes + "";
                if(dia <10){//consistência para dia de dois dígitos
                    Sdia = "0"+dia;
                }
                if(mes <10){//consistência para mes de dois dígitos
                    Smes = "0"+mes;
                }
                String dataString = Sdia + "/" +  Smes + "/" + ano;
                textdiaView.setText(dataString);
            }
        }, ANO, MES, DIA);
        datePickerDialog.show();
    }
    //-----------(função responsável por criar o seletor de hora e setar num TextView)------------//
    private  void  HoraSelect(TextView texthoraview ){
        Calendar calendar =  Calendar.getInstance();
        int hora = Integer.parseInt(texthoraview.getText().toString().substring(0,2));
        int minutos= Integer.parseInt(texthoraview.getText().toString().substring(5));
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String m,h;
                if(minute < 10){//consistência para colocar os minutos com dos dígitos
                    m= "0" + minute;
                }else {
                    m = minute+"";
                }
                if(hour<10){//consistência para colocar as horas com dos dígitos
                    h="0" + hour;
                }else {
                    h = hour + "";
                }
                String TimeString =  h + " : " + m;
                texthoraview.setText(TimeString);
            }
        },hora,minutos, true);
        timePickerDialog.show();
    }
    //-----------------------(função responsável por remover um elemento)--------------------------//
    private void excluir (int id) {
        c = new Consulta();
        c.set_id(id);
        db.deleteConsulta(c);
        Toast.makeText(getActivity(), "Consulta excluída!", Toast.LENGTH_LONG).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta, new ConsultaListFragment()).commit();
    }
}
