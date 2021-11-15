package com.jorgebandeo.gerenciamentodeconsultasmedicas.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.annotation.NonNull;

import com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta.Consulta;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico.Medico;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente.Paciente;
import com.jorgebandeo.gerenciamentodeconsultasmedicas.R;

import java.util.ArrayList;

public class database extends SQLiteOpenHelper {
    private static final String LOG = "database";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Consultas";
    private static final String TABLE_CONSULTA= "Consulta";
    private static final String TABLE_MEDICO = "Medico";
    private static final String TABLE_PACIENTE = "Pciente";
    

    private static final String SQL_CREATE_CONSULTA = "CREATE TABLE " + TABLE_CONSULTA + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "medico_id INTEGER, " +
            "dia VARCHAR(10)," +
            "paciente_id INTEGER, " +
            "data_hora_inico DATE, " +
            "data_hora_fim DATE, " +
            "observacao VARCHAR(200))";
    private static final String SQL_CREATE_MEDICO = "CREATE TABLE " + TABLE_MEDICO + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(50), " +
            "crm VARCHAR(50), " +
            "celular VARCHAR(20), " +
            "fixo VARCHAR(20))";
    private static final String SQL_CREATE_PACIENTE = "CREATE TABLE " + TABLE_PACIENTE + "(" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome VARCHAR(50), " +
            "grp_sanguineo TINYINT(1), " +
            "celular VARCHAR(20), " +
            "fixo VARCHAR(20))";
    private static final String SQL_DELETE_CONSULTA = "DROP TABLE IF EXISTS " + TABLE_CONSULTA;
    private static final String SQL_DELETE_MEDICO = "DROP TABLE IF EXISTS " + TABLE_MEDICO;
    private static final String SQL_DELETE_PACIENTE = "DROP TABLE IF EXISTS " + TABLE_PACIENTE;

    public database(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_CONSULTA);
        db.execSQL(SQL_CREATE_MEDICO);
        db.execSQL(SQL_CREATE_PACIENTE);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_CONSULTA);
        db.execSQL(SQL_DELETE_MEDICO);
        db.execSQL(SQL_DELETE_PACIENTE);
        onCreate(db);
    }

    /* Início CRUD Medico */
    public long createMedico(@NonNull Medico m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("crm", m.getCrm());
        cv.put("celular", m.getCelular());
        cv.put("fixo", m.getFixo());

        long id = db.insert(TABLE_MEDICO, null, cv);
        db.close();
        return id;
    }

    public long updateMedico(@NonNull Medico m ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", m.getNome());
        cv.put("crm", m.getCrm());
        cv.put("celular", m.getCelular());
        cv.put("fixo", m.getFixo());
        long rows = db.update(TABLE_MEDICO, cv, "_id = ?", new String[]{String.valueOf(m.get_id())});
        db.close();
        return rows;
    }

    public long deleteMedico(@NonNull Medico m) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_MEDICO, "_id = ?", new String[]{String.valueOf(m.get_id())});
        db.close();
        return rows;
    }

    public void getAllMedico(Context context, @NonNull ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "crm", "celular", "fixo"};
        Cursor data = db.query(TABLE_MEDICO, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.ItemIdMedico, R.id.ItemNomeMedico,
                R.id.ItemCrmMedico, R.id.ItemCelularMedico, R.id.ItemFixoMedico};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.fragment_itens_medico, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();

    }

    public void getNameAllMedico (ArrayList<Integer> listMedicoId, ArrayList<String> listMedicoNome) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_MEDICO, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listMedicoId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listMedicoNome.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    public Medico getMedico (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "crm", "celular", "fixo"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_MEDICO, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Medico m = new Medico();
        m.set_id(data.getInt(0));
        m.setNome(data.getString(1));
        m.setCrm(data.getString(2));
        m.setCelular(data.getInt(3));
        m.setFixo(data.getInt(4));

        data.close();
        db.close();
        return m;
    }
    /* Fim CRUD Medico */

    /* Início CRUD Paciente */
    public long createPaciente(@NonNull Paciente p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", p.getNome());
        cv.put("grp_sanguineo", p.getGrp_sanguineo());
        cv.put("celular", p.getCelular());
        cv.put("fixo", p.getFixo());

        long id = db.insert(TABLE_PACIENTE, null, cv);
        db.close();
        return id;
    }

    public long updatePaciente(@NonNull Paciente p ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nome", p.getNome());
        cv.put("grp_sanguineo", p.getGrp_sanguineo());
        cv.put("celular", p.getCelular());
        cv.put("fixo", p.getFixo());
        long rows = db.update(TABLE_PACIENTE, cv, "_id = ?", new String[]{String.valueOf(p.getId())});
        db.close();
        return rows;
    }

    public long deletePaciente(@NonNull Paciente p) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_PACIENTE, "_id = ?", new String[]{String.valueOf(p.getId())});
        db.close();
        return rows;
    }

    public void getAllPaciente (Context context, @NonNull ListView lv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "grp_sanguineo", "celular", "fixo"};
        Cursor data = db.query(TABLE_PACIENTE, columns, null, null, null,
                null, "nome");
        int[] to = {R.id.ItemIdPaciente, R.id.ItemNomePaciente,
                R.id.ItemGrup_S_Paciente, R.id.ItemCelularPaciente, R.id.ItemFixoPaciente};
        SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context,
                R.layout.fragment_itens_paciente, data, columns, to, 0);
        lv.setAdapter(simpleCursorAdapter);
        db.close();
    }

    public void getNameAllPaciente (ArrayList<Integer> listPacienteId, ArrayList<String> listPacienteNome) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome"};
        Cursor data = db.query(TABLE_PACIENTE, columns, null, null, null,
                null, "nome");
        while (data.moveToNext()) {
            int idColumnIndex = data.getColumnIndex("_id");
            listPacienteId.add(Integer.parseInt(data.getString(idColumnIndex)));
            int nameColumnIndex = data.getColumnIndex("nome");
            listPacienteNome.add(data.getString(nameColumnIndex));
        }
        db.close();
    }

    public Paciente getPaciente (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "nome", "grp_sanguineo", "celular", "fixo"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_PACIENTE, columns, "_id = ?", args, null,
                null, null);
        data.moveToFirst();
        Paciente p = new Paciente();
        p.setId(data.getInt(0));
        p.setNome(data.getString(1));
        p.setGrp_sanguineo(data.getString(2));
        p.setCelular(data.getInt(3));
        p.setFixo(data.getInt(4));

        data.close();
        db.close();
        return p;
    }
    /* Fim CRUD Paciente */

    /* Início CRUD Consulta */
    public long createConsulta(@NonNull Consulta c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("medico_id", c.getMedico_id());
        cv.put("paciente_id", c.getPaciente_id());
        cv.put("dia", c.getDia());
        cv.put("data_hora_inico", c.getdata_hora_inicio());
        cv.put("data_hora_fim", c.getdata_hora_fim());
        cv.put("observacao", c.getObservacao());
        long id = db.insert(TABLE_CONSULTA, null, cv);
        db.close();
        return id;
    }

    public long updateConsulta(@NonNull Consulta c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("medico_id", c.getMedico_id());
        cv.put("paciente_id", c.getPaciente_id());
        cv.put("dia", c.getDia());
        cv.put("data_hora_inico", c.getdata_hora_inicio());
        cv.put("data_hora_fim", c.getdata_hora_fim());
        cv.put("observacao", c.getObservacao());
        long rows = db.update(TABLE_CONSULTA, cv, "_id = ?", new String[]{String.valueOf(c.get_id())});
        db.close();
        return rows;
    }

    public long deleteConsulta(@NonNull Consulta c) {
        SQLiteDatabase db = this.getWritableDatabase();
        long rows = db.delete(TABLE_CONSULTA, "_id = ?", new String[]{String.valueOf(c.get_id())});
        db.close();
        return rows;
    }

    public void getAllConsulta (Context context, @NonNull ListView lv) {
       SQLiteDatabase db = getWritableDatabase();
       String[] columns = {"_id", "data_hora_inico"};
       Cursor data = db.query(TABLE_CONSULTA, columns, null, null , null,null, "data_hora_inico" );
       int[] to = {R.id.textViewIdListarConsulta, R.id.textViewHDIListarConsulta};
       SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(context, R.layout.fragment_itens_consulta, data, columns, to, 0);
       lv.setAdapter(simpleCursorAdapter);
       db.close();
    }

    public Consulta getConsulta (int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] columns = {"_id", "medico_id", "paciente_id", "dia","data_hora_inico", "data_hora_fim", "observacao"};
        String[] args = {String.valueOf(id)};
        Cursor data = db.query(TABLE_CONSULTA, columns, "_id = ?", args, null,null, null);
        data.moveToFirst();
        Consulta c = new Consulta();
        c.set_id(data.getInt(0));
        c.setMedico_id(data.getInt(1));
        c.setPaciente_id(data.getInt(2));
        c.setDia(data.getString(3));
        c.setdata_hora_inicio(data.getString(4));
        c.setdata_hora_fim(data.getString(5));
        c.setObservacao(data.getString(6));
        data.close();
        db.close();
        return c;
    }
    /* Fim CRUD Consulta */

}