package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesDB.CriterioRecomendacionVO;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_LISTFRECUENCIAS;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_LISTUNIDADES;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDFUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD;

public class CriterioRecomendacionDAO {

    private Context ctx;

    public CriterioRecomendacionDAO(Context ctx){
        this.ctx = ctx;
    }
    public CriterioRecomendacionVO consultarById(int  id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CriterioRecomendacionVO temp = null;
        try{
            String consulta = "SELECT " +
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_ID+", "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_NAME+", "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_LISTUNIDADES+", "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_LISTFRECUENCIAS+", "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION+
                    " FROM "+
                    TABLE_CRITERIORECOMENDACION+" as CR "+
                    " WHERE "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_ID+"="+String.valueOf(id);
            Cursor cursor = db.rawQuery(consulta,null);
            if(cursor.getCount()>=0) {
                cursor.moveToFirst();
                temp = new CriterioRecomendacionVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setListUnidades(cursor.getString(2));
                temp.setListFrecuancias(cursor.getString(3));
                temp.setIdTipoRecomendacion(cursor.getInt(4));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return temp;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CRITERIORECOMENDACION,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public CriterioRecomendacionVO insertar(int id, String name, String listUnidades,String listFrecuencias,int idTipoRecomendacion){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CRITERIORECOMENDACION_COL_ID,id);
        values.put(TABLE_CRITERIORECOMENDACION_COL_NAME,name);
        values.put(TABLE_CRITERIORECOMENDACION_COL_LISTUNIDADES,listUnidades);
        values.put(TABLE_CRITERIORECOMENDACION_COL_LISTFRECUENCIAS,listFrecuencias);
        values.put(TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION,idTipoRecomendacion);
        int temp = (int) db.insert(TABLE_CRITERIORECOMENDACION,TABLE_CRITERIORECOMENDACION_COL_ID,values);
        Log.d("consultasuccess CR","idEva->"+temp);
        CriterioRecomendacionVO ev  = consultarById(temp);
        Log.d("consultasuccess CR","idEva-->"+ev.getId());
        db.close();
        return ev;
    }

    public List<CriterioRecomendacionVO> listarByIdTipoRecomendacionIdFundoIdVariedad(int idTipoRecomendacion,int idFundo,int idVariedad){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<CriterioRecomendacionVO> criterioRecomendacionVOS = new ArrayList<>();
        try{

            String consulta = "SELECT " +
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_ID+", " +//0
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_NAME+", " +//1
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_LISTUNIDADES+", "+//2
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_LISTFRECUENCIAS+", "+//3
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION+//4
                    " FROM "+
                    TABLE_CRITERIORECOMENDACION+" as CR ," +
                    TABLE_FUNDOVARIEDAD+" as FV ,"+
                    TABLE_CONFIGURACIONRECOMENDACION+" as COR"+
                    " WHERE "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION+"="+String.valueOf(idTipoRecomendacion)+
                    " AND "+
                    "COR."+TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION+"="+"CR."+TABLE_CRITERIORECOMENDACION_COL_ID+
                    " AND "+
                    "COR."+TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD+"="+"FV."+TABLE_FUNDOVARIEDAD_COL_ID+
                    " AND "+
                    "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+String.valueOf(idFundo)+
                    " AND "+
                    "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+String.valueOf(idVariedad)+
                    " ORDER BY "+
                    "CR."+TABLE_CRITERIORECOMENDACION_COL_NAME+
                    " COLLATE UNICODE ASC";
            Cursor cursor = db.rawQuery(consulta
                    ,null);
            Log.d("consultalsitaCR",consulta);
            while (cursor.moveToNext() && cursor.getCount()>0){
                CriterioRecomendacionVO temp;
                temp = new CriterioRecomendacionVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setListUnidades(cursor.getString(2));
                temp.setListFrecuancias(cursor.getString(3));
                temp.setIdTipoRecomendacion(cursor.getInt(4));
                criterioRecomendacionVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return criterioRecomendacionVOS;
    }


}
