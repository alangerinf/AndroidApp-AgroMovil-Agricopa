package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesDB.ContactoVO;


import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONTACTO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONTACTO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONTACTO_COL_IDFUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONTACTO_COL_NAME;

public class ContactoDAO {
    Context ctx;

    public ContactoDAO(Context ctx){
        this.ctx = ctx;
    }

    public ContactoVO consultarContactoByid(int id){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ContactoVO temp = null;
        try{
            temp = new ContactoVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "C."+TABLE_CONTACTO_COL_ID+", " +
                            "C."+TABLE_CONTACTO_COL_NAME+", "+
                            "C."+TABLE_CONTACTO_COL_IDFUNDO+
                        " FROM "+
                            TABLE_CONTACTO+" as C"+
                        " WHERE "+
                            "C."+TABLE_CONTACTO_COL_ID+" = "+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdFundo(cursor.getInt(2));
                cursor.close();
            }
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return temp;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_CONTACTO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public List<ContactoVO> listarByIdFundo(int idFundo){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<ContactoVO> contactoVOS = new ArrayList<ContactoVO>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "C."+TABLE_CONTACTO_COL_ID+", " +
                            "C."+TABLE_CONTACTO_COL_NAME+", " +
                            "C."+TABLE_CONTACTO_COL_IDFUNDO+
                        " FROM "+
                            TABLE_CONTACTO+" as C"+
                        " WHERE "+
                            "C."+TABLE_CONTACTO_COL_IDFUNDO+"="+String.valueOf(idFundo)+
                        " ORDER BY "+
                            "C."+TABLE_CONTACTO_COL_NAME+
                            " ASC "
                    ,null);
            while (cursor.moveToNext()){
                ContactoVO temp = new ContactoVO();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdFundo(cursor.getInt(2));
                contactoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return contactoVOS;
    }


    public boolean insertarContacto(int id, String name,int idFundo){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_CONTACTO_COL_ID,id);
        values.put(TABLE_CONTACTO_COL_NAME,name);
        values.put(TABLE_CONTACTO_COL_IDFUNDO,idFundo);
        Long temp = db.insert(TABLE_CONTACTO,TABLE_CONTACTO_COL_ID,values);
        db.close();
        return temp > 0;
    }


}
