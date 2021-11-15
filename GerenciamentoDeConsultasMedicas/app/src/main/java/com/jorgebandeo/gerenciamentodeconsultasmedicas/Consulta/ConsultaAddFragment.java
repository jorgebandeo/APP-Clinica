package com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.location.GnssAntennaInfo;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.type.DateTime;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase.database;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;

import java.text.DateFormat;
import java.text.Format;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;


public class ConsultaAddFragment extends Fragment {

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
    database db;

    public ConsultaAddFragment(){}

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_consulta_add, container, false);
        db = new database(getActivity());
        hdi = v.findViewById(R.id.CampoHoraInicioConsultaViewADD);
        hdf = v.findViewById(R.id.CampoHoraFimConsultaViewADD);
        DIA = v.findViewById(R.id.CampoDiaConsultaViewADD);
        medico = v.findViewById(R.id.SpinnerMedicoConsultaViewADD);
        paciente = v.findViewById(R.id.SpinnerPacienteConsultaViewADD);
        obserbacao = v.findViewById(R.id.CampoComentarioConsultaViewADD);

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

        //--------------------------------(Buttons de ação)---------------------------------------//
        Button SelectDia        = (Button) v.findViewById(R.id.buttonSelectDia);
        Button SelectInicioHora = (Button) v.findViewById(R.id.buttonSelectInicio);
        Button SelectFimHora    = (Button) v.findViewById(R.id.buttonSelectFin);


        SelectDia.setOnClickListener(new View.OnClickListener() {//adiciona o dia
            @Override
            public void onClick(View v) { DiaSelect(DIA); }//chamada de função
        });

        SelectInicioHora.setOnClickListener(new View.OnClickListener() {//adiciona a hora de início
            @Override
            public void onClick(View v) { HoraSelect(hdi); }//chamada de função
        });

        SelectFimHora.setOnClickListener(new View.OnClickListener() {//adiciona a hora de fim
            @Override
            public void onClick(View v) { HoraSelect(hdf); }//chamada de função
        });


        Button ADD =(Button) v.findViewById(R.id.ButtonSalvarConsultaADD);
        ADD.setOnClickListener(new View.OnClickListener() {//preparação para salvamento e salvamento
            @Override
            public void onClick(View v) { adicionar(); }//chamada de função
        });


        return v;

    }

    //--------------(função responsável por retornar o dia da semana por meio da data)------------//
    public static String getDayOfWeek(String data) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        DayOfWeek dow = DayOfWeek.from(parser.parse(data));
        return dow.getDisplayName(TextStyle.SHORT, new Locale("pt", "BR")).toUpperCase();
    }
    //-----------------------(função responsável por verificar e salvar)--------------------------//
    public void adicionar (){
        int intHI=0;//auxiliar para cálculo de consistência
        int intHF=0;//auxiliar para cálculo de consistência
        String auxD = "";
        if(DIA.getText().toString() != "") {
            auxD = (getDayOfWeek(DIA.getText().toString()));//auxiliar que recebe se o dia da semana
        }
        //para evitar falhas os auxiliares de cálculo só recebem os valores se existirem valores
        if(hdi.getText().toString() != "" || hdf.getText().toString() != "") {
            //retira a separação da hora e possíveis espaços e retorna inteiros
             intHI = Integer.parseInt(hdi.getText().toString().replace(":","").replace(" ",""));
             intHF = Integer.parseInt(hdf.getText().toString().replace(":","").replace(" ",""));
        }

        if(medico.getSelectedItem()==null){
            //spinner do médico
            Toast.makeText(getActivity(), "Não a medico registrado!", Toast.LENGTH_LONG).show();
        }else if(paciente.getSelectedItem()==null) {
            //spinner do paciente
            Toast.makeText(getActivity(), "Não paciente registrado!", Toast.LENGTH_LONG).show();
        }else if(DIA.getText().toString().equals("")) {
            //TextView do dia
            Toast.makeText(getActivity(), "Por favor, informe o Dia!", Toast.LENGTH_LONG).show();
        }else if(auxD.equals("DOM.")) {
            //verifica se o dia for domingo
            Toast.makeText(getActivity(), "O dia escolhido não pode ser num domingo!", Toast.LENGTH_LONG).show();
        }else if(auxD.equals("SÁB.")){
            //verifica se o dia for sábado
            Toast.makeText(getActivity(), "O dia escolhido não pode ser num sábado!", Toast.LENGTH_LONG).show();
        }else if(hdi.getText().toString().equals("")){
            //TextView da hora de inícia
            Toast.makeText(getActivity(), "Por favor, informe o horario de inicio!", Toast.LENGTH_LONG).show();
        }else if (hdf.getText().toString().equals("")){
            //TextView da hora de fim
            Toast.makeText(getActivity(), "Por favor, informe o horario de fim!", Toast.LENGTH_LONG).show();
        }else if (obserbacao.getText().toString().equals("")){
            //TextView da obs:.
            Toast.makeText(getActivity(), "Por favor, desreva a ocaciao da consulta!", Toast.LENGTH_LONG).show();
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

            Consulta c = new Consulta();
            c.set_id(0);
            String NomeMedico = medico.getSelectedItem()+"";
            c.setMedico_id(medico_id_list.get(medico_list.indexOf(NomeMedico)));
            String NomePaciente = paciente.getSelectedItem().toString();
            c.setPaciente_id(paciente_id_list.get(paciente_list.indexOf(NomePaciente)));
            c.setDia(DIA.getText().toString());
            c.setdata_hora_inicio(hdi.getText().toString());
            c.setdata_hora_fim(hdf.getText().toString());
            c.setObservacao(obserbacao.getText().toString());
            db.createConsulta(c);
            Toast.makeText(getActivity(), "Consulta salva!", Toast.LENGTH_LONG).show();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameConsulta, new ConsultaListFragment()).commit();


            }

    }
    //-----------(função responsável por criar o seletor da data e setar num TextView)------------//
    private  void  DiaSelect (TextView textdiaView){
        Calendar calendar = Calendar.getInstance();
        int ANO = calendar.get(Calendar.YEAR);
        int MES = calendar.get(Calendar.MONTH);
        int DIA = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),new DatePickerDialog.OnDateSetListener(){
           @Override
           public void onDateSet(DatePicker datePicker, int ano, int mes, int dia ) {
                mes++;
                String Sdia = dia + "";
                String Smes = mes + "";
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
    private  void  HoraSelect(TextView texthoraview){
        Calendar calendar =  Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR);
        int minutos= calendar.get(Calendar.MINUTE);
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
                    h=hour+"";
                }
                String TimeString =  h + " : " + m;
                texthoraview.setText(TimeString);
            }
        },hora,minutos, true);
        timePickerDialog.show();
    }
}