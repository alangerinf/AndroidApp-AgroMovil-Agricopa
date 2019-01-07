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
import pe.ibao.agromovil.models.vo.entitiesInternal.MuestraVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_MAGNITUD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIO_COL_TIPO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_COMENTARIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_IDCRITERIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_IDEVALUACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_IDTIPOINSPECCION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_TIME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA_COL_VALUE;

public class MuestrasDAO {

    Context ctx;

    public MuestrasDAO(Context ctx){
        this.ctx = ctx;
    }

    public List<MuestraVO> listarByIdEvaluacion(int idEvaluacion){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<MuestraVO> muestraVOList =  new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "M."+TABLE_MUESTRA_COL_ID+", " +
                        "M."+TABLE_MUESTRA_COL_VALUE+", " +
                        "M."+TABLE_MUESTRA_COL_IDCRITERIO+", "+
                        "M."+TABLE_MUESTRA_COL_IDEVALUACION+", "+
                        "M."+TABLE_MUESTRA_COL_TIME+", "+
                        "M."+TABLE_MUESTRA_COL_COMENTARIO+", "+
                        "M."+TABLE_MUESTRA_COL_IDTIPOINSPECCION+
                    " FROM "+
                        TABLE_MUESTRA+" as M"+
                    " WHERE "+
                        "M."+TABLE_MUESTRA_COL_IDEVALUACION+"="+String.valueOf(idEvaluacion)
                ,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                MuestraVO temp= new MuestraVO();
                temp.setId(cursor.getInt(0));
                temp.setValue(cursor.getString(1));
                temp.setIdCriterio(cursor.getInt(2));
                temp.setIdEvaluacion(cursor.getInt(3));
                temp.setTime(cursor.getString(4     ));
                temp.setComent(cursor.getString(5));
                temp.setIdTipoInspeccion(cursor.getInt(6));
                if(temp.getComent().equals("") || temp.getComent()==null){
                    temp.setStatusComent(false);
                }else{
                    temp.setStatusComent(true);
                }
                CriterioDAO criterioDAO = new CriterioDAO(ctx);
                CriterioVO cri = criterioDAO.consultarById(temp.getIdCriterio());
                if(temp!=null){
                    Log.d("locomata","temp is null");
                    temp.setIdTipoInspseccion(cri.getIdTipoInspseccion());
                    temp.setName(cri.getName());
                    temp.setType(cri.getType());
                    temp.setMagnitud(cri.getMagnitud());
                }else{
                    Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
                }
                muestraVOList.add(temp);
            }

        }
        cursor.close();
        c.close();
        return muestraVOList;

    }

    public boolean clearTableUpload(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_MUESTRA,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();

        //new FotoDAO(ctx).borrarByIdMuestra(id);

        return flag;
    }


    public MuestraVO nuevoByIdEvaluacionIdCriterio(int idEvaluacion, int idCriterio,int idTipoInspecicon) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        Log.d("locomata","datos recibidos e funcion : "+idEvaluacion+" "+idCriterio);

        SQLiteDatabase db = conn.getWritableDatabase();
        MuestraVO res = null;
        ContentValues values = new ContentValues();
            values.put(Utilities.TABLE_MUESTRA_COL_VALUE,"");
            values.put(Utilities.TABLE_MUESTRA_COL_IDCRITERIO,String.valueOf(idCriterio));
            values.put(Utilities.TABLE_MUESTRA_COL_IDTIPOINSPECCION,String.valueOf(idTipoInspecicon));
            values.put(Utilities.TABLE_MUESTRA_COL_IDEVALUACION,String.valueOf(idEvaluacion));
        Long id = db.insert(Utilities.TABLE_MUESTRA,Utilities.TABLE_MUESTRA_COL_ID,values);

        Log.d("locomata","id insertado : "+id);

        //obteniendo datos extra de tablas alternas
        Cursor cursor = db.rawQuery(
                "SELECT " +
                    "M."+TABLE_MUESTRA_COL_ID+", " +
                    "M."+TABLE_MUESTRA_COL_VALUE+", " +
                    "M."+TABLE_MUESTRA_COL_IDCRITERIO+", "+
                    "M."+TABLE_MUESTRA_COL_IDEVALUACION+", "+
                    "M."+TABLE_MUESTRA_COL_TIME+", "+
                    "M."+TABLE_MUESTRA_COL_COMENTARIO+", "+
                    "M."+TABLE_MUESTRA_COL_IDTIPOINSPECCION+
                " FROM "+
                TABLE_MUESTRA+" as M"+
                " WHERE "+
                "M."+TABLE_MUESTRA_COL_ID+"="+String.valueOf(id)
                , null);
        Log.d("locomata","cantidad isnercio"+cursor.getCount());
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            res = new MuestraVO();
            res.setId(cursor.getInt(0));
            res.setValue(cursor.getString(1));
            res.setIdCriterio(cursor.getInt(2));
            res.setIdEvaluacion(cursor.getInt(3));
            res.setTime(cursor.getString(4));
            res.setComent(cursor.getString(5));
            res.setIdTipoInspeccion(cursor.getInt(6));
            if(res.getComent().equals("") || res.getComent()==null){
                res.setStatusComent(false);
            }else{
                res.setStatusComent(true);
            }
            CriterioDAO criterioDAO = new CriterioDAO(ctx);
            CriterioVO temp = criterioDAO.consultarById(res.getIdCriterio());
            if(temp!=null){
                Log.d("locomata","temp is null");
                res.setIdTipoInspseccion(temp.getIdTipoInspseccion());
                res.setName(temp.getName());
                res.setType(temp.getType());
                res.setMagnitud(temp.getMagnitud());
            }else{
                Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
            }

            Log.d("locomata","datos encontrados"+
                    res.getId()+" "+
                    res.getIdCriterio()+" "+
            res.getIdEvaluacion()+" "+
            res.getMagnitud()+" "+
            res.getIdCriterio()+" "+
            res.getValue()+" "+
            res.getName()+" "+
            res.getType());
        }
        cursor.close();
        db.close();
        conn.close();
        return res;
    }
    public MuestraVO consultById(int idMuestra) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        Log.d("locomata","datos recibidos e funcion : "+idMuestra);

        SQLiteDatabase db = conn.getWritableDatabase();
        MuestraVO res = null;


        //obteniendo datos extra de tablas alternas
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "M."+TABLE_MUESTRA_COL_ID+", " +
                        "M."+TABLE_MUESTRA_COL_VALUE+", " +
                        "M."+TABLE_MUESTRA_COL_IDCRITERIO+", "+
                        "M."+TABLE_MUESTRA_COL_IDEVALUACION+", "+
                        "M."+TABLE_MUESTRA_COL_TIME+", "+
                        "M."+TABLE_MUESTRA_COL_COMENTARIO+", "+
                        "M."+TABLE_MUESTRA_COL_IDTIPOINSPECCION+
                        " FROM "+
                        TABLE_MUESTRA+" as M"+
                        " WHERE "+
                        "M."+TABLE_MUESTRA_COL_ID+"="+String.valueOf(idMuestra)
                , null);
        Log.d("locomata","cantidad isnercio"+cursor.getCount());
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            res = new MuestraVO();
            res.setId(cursor.getInt(0));
            res.setValue(cursor.getString(1));
            res.setIdCriterio(cursor.getInt(2));
            res.setIdEvaluacion(cursor.getInt(3));
            res.setTime(cursor.getString(4));
            res.setComent(cursor.getString(5));
            res.setIdTipoInspeccion(cursor.getInt(6));
            if(res.getComent().equals("") || res.getComent()==null){
                res.setStatusComent(false);
            }else{
                res.setStatusComent(true);
            }
            CriterioDAO criterioDAO = new CriterioDAO(ctx);
            CriterioVO temp = criterioDAO.consultarById(res.getIdCriterio());
            if(temp!=null){
                Log.d("locomata","temp is null");
                res.setIdTipoInspseccion(temp.getIdTipoInspseccion());
                res.setName(temp.getName());
                res.setType(temp.getType());
                res.setMagnitud(temp.getMagnitud());
            }else{
                Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
            }

            Log.d("locomata","datos encontrados"+
                    res.getId()+" "+
                    res.getIdCriterio()+" "+
                    res.getIdEvaluacion()+" "+
                    res.getMagnitud()+" "+
                    res.getIdCriterio()+" "+
                    res.getValue()+" "+
                    res.getName()+" "+
                    res.getType());
        }
        cursor.close();
        db.close();
        conn.close();
        return res;
    }
    public boolean editarValorById(int id,String valor){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_MUESTRA_COL_VALUE,valor);
        int res = db.update(TABLE_MUESTRA,values,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean editarComentById(int id,String coment){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_MUESTRA_COL_COMENTARIO,coment);
        int res = db.update(TABLE_MUESTRA,values,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean borrarMuestraById(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_MUESTRA,TABLE_MUESTRA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();

        new FotoDAO(ctx).borrarByIdMuestra(id);

        return flag;
    }




    public boolean borrarValorByIdEvaluacion(int idEvaluacion){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(idEvaluacion),
                };

        int res = db.delete(TABLE_MUESTRA,TABLE_MUESTRA_COL_IDEVALUACION+"=?",parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public List<MuestraVO> listarAll(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<MuestraVO> muestraVOList =  new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "M."+TABLE_MUESTRA_COL_ID+", " +
                        "M."+TABLE_MUESTRA_COL_VALUE+", " +
                        "M."+TABLE_MUESTRA_COL_IDCRITERIO+", "+
                        "M."+TABLE_MUESTRA_COL_IDEVALUACION+", "+
                        "M."+TABLE_MUESTRA_COL_TIME+", "+
                        "M."+TABLE_MUESTRA_COL_COMENTARIO+", "+
                        "M."+TABLE_MUESTRA_COL_IDTIPOINSPECCION+
                        " FROM "+
                        TABLE_MUESTRA+" as M"//+
                     //   " WHERE "+
                    //    "M."+TABLE_MUESTRA_COL_IDEVALUACION+"="+String.valueOf(idEvaluacion)
                ,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                MuestraVO temp= new MuestraVO();
                temp.setId(cursor.getInt(0));
                temp.setValue(cursor.getString(1));
                temp.setIdCriterio(cursor.getInt(2));
                temp.setIdEvaluacion(cursor.getInt(3));
                temp.setTime(cursor.getString(4     ));
                temp.setComent(cursor.getString(5));
                temp.setIdTipoInspeccion(cursor.getInt(6));
                if(temp.getComent().equals("") || temp.getComent()==null){
                    temp.setStatusComent(false);
                }else{
                    temp.setStatusComent(true);
                }
                CriterioDAO criterioDAO = new CriterioDAO(ctx);
                CriterioVO cri = criterioDAO.consultarById(temp.getIdCriterio());
                if(temp!=null){
                    Log.d("locomata","temp is null");
                    temp.setIdTipoInspseccion(cri.getIdTipoInspseccion());
                    temp.setName(cri.getName());
                    temp.setType(cri.getType());
                    temp.setMagnitud(cri.getMagnitud());
                }else{
                    Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
                }
                muestraVOList.add(temp);
            }

        }
        cursor.close();
        c.close();
        return muestraVOList;

    }
}
