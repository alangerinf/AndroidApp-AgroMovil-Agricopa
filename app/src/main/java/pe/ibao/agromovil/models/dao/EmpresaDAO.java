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
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA_COL_IDZONA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_IDEMPRESA;

public class EmpresaDAO {

    Context ctx;
    ConexionSQLiteHelper c;
    public EmpresaDAO(Context ctx) {
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        this.ctx=ctx;
    }


    public boolean borrarTable(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_EMPRESA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean insertarEmpresa(int id, String name,int idZona){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.TABLE_EMPRESA_COL_ID,id);
        values.put(Utilities.TABLE_EMPRESA_COL_NAME,name);
        values.put(Utilities.TABLE_EMPRESA_COL_IDZONA,idZona);
        Long temp = db.insert(Utilities.TABLE_EMPRESA,Utilities.TABLE_EMPRESA_COL_ID,values);
        db.close();
        return (temp>0)?true:false;
    }

    public EmpresaVO consultarEmpresaByid(int id){
        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO temp = null;
        try{
            temp = new EmpresaVO();
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                        "E."+TABLE_EMPRESA_COL_ID+", " +
                        "E."+TABLE_EMPRESA_COL_NAME+", "+
                        "E."+TABLE_EMPRESA_COL_IDZONA+
                        " FROM "+TABLE_EMPRESA+" as E"+
                        " WHERE "+"E."+TABLE_EMPRESA_COL_ID+" = "+String.valueOf(id)
            ,null);
            cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdZona(cursor.getInt(2));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return temp;
    }
/*
    public EmpresaVO consultarEmpresaByIdFundo(int idFundo){
        SQLiteDatabase db = c.getReadableDatabase();
        EmpresaVO empresaVO = null;
        try{
            empresaVO = new EmpresaVO();
            Cursor cursor = db.rawQuery(
                    " SELECT "+"E."+TABLE_EMPRESA_COL_ID+", "+"E."+TABLE_EMPRESA_COL_NAME+
                            " FROM "+TABLE_EMPRESA+" as E, "+TABLE_FUNDO+" as F"+
                            " WHERE "+"F."+TABLE_FUNDO_COL_ID+"="+  String.valueOf(idFundo)+
                            " AND "+"F."+TABLE_FUNDO_COL_IDEMPRESA+" = "+"E."+TABLE_EMPRESA_COL_ID, null);
            cursor.moveToFirst();
            empresaVO.setId(cursor.getInt(0));
            empresaVO.setName(cursor.getString(1));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return empresaVO;
    }
  */
    public List<EmpresaVO> listEmpresasByIdZona(int idZona){
        SQLiteDatabase db = c.getReadableDatabase();
        List<EmpresaVO> empresas = new  ArrayList<>();
        try{

            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "E."+TABLE_EMPRESA_COL_ID+", " +
                            "E."+TABLE_EMPRESA_COL_NAME+", "+
                            "E."+TABLE_EMPRESA_COL_IDZONA+
                            " FROM "+TABLE_EMPRESA+" as E"+
                            " WHERE "+"E."+TABLE_EMPRESA_COL_IDZONA+" = "+String.valueOf(idZona)
                    ,null);
            while(cursor.moveToNext()){
                EmpresaVO temp = new EmpresaVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    temp.setIdZona(cursor.getInt(2));
                    Log.d("EmpresaDAOxdxd",""+temp.getName());
                empresas.add(temp);
               // Toast.makeText(ctx,temp.getName(),Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        return empresas;
    }
    public void EmpresaDAOCloseConection() {
        c.close();
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, Utilities.DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_EMPRESA,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
