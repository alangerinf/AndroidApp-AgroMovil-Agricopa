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
import pe.ibao.agromovil.models.vo.entitiesDB.ZonaVO;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_ZONA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_ZONA_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_ZONA_COL_NAME;

public class ZonaDAO {

    Context ctx;
    ConexionSQLiteHelper c;
    public ZonaDAO(Context ctx) {
        c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_ZONA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertarZona(int id, String name){

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(TABLE_ZONA_COL_ID,id);
            values.put(TABLE_ZONA_COL_NAME,name);
            Long temp = db.insert(TABLE_ZONA,TABLE_ZONA_COL_ID,values);
            db.close();
            conn.close();
            return (temp>0);
        }catch (Exception e){
            Log.d("ZonaDAO",e.toString());
        }

        return false;
    }

    public ZonaVO consultarZonaByid(int id){
        SQLiteDatabase db = c.getReadableDatabase();
        ZonaVO temp = null;
        try{
            temp = new ZonaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "Z."+TABLE_ZONA_COL_ID+", " +
                            "Z."+TABLE_ZONA_COL_NAME+
                        " FROM "+
                            TABLE_ZONA+" as Z"+
                        " WHERE "+
                            "Z."+TABLE_ZONA_COL_ID+" = "+String.valueOf(id)
                    ,null);
            cursor.moveToFirst();
            temp.setId(cursor.getInt(0));
            temp.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(), Toast.LENGTH_SHORT);
        }
        return temp;
    }


    public List<ZonaVO> listZonas(){
        SQLiteDatabase db = c.getReadableDatabase();
        List<ZonaVO> zonas = new ArrayList<>();
        try{
            String[] campos = {TABLE_ZONA_COL_ID,TABLE_ZONA_COL_NAME};
            Cursor cursor= db.query(TABLE_ZONA,campos,null,null,null,null,TABLE_ZONA_COL_NAME+" COLLATE UNICODE ASC");
            while(cursor.moveToNext()){
                ZonaVO temp = new ZonaVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                zonas.add(temp);
                Log.d("zonaDAOtag",temp.getName());
               // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Log.d("zonaDAOtag",e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return zonas;
    }
    public void EmpresaDAOCloseConection() {
        c.close();
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_ZONA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
