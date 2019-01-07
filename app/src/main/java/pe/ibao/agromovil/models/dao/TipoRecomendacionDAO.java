package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesDB.TipoRecomendacionVO;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDFUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_TIPORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_TIPORECOMENDACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_TIPORECOMENDACION_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD_COL_ID;

public class TipoRecomendacionDAO {

    Context ctx;

    public TipoRecomendacionDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean insertarTipoRecomendacion(int id, String name){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TIPORECOMENDACION_COL_ID,id);
        values.put(TABLE_TIPORECOMENDACION_COL_NAME,name);
        Long temp = db.insert(TABLE_TIPORECOMENDACION,TABLE_TIPORECOMENDACION_COL_ID,values);
        db.close();
        return temp > 0;
    }

    public TipoRecomendacionVO consultarByid(int id){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TipoRecomendacionVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "TR."+TABLE_TIPORECOMENDACION_COL_ID+", " +
                            "TR."+TABLE_TIPORECOMENDACION_COL_NAME+
                            " FROM "+
                            TABLE_TIPORECOMENDACION+" as TR "+
                            " WHERE "+
                            "TR."+TABLE_TIPORECOMENDACION_COL_ID+"="+String.valueOf(id)
                    ,null);

            if(cursor.getCount()>0){
                temp = new TipoRecomendacionVO();
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
            }
            cursor.close();

        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        }
        c.close();
        return temp;
    }

    public List<TipoRecomendacionVO> listarByIdFundoIdVariedad(int idFundo, int idVariedad){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<TipoRecomendacionVO> tipoRecomendacionVOS = new ArrayList<>();

        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "TR."+TABLE_TIPORECOMENDACION_COL_ID+", "+
                            "TR."+TABLE_TIPORECOMENDACION_COL_NAME+
                            " FROM "+
                            TABLE_CONFIGURACIONRECOMENDACION+" as COR,"+
                            TABLE_CRITERIORECOMENDACION+" as CR,"+
                            TABLE_TIPORECOMENDACION+" as TR,"+
                            TABLE_FUNDOVARIEDAD+" as FV"+
                            " WHERE "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+String.valueOf(idVariedad)+
                            " AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+String.valueOf(idFundo)+
                            " AND "+
                            "COR."+TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD+"="+"FV."+TABLE_FUNDOVARIEDAD_COL_ID+
                            " AND "+
                            "COR."+TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION+"="+"CR."+TABLE_CRITERIORECOMENDACION_COL_ID+
                            " AND "+
                            "CR."+TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION+"="+"TR."+TABLE_TIPORECOMENDACION_COL_ID+
                            " GROUP BY "+
                            "TR."+TABLE_TIPORECOMENDACION_COL_ID+
                            " ORDER BY "+
                            "TR."+TABLE_TIPORECOMENDACION_COL_NAME+
                            " COLLATE UNICODE ASC"
                    , null);

            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    TipoRecomendacionVO temp = new TipoRecomendacionVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    tipoRecomendacionVOS.add(temp);
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        }
        c.close();
        return tipoRecomendacionVOS;
    }


    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        int res = db.delete(TABLE_TIPORECOMENDACION,null,null);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }




}
