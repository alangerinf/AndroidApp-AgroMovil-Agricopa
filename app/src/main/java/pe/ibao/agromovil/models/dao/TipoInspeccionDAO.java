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
import pe.ibao.agromovil.models.vo.entitiesDB.EmpresaVO;
import pe.ibao.agromovil.models.vo.entitiesDB.TipoInspeccionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.EvaluacionVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.*;

public class TipoInspeccionDAO {

    Context ctx;

    public TipoInspeccionDAO(Context ctx) {

        this.ctx=ctx;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        int res = db.delete(TABLE_TIPOINSPECCION,null,null);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertarTipoInspeccion(int id, String name){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_TIPOINSPECCION_COL_ID,id);
        values.put(TABLE_TIPOINSPECCION_COL_NAME,name);
        Long temp = db.insert(TABLE_TIPOINSPECCION,TABLE_TIPOINSPECCION_COL_ID,values);
        db.close();
        return temp > 0;
    }
    public TipoInspeccionVO consultarByid(int id){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        TipoInspeccionVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "TI."+TABLE_TIPOINSPECCION_COL_ID+", " +
                            "TI."+TABLE_TIPOINSPECCION_COL_NAME+
                        " FROM "+
                            TABLE_TIPOINSPECCION+" as TI "+
                        " WHERE "+
                            "TI."+TABLE_TIPOINSPECCION_COL_ID+"="+String.valueOf(id)
                    ,null);

            if(cursor.getCount()>0){
                temp = new TipoInspeccionVO();
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



    public List<TipoInspeccionVO> listarByIdFundoAndIdVariedad(int idFundo, int idVariedad){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<TipoInspeccionVO> tipoInspeccionVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "TI."+TABLE_TIPOINSPECCION_COL_ID+", "+
                            "TI."+TABLE_TIPOINSPECCION_COL_NAME+
                        " FROM "+
                            TABLE_VARIEDAD+" as V, "+
                            TABLE_FUNDO+" as F,"+
                            TABLE_FUNDOVARIEDAD+" as FV,"+
                            TABLE_TIPOINSPECCION+" as TI,"+
                            TABLE_CRITERIO+" as C,"+
                            TABLE_CONFIGURACIONCRITERIO+" as CC"+
                        " WHERE "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+String.valueOf(idFundo)+" AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+String.valueOf(idVariedad)+" AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_ID+"="+"CC."+TABLE_CONFIGURACIONCRITERIO_COL_IDFUNDOVARIEDAD+" AND "+
                            "CC."+TABLE_CONFIGURACIONCRITERIO_COL_IDCRITERIO+"="+"C."+TABLE_CRITERIO_COL_ID+" AND "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOINSPECCION+"="+"TI."+TABLE_TIPOINSPECCION_COL_ID+
                        " GROUP BY "+
                            "TI."+TABLE_TIPOINSPECCION_COL_ID+
                        " ORDER BY "+
                            "TI."+TABLE_TIPOINSPECCION_COL_NAME+
                            " COLLATE UNICODE ASC"
                    , null);
            Log.d("eva123","idFundo "+idFundo);
            Log.d("eva123","idVariedad "+idVariedad);
            Log.d("eva123","consulta dir retorna "+cursor.getCount());
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    TipoInspeccionVO temp = new TipoInspeccionVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    tipoInspeccionVOS.add(temp);
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("tipoinspeccionDAO",e.toString());
        }
        c.close();
        return tipoInspeccionVOS;
    }


    public List<TipoInspeccionVO> listarFaltantesByIdFundoAndIdVariedadIdVisitaIdEvaluacion(int idFundo, int idVariedad, int idVisita, int idEvaluacion){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<TipoInspeccionVO> tipoInspeccionVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "TI."+TABLE_TIPOINSPECCION_COL_ID+", "+
                            "TI."+TABLE_TIPOINSPECCION_COL_NAME+
                            " FROM "+
                            TABLE_VARIEDAD+" as V, "+
                            TABLE_FUNDO+" as F,"+
                            TABLE_FUNDOVARIEDAD+" as FV,"+
                            TABLE_TIPOINSPECCION+" as TI,"+
                            TABLE_CRITERIO+" as C,"+
                            TABLE_CONFIGURACIONCRITERIO+" as CC"+
                            " WHERE "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+String.valueOf(idFundo)+" AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+String.valueOf(idVariedad)+" AND "+
                            "FV."+TABLE_FUNDOVARIEDAD_COL_ID+"="+"CC."+TABLE_CONFIGURACIONCRITERIO_COL_IDFUNDOVARIEDAD+" AND "+
                            "CC."+TABLE_CONFIGURACIONCRITERIO_COL_IDCRITERIO+"="+"C."+TABLE_CRITERIO_COL_ID+" AND "+
                            "C."+TABLE_CRITERIO_COL_IDTIPOINSPECCION+"="+"TI."+TABLE_TIPOINSPECCION_COL_ID+
                            " GROUP BY "+
                            "TI."+TABLE_TIPOINSPECCION_COL_ID+
                            " ORDER BY "+
                            "TI."+TABLE_TIPOINSPECCION_COL_NAME+
                            " COLLATE UNICODE ASC"
                    , null);
            Log.d("eva123","idFundo "+idFundo);
            Log.d("eva123","idVariedad "+idVariedad);
            Log.d("eva123","consulta dir retorna "+cursor.getCount());
            if(cursor.getCount()>0){
                while (cursor.moveToNext()){
                    TipoInspeccionVO temp = new TipoInspeccionVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    tipoInspeccionVOS.add(temp);
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("tipoinspeccionDAO",e.toString());
        }
        c.close();

        List<EvaluacionVO> evaluacionDAOList = new EvaluacionDAO(ctx).listarByIdVisita(idVisita);

        Log.d("buscandoTI","tamano"+tipoInspeccionVOS.size());
        for(int i=0;i<tipoInspeccionVOS.size();i++){
            TipoInspeccionVO ti = tipoInspeccionVOS.get(i);
            Log.d("buscandoTI",""+ti.getId()+ti.getName());
        }

        Log.d("buscandoev","tamano"+evaluacionDAOList.size());
        for(int j=0;j<evaluacionDAOList.size()-1;j++){
            EvaluacionVO ev = evaluacionDAOList.get(j);
            Log.d("buscandoEV",""+ev.getIdTipoInspeccion()+ev.getNameInspeccion());
        }

        int idTI = new EvaluacionDAO(ctx).consultarById(idEvaluacion).getIdTipoInspeccion();

        for(int j=0;j<evaluacionDAOList.size()-1;j++){
            EvaluacionVO ev = evaluacionDAOList.get(j);
            Log.d("buscando",""+ev.getIdTipoInspeccion()+ev.getNameInspeccion());
            for(int i=0;i<tipoInspeccionVOS.size();i++){
                TipoInspeccionVO ti = tipoInspeccionVOS.get(i);
                if(ev.getIdTipoInspeccion()== ti.getId() && idTI!= ti.getId()){
                   tipoInspeccionVOS.remove(ti);
                   Log.d("removido",""+ti.getId()+ti.getName());
                    break;
                }
            }
        }
        return tipoInspeccionVOS;
    }
}
