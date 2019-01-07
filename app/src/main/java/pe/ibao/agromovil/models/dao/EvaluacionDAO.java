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
import pe.ibao.agromovil.models.vo.entitiesInternal.EvaluacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.MuestraVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.RecomendacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_IDTIPOINSPECCION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_IDVISITA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_LATITUD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_LONGITUD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_QR;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_TIMEFIN;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EVALUACION_COL_TIMEINI;

public class EvaluacionDAO {
    private Context ctx;

    public EvaluacionDAO(Context ctx) {
        this.ctx = ctx;
    }

    public boolean editarIdTipoInspeccion(int id,int idTipoInspeccion){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_EVALUACION_COL_IDTIPOINSPECCION,String.valueOf(idTipoInspeccion));
        int res = db.update(TABLE_EVALUACION,values,TABLE_EVALUACION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return flag;
    }
    public int clearTableUpload(int idVisita){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();

        List<EvaluacionVO> evas = new EvaluacionDAO(ctx).listarByIdVisita(idVisita);

        String[] parametros = {String.valueOf(idVisita)};
        int res = db.delete(Utilities.TABLE_EVALUACION,TABLE_EVALUACION_COL_IDVISITA+"=?",parametros);

        Log.d("borrando",String.valueOf(res));

        for(EvaluacionVO ev : evas){
            //eliminando muestras
            List<MuestraVO> muestras = new MuestrasDAO(ctx).listarByIdEvaluacion(ev.getId());
            for(MuestraVO m : muestras){
                new MuestrasDAO(ctx).clearTableUpload(m.getId());
            }
        }

        return res;
    }



    public boolean editarQR(int id,String qr){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_EVALUACION_COL_QR,String.valueOf(qr));
        int res = db.update(TABLE_EVALUACION,values,TABLE_EVALUACION_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();

        return flag;
    }

    public EvaluacionVO consultarById(int  id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        EvaluacionVO temp = null;
        try{
            String consulta = "SELECT " +
                    "E."+TABLE_EVALUACION_COL_ID+       ", "+
                    "E."+TABLE_EVALUACION_COL_TIMEINI+  ", "+
                    "E."+TABLE_EVALUACION_COL_TIMEFIN+  ", "+
                    "E."+TABLE_EVALUACION_COL_QR+       ", "+
                    "E."+TABLE_EVALUACION_COL_LATITUD+  ", "+
                    "E."+TABLE_EVALUACION_COL_LONGITUD+ ", "+
                    "E."+TABLE_EVALUACION_COL_IDTIPOINSPECCION+", "+
                    "E."+TABLE_EVALUACION_COL_IDVISITA+
                    " FROM "+
                    TABLE_EVALUACION+" as E "+
                    " WHERE "+
                    "E."+TABLE_EVALUACION_COL_ID+"="+String.valueOf(id);

            Cursor cursor = db.rawQuery(consulta,null);
            Log.d("consult",consulta);
            Log.d("consult",""+cursor.getCount());
            Log.d("consult","contando por "+id);
            if(cursor.getCount()>=0) {
                cursor.moveToFirst();
                temp = new EvaluacionVO();
                temp.setId(cursor.getInt(0));
                temp.setTimeIni(cursor.getString(1));
                temp.setTimeFin(cursor.getString(2));
                temp.setQr(cursor.getString(3));
                temp.setLat(cursor.getDouble(4));
                temp.setLon(cursor.getDouble(5));
                temp.setIdTipoInspeccion(cursor.getInt(6));
                temp.setIdVisita(cursor.getInt(7));
                if (temp.getIdTipoInspeccion()>0){
                    temp.setNameInspeccion(new TipoInspeccionDAO(ctx).consultarByid(temp.getIdTipoInspeccion()).getName());
                }
                List<MuestraVO> listMuestras =  new MuestrasDAO(ctx).listarByIdEvaluacion(temp.getId());
                float mtotal= (int)listMuestras.size();
                float sinllenar=0;

                for( MuestraVO m : listMuestras ) {
                    if (m.getValue().equals("") || m.getValue().isEmpty())
                    {
                        if(
                                m.getType().equals("boolean")
                                        ||
                                        m.getType().equals("list")
                                ){
                            //sinllenar = sinllenar + 1;
                            Log.d("porcentaje","+0 "+sinllenar+ " "+m.getType()+" "+m.getValue() );
                        }else{
                            sinllenar = sinllenar + 1;
                            Log.d("porcentaje","+1 "+sinllenar+ " "+m.getType()+" "+m.getValue() );
                        }

                    }
                    Log.d("porcentaje",m.getType()+" "+m.getValue());

                }


                int por = (int)((((mtotal*1.0)-sinllenar)/mtotal)*100);
                Log.d("porcentaje"," "+mtotal+" "+ sinllenar+" "+por );
                temp.setPorcentaje(por);
            }
            cursor.close();

        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return temp;
    }

    public EvaluacionVO nuevoByIdVisita(Double lat, Double lon, Integer idVisita){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
            values.put(Utilities.TABLE_EVALUACION_COL_LATITUD,lat);
            values.put(Utilities.TABLE_EVALUACION_COL_LONGITUD,lon);
            values.put(Utilities.TABLE_EVALUACION_COL_IDVISITA,idVisita.toString());
        int temp = (int) db.insert(TABLE_EVALUACION,TABLE_EVALUACION_COL_ID,values);
        Log.d("consultasuccess","idEva->"+temp);
        EvaluacionVO ev  = consultarById(temp);
        Log.d("consultasuccess","idEva-->"+ev.getId());
        db.close();
        return ev;
    }

    public int borrarById(int id){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {String.valueOf(id)};
        int res =db.delete(Utilities.TABLE_EVALUACION,TABLE_EVALUACION_COL_ID+"=?",parametros);
        Log.d("borrando",String.valueOf(res));

        //eliminando muestras
        List<MuestraVO> muestras = new MuestrasDAO(ctx).listarByIdEvaluacion(id);

        for(MuestraVO m : muestras){
            new MuestrasDAO(ctx).borrarMuestraById(m.getId());
        }
        return res;
    }

    public int borrarByIdVisita(int idVisita){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        List<EvaluacionVO> evas = new EvaluacionDAO(ctx).listarByIdVisita(idVisita);

        String[] parametros = {String.valueOf(idVisita)};
        int res = db.delete(Utilities.TABLE_EVALUACION,TABLE_EVALUACION_COL_IDVISITA+"=?",parametros);

        Log.d("borrando",String.valueOf(res));

        for(EvaluacionVO ev : evas){
            //eliminando muestras
            List<MuestraVO> muestras = new MuestrasDAO(ctx).listarByIdEvaluacion(ev.getId());
            for(MuestraVO m : muestras){
                new MuestrasDAO(ctx).borrarMuestraById(m.getId());
            }
        }

        return res;
    }






    public List<EvaluacionVO> listarByIdVisita(int idVisita){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionVO> evaluacionVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EVALUACION_COL_ID+", " +//0
                            "E."+TABLE_EVALUACION_COL_TIMEINI+", " +//1
                            "E."+TABLE_EVALUACION_COL_TIMEFIN+", "+//2
                            "E."+TABLE_EVALUACION_COL_QR+", "+//3
                            "E."+TABLE_EVALUACION_COL_LATITUD+", "+//4
                            "E."+TABLE_EVALUACION_COL_LONGITUD+", "+//5
                            "E."+TABLE_EVALUACION_COL_IDTIPOINSPECCION+", "+//6
                            "E."+TABLE_EVALUACION_COL_IDVISITA+" "+//7
                        " FROM "+
                            TABLE_EVALUACION+" as E " +
                        " WHERE "+
                            "E."+TABLE_EVALUACION_COL_IDVISITA+" = "+String.valueOf(idVisita)
                    ,null);
            while (cursor.moveToNext() && cursor.getCount()>0){
                EvaluacionVO temp = new EvaluacionVO();
                    temp.setId(cursor.getInt(0));
                    temp.setTimeIni(cursor.getString(1));
                    temp.setTimeFin(cursor.getString(2));
                    temp.setQr(cursor.getString(3));
                    temp.setLat(cursor.getDouble(4));
                    temp.setLon(cursor.getDouble(5));
                    temp.setIdTipoInspeccion(cursor.getInt(6));
                    temp.setIdVisita(cursor.getInt(7));
                if (temp.getIdTipoInspeccion()>0){
                    temp.setNameInspeccion(new TipoInspeccionDAO(ctx).consultarByid(temp.getIdTipoInspeccion()).getName());
                }

                List<MuestraVO> listMuestras =  new MuestrasDAO(ctx).listarByIdEvaluacion(temp.getId());
                float mtotal= (int)listMuestras.size();
                float sinllenar=0;

                for( MuestraVO m : listMuestras ) {
                    if (m.getValue().equals("") || m.getValue().isEmpty())
                        {
                            if(
                                    m.getType().equals("boolean")
                                    ||
                                    m.getType().equals("list")
                            ){
                                //sinllenar = sinllenar + 1;
                                Log.d("porcentaje","+0 "+sinllenar+ " "+m.getType()+" "+m.getValue() );
                            }else{
                                sinllenar = sinllenar + 1;
                                Log.d("porcentaje","+1 "+sinllenar+ " "+m.getType()+" "+m.getValue() );
                            }

                        }
                        Log.d("porcentaje",m.getType()+" "+m.getValue());

                }


                int por = (int)((((mtotal*1.0)-sinllenar)/mtotal)*100);
                Log.d("porcentaje"," "+mtotal+" "+ sinllenar+" "+por );
                temp.setPorcentaje(por);
                evaluacionVOS.add(temp);

            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return evaluacionVOS;
    }




    public List<EvaluacionVO> listarAll(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<EvaluacionVO> evaluacionVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EVALUACION_COL_ID+", " +//0
                            "E."+TABLE_EVALUACION_COL_TIMEINI+", " +//1
                            "E."+TABLE_EVALUACION_COL_TIMEFIN+", "+//2
                            "E."+TABLE_EVALUACION_COL_QR+", "+//3
                            "E."+TABLE_EVALUACION_COL_LATITUD+", "+//4
                            "E."+TABLE_EVALUACION_COL_LONGITUD+", "+//5
                            "E."+TABLE_EVALUACION_COL_IDTIPOINSPECCION+", "+//6
                            "E."+TABLE_EVALUACION_COL_IDVISITA+" "+//7
                            " FROM "+
                            TABLE_EVALUACION+" as E "// +
                          //  " WHERE "+
                         //   "E."+TABLE_EVALUACION_COL_IDVISITA+" = "+String.valueOf(idVisita)
                    ,null);
            while (cursor.moveToNext() && cursor.getCount()>0){
                EvaluacionVO temp = new EvaluacionVO();
                    temp.setId(cursor.getInt(0));
                    temp.setTimeIni(cursor.getString(1));
                    temp.setTimeFin(cursor.getString(2));
                    temp.setQr(cursor.getString(3)==null?"No Especifica":cursor.getString(3));
                    temp.setLat(cursor.getDouble(4));
                    temp.setLon(cursor.getDouble(5));
                    temp.setIdTipoInspeccion(cursor.getInt(6));
                    temp.setIdVisita(cursor.getInt(7));
                    evaluacionVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
            Log.d("errorCargaEvaluacion",e.toString());
        }
        return evaluacionVOS;
    }
}
