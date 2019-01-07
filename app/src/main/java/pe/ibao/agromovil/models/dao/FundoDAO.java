package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesDB.EmpresaVO;
import pe.ibao.agromovil.models.vo.entitiesDB.FundoVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_EMPRESA_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_AREA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_IDEMPRESA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_SISTEMARIEGO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_MUESTRA;

public class FundoDAO {


    Context ctx;
    public FundoDAO(Context ctx) {


        this.ctx=ctx;
    }



    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        int res = db.delete(TABLE_FUNDO,null,null);
        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
    public boolean insertarFundo(int id, String name,int idEmpresa,String sistemaRiego){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TABLE_FUNDO_COL_ID,id);
        values.put(TABLE_FUNDO_COL_NAME,name);
        values.put(TABLE_FUNDO_COL_SISTEMARIEGO,sistemaRiego);
        values.put(TABLE_FUNDO_COL_IDEMPRESA,idEmpresa);
        Long temp = db.insert(TABLE_FUNDO,TABLE_FUNDO_COL_ID,values);
        db.close();
        return temp > 0;
    }
    public FundoVO consultarById(int id) {
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        FundoVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "F."+ TABLE_FUNDO_COL_ID+", " +
                            "F."+TABLE_FUNDO_COL_NAME+", " +
                            "F."+TABLE_FUNDO_COL_IDEMPRESA+
                        " FROM "+
                            TABLE_FUNDO+" as F"+
                        " WHERE "+
                            "F."+TABLE_FUNDO_COL_ID+"="+String.valueOf(id)
                    ,null);

            if(cursor.getCount()>0){
                temp = new FundoVO();
                cursor.moveToFirst();
                temp.setId(cursor.getInt(0));
                temp.setName(cursor.getString(1));
                temp.setIdEmpresa(cursor.getInt(2));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return temp;
    }

    public List<FundoVO> listarByIdEmpresa(int idEmpresa){
        ConexionSQLiteHelper c;
        c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<FundoVO> fundoVOS = new ArrayList<FundoVO>();
        try{
            Cursor cursor = db.rawQuery(
                    " SELECT "+
                            "F."+TABLE_FUNDO_COL_ID+", " +
                            "F."+TABLE_FUNDO_COL_NAME+", " +
                            "F."+TABLE_FUNDO_COL_IDEMPRESA+
                        " FROM "+
                            TABLE_FUNDO+" as F"+
                        " WHERE "+
                            "F."+TABLE_FUNDO_COL_IDEMPRESA+"="+  String.valueOf(idEmpresa)+
                        " ORDER BY "+
                            "F."+TABLE_FUNDO_COL_NAME+
                            " COLLATE UNICODE ASC"
                    ,null);
            while (cursor.moveToNext()){
                FundoVO temp = new FundoVO();
                    temp.setId(cursor.getInt(0));
                    temp.setName(cursor.getString(1));
                    temp.setIdEmpresa(cursor.getInt(2));
                fundoVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return fundoVOS;
    }


}
