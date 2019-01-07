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
import pe.ibao.agromovil.models.vo.entitiesDB.CriterioVO;
import pe.ibao.agromovil.models.vo.entitiesDB.FundoVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONCRITERIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONCRITERIO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONCRITERIO_COL_IDCRITERIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONCRITERIO_COL_IDFUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_IDTIPOINSPECCION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_MAGNITUD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_TIPO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDFUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_IDEMPRESA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD;

public class CriterioDAO {

    Context ctx;

    public CriterioDAO(Context ctx) {

        this.ctx=ctx;
    }

    public boolean insertarCriterio(int id, String name,String tipo, String magnitud, int idTipoInspeccion){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CRITERIO_COL_ID,id);
        values.put(TABLE_CRITERIO_COL_NAME,name);
        values.put(TABLE_CRITERIO_COL_TIPO,tipo);
        values.put(TABLE_CRITERIO_COL_MAGNITUD,magnitud);
        values.put(TABLE_CRITERIO_COL_IDTIPOINSPECCION,idTipoInspeccion);
        Long temp = db.insert(TABLE_CRITERIO,TABLE_CRITERIO_COL_ID,values);
        db.close();
        return temp > 0;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CRITERIO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CRITERIO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public CriterioVO consultarById(int id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        CriterioVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CRITERIO_COL_ID+", " +
                            "C."+TABLE_CRITERIO_COL_NAME+", " +
                            "C."+TABLE_CRITERIO_COL_TIPO+", "+
                            "C."+TABLE_CRITERIO_COL_MAGNITUD+", "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOINSPECCION+
                    " FROM "+TABLE_CRITERIO+" as C"+
                    " WHERE "+
                            "C."+TABLE_CRITERIO_COL_ID+"="+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0){
                Log.d("locomata","criterio encontrado");
                temp = new CriterioVO();
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setType(cursor.getString(2));
                temp.setMagnitud(cursor.getString(3));
                temp.setIdTipoInspseccion(cursor.getInt(4));

            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }

        c.close();
        return temp;
    }

    public List<CriterioVO> listarByIdTipoInspeccionIdFundoIdVariedad(int idTipoInspeccion, int idFundo, int idVariedad){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<CriterioVO> criterioVOS = new ArrayList<CriterioVO>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "C."+TABLE_CRITERIO_COL_ID+", " +
                            "C."+TABLE_CRITERIO_COL_NAME+", " +
                            "C."+TABLE_CRITERIO_COL_TIPO+", "+
                            "C."+TABLE_CRITERIO_COL_MAGNITUD+", "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOINSPECCION+
                        " FROM "+
                            TABLE_CRITERIO+" AS C, "+
                            TABLE_FUNDOVARIEDAD+" AS FV, "+
                            TABLE_CONFIGURACIONCRITERIO+" AS CC"+
                        " WHERE "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOINSPECCION+"="+  String.valueOf(idTipoInspeccion)+
                            " AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+String.valueOf(idFundo)+
                            " AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+String.valueOf(idVariedad)+
                            " AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_ID+"="+"CC."+TABLE_CONFIGURACIONCRITERIO_COL_IDFUNDOVARIEDAD+
                            " AND "+
                            "CC."+TABLE_CONFIGURACIONCRITERIO_COL_IDCRITERIO+"="+"C."+TABLE_CRITERIO_COL_ID+
                            " ORDER BY "+
                            "C."+TABLE_CRITERIO_COL_NAME+
                            " COLLATE UNICODE ASC"
                    ,null);
            while (cursor.moveToNext()){
                CriterioVO temp = new CriterioVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setType(cursor.getString(2));
                temp.setMagnitud(cursor.getString(3));
                temp.setIdTipoInspseccion(cursor.getInt(4));
                criterioVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return criterioVOS;
    }

}