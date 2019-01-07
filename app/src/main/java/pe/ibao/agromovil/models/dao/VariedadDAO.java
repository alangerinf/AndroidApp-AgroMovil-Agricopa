package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesDB.VariedadVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_IDEMPRESA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VARIEDAD_COL_IDCULTIVO;

public class VariedadDAO {

    Context ctx;
    ConexionSQLiteHelper c;
    public VariedadDAO(Context ctx) {
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        this.ctx=ctx;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_VARIEDAD,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public boolean insertarVariedad(int id, String name,int idCultivo){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_VARIEDAD_COL_ID,id);
        values.put(TABLE_VARIEDAD_COL_NAME,name);
        values.put(TABLE_VARIEDAD_COL_IDCULTIVO,idCultivo);
        Long temp = db.insert(TABLE_VARIEDAD,TABLE_VARIEDAD_COL_ID,values);
        db.close();
        return temp > 0;
    }
    public VariedadVO consultarVariedadById(int id) {
        SQLiteDatabase db = c.getReadableDatabase();
        VariedadVO temp = null;
        try{
            temp = new VariedadVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "V."+ TABLE_VARIEDAD_COL_ID+", " +
                            "V."+TABLE_VARIEDAD_COL_NAME+", " +
                            "V."+TABLE_VARIEDAD_COL_IDCULTIVO+
                    " FROM "+
                            TABLE_VARIEDAD+" as V"+
                    " WHERE "+
                            "V."+TABLE_VARIEDAD_COL_ID+"="+String.valueOf(id)
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            temp.setIdCultivo(cursor.getInt(2));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT);
        }
        return temp;
    }

    public List<VariedadVO> listarVariedadByIdCultivo(int idCultivo){
        SQLiteDatabase db = c.getReadableDatabase();
        List<VariedadVO> variedadVOS = new ArrayList<VariedadVO>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "V."+TABLE_VARIEDAD_COL_ID+", " +
                            "V."+TABLE_VARIEDAD_COL_NAME+", " +
                            "V."+TABLE_VARIEDAD_COL_IDCULTIVO+
                        " FROM "+
                            TABLE_VARIEDAD+" as V"+
                        " WHERE "+
                            "V."+TABLE_VARIEDAD_COL_IDCULTIVO+"="+  String.valueOf(idCultivo)+
                        " ORDER BY "+
                            "V."+TABLE_VARIEDAD_COL_NAME+
                            " COLLATE UNICODE ASC"
                    ,null);
            while (cursor.moveToNext()){
                VariedadVO temp = new VariedadVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdCultivo(cursor.getInt(2));
                variedadVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return variedadVOS;
    }


}
