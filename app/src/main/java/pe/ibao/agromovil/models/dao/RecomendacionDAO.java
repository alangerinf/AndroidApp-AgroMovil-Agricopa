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
import pe.ibao.agromovil.models.vo.entitiesInternal.RecomendacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_CANTIDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_COMENTARIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_FRECUENCIA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_IDVISITA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_RECOMENDACION_COL_UNIDAD;

public class RecomendacionDAO {

    Context ctx;

    public RecomendacionDAO(Context ctx) {
        this.ctx = ctx;
    }


    public List<RecomendacionVO> listarByIdTipoRecomendacionIdVisita(int idTipoRecomendacion,int idVisita){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<RecomendacionVO> recomendacionVOList =  new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "R."+TABLE_RECOMENDACION_COL_ID+", " +
                        "R."+TABLE_RECOMENDACION_COL_CANTIDAD+", " +
                        "R."+TABLE_RECOMENDACION_COL_UNIDAD+", "+
                        "R."+TABLE_RECOMENDACION_COL_FRECUENCIA+", "+
                        "R."+TABLE_RECOMENDACION_COL_COMENTARIO+", "+
                        "R."+TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION+", "+
                        "R."+TABLE_RECOMENDACION_COL_IDVISITA+
                        " FROM "+
                        TABLE_RECOMENDACION+" as R,"+
                        TABLE_CRITERIORECOMENDACION+" as CR"+
                        " WHERE "+
                        "R."+TABLE_RECOMENDACION_COL_IDVISITA+"="+String.valueOf(idVisita)+
                        " AND "+
                        "CR."+TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION+"="+String.valueOf(idTipoRecomendacion)+
                        " AND "+
                        "CR."+TABLE_CRITERIORECOMENDACION_COL_ID+"="+"R."+TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION
                ,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                RecomendacionVO temp= new RecomendacionVO();
                temp.setId(cursor.getInt(0));
                temp.setCantidad(cursor.getString(1));
                temp.setUnidad(cursor.getInt(2));
                temp.setFrecuencia(cursor.getInt(3));
                temp.setComentario(cursor.getString(4     ));
                temp.setIdCriterioRecomendacion(cursor.getInt(5));
                temp.setIdVisita(cursor.getInt(6));

                CriterioRecomendacionDAO criterioDAO = new CriterioRecomendacionDAO(ctx);
                CriterioRecomendacionVO cri = criterioDAO.consultarById(temp.getIdCriterioRecomendacion());
                if(temp!=null){
                    Log.d("locomata","temp is null");
                    temp.setName(cri.getName());
                    temp.setListUnidades(cri.getListUnidades());
                    temp.setListFrecuancias(cri.getListFrecuancias());
                    temp.setIdTipoRecomendacion(cri.getIdTipoRecomendacion());
                }else{
                    Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
                }
                recomendacionVOList.add(temp);
            }

        }
        cursor.close();
        c.close();
        return recomendacionVOList;

    }

    public float clearTableUpload(){
        int cont= 0;
        float r;
        List<VisitaVO> listVisitas = new VisitaDAO(ctx).listarNoEditable();
        if(listVisitas.size()>0){
            for(int i=0;i<listVisitas.size();i++){
                ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                SQLiteDatabase db = conn.getWritableDatabase();
                int res = db.delete(TABLE_RECOMENDACION,TABLE_RECOMENDACION_COL_IDVISITA+"="+listVisitas.get(i).getId(),null);
                if(res==0){
                    cont++;
                }
                db.close();
                conn.close();
            }
            r = (((float)cont)*1.0f)/((float)listVisitas.size());
        }else{
            r = 1.0f;
        }
        return r;
    }

    public RecomendacionVO nuevoByIdCriterioRecomendacionIdVisita(int idCriterioRecomendacion, int idVisita) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        Log.d("locomata","datos recibidos e funcion : "+idCriterioRecomendacion+" "+idVisita);

        SQLiteDatabase db = conn.getWritableDatabase();
        RecomendacionVO res = null;
        ContentValues values = new ContentValues();
        values.put(TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION,String.valueOf(idCriterioRecomendacion));
        values.put(TABLE_RECOMENDACION_COL_IDVISITA,String.valueOf(idVisita));
        Long id = db.insert(TABLE_RECOMENDACION,TABLE_RECOMENDACION_COL_ID,values);

        Log.d("locomata","id insertado : "+id);

        //obteniendo datos extra de tablas alternas
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "R."+TABLE_RECOMENDACION_COL_ID+", " +
                            "R."+TABLE_RECOMENDACION_COL_CANTIDAD+", " +
                            "R."+TABLE_RECOMENDACION_COL_UNIDAD+", "+
                            "R."+TABLE_RECOMENDACION_COL_FRECUENCIA+", "+
                            "R."+TABLE_RECOMENDACION_COL_COMENTARIO+", "+
                            "R."+TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION+", "+
                            "R."+TABLE_RECOMENDACION_COL_IDVISITA+
                            " FROM "+
                            TABLE_RECOMENDACION+" as R"+
                            " WHERE "+
                            "R."+TABLE_RECOMENDACION_COL_ID+"="+String.valueOf(id)
                    , null);
            Log.d("locomata","cantidad isnercio"+cursor.getCount());
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                res = new RecomendacionVO();
                res.setId(cursor.getInt(0));
                res.setCantidad(cursor.getString(1));
                res.setUnidad(cursor.getInt(2));
                res.setFrecuencia(cursor.getInt(3));
                res.setComentario(cursor.getString(4));
                res.setIdCriterioRecomendacion(cursor.getInt(5));
                res.setIdVisita(cursor.getInt(6));

                CriterioRecomendacionDAO criterioRecomendacionDAO = new CriterioRecomendacionDAO(ctx);
                CriterioRecomendacionVO temp = criterioRecomendacionDAO.consultarById(res.getIdCriterioRecomendacion());

                if(temp!=null){
                    Log.d("locomata","temp is not null");
                    res.setListUnidades(temp.getListUnidades());
                    res.setName(temp.getName());
                    res.setListFrecuancias(temp.getListFrecuancias());
                    res.setIdTipoRecomendacion(temp.getIdTipoRecomendacion());
                }else{
                    Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        }

        db.close();
        conn.close();
        return res;
    }

    public boolean borrarById(int id) {

        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper( ctx
                                                            , DATABASE_NAME
                                                            ,null
                                                            ,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_RECOMENDACION
                            ,TABLE_RECOMENDACION_COL_ID+"=?"
                            ,parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();

        return flag;
    }

    public Boolean editarUnidadById(int id, int s) {

            boolean flag = false;
            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
            SQLiteDatabase db = conn.getWritableDatabase();
            String[] parametros =
                    {
                            String.valueOf(id),
                    };
            ContentValues values = new ContentValues();
            values.put(TABLE_RECOMENDACION_COL_UNIDAD,s);
            int res = db.update(TABLE_RECOMENDACION
                                ,values,TABLE_RECOMENDACION_COL_ID+"=?"
                                ,parametros);
            if(res>0){
                flag=true;
            }
            db.close();
            conn.close();
            return flag;

    }
    public Boolean editarFrecuenciaById(int id, int s) {

        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_RECOMENDACION_COL_FRECUENCIA,s);
        int res = db.update(TABLE_RECOMENDACION
                ,values,TABLE_RECOMENDACION_COL_ID+"=?"
                ,parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;

    }
    public Boolean editarComentarioById(int id, String s) {

        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_RECOMENDACION_COL_COMENTARIO,s);
        int res = db.update(TABLE_RECOMENDACION
                ,values,TABLE_RECOMENDACION_COL_ID+"=?"
                ,parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
    public Boolean editarCantidadById(int id, String s) {

        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_RECOMENDACION_COL_CANTIDAD,s);
        int res = db.update(TABLE_RECOMENDACION
                ,values,TABLE_RECOMENDACION_COL_ID+"=?"
                ,parametros);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public List<RecomendacionVO> listarAll() {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<RecomendacionVO> recomendacionVOList =  new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "R."+TABLE_RECOMENDACION_COL_ID+", " +
                        "R."+TABLE_RECOMENDACION_COL_CANTIDAD+", " +
                        "R."+TABLE_RECOMENDACION_COL_UNIDAD+", "+
                        "R."+TABLE_RECOMENDACION_COL_FRECUENCIA+", "+
                        "R."+TABLE_RECOMENDACION_COL_COMENTARIO+", "+
                        "R."+TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION+", "+
                        "R."+TABLE_RECOMENDACION_COL_IDVISITA+
                        " FROM "+
                        TABLE_RECOMENDACION+" as R"
                ,null);
        if(cursor.getCount()>0){
            while(cursor.moveToNext()){
                RecomendacionVO temp= new RecomendacionVO();
                temp.setId(cursor.getInt(0));
                temp.setCantidad(cursor.getString(1));
                temp.setUnidad(cursor.getInt(2));
                temp.setFrecuencia(cursor.getInt(3));
                temp.setComentario(cursor.getString(4     ));
                temp.setIdCriterioRecomendacion(cursor.getInt(5));
                temp.setIdVisita(cursor.getInt(6));
                CriterioRecomendacionDAO criterioDAO = new CriterioRecomendacionDAO(ctx);
                CriterioRecomendacionVO cri = criterioDAO.consultarById(temp.getIdCriterioRecomendacion());

                if(temp!=null){
                    Log.d("locomata","temp is not null");
                    temp.setName(cri.getName());
                    temp.setListUnidades(cri.getListUnidades());
                    temp.setListFrecuancias(cri.getListFrecuancias());
                    temp.setIdTipoRecomendacion(cri.getIdTipoRecomendacion());
                }else{
                    Toast.makeText(ctx,"Error de data interna",Toast.LENGTH_LONG).show();
                }
                recomendacionVOList.add(temp);
            }

        }
        cursor.close();
        c.close();
        return recomendacionVOList;


    }
}
