package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesInternal.ColaUploadFotosVO;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_COLAFOTOS;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_COLAFOTOS_COL_IDDB;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_COLAFOTOS_COL_PATH;

public class ColaUploadFotosDAO {

    Context ctx;
    public ColaUploadFotosDAO(Context ctx) {
        this.ctx=ctx;
    }

    public ColaUploadFotosVO nuevo(int idDB, String path) {
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        Log.d("locomata","datos recibidos e funcion : "+idDB);

        SQLiteDatabase db = conn.getWritableDatabase();
        ColaUploadFotosVO res = null;
        ContentValues values = new ContentValues();
        values.put(TABLE_COLAFOTOS_COL_IDDB,idDB);
        values.put(TABLE_COLAFOTOS_COL_PATH,path);
        Long id = db.insert(TABLE_COLAFOTOS,TABLE_COLAFOTOS_COL_IDDB,values);

        Log.d("locomata","id FOTO insertado : "+id);

        //obteniendo datos extra de tablas alternas
        Cursor cursor = db.rawQuery(
                "SELECT " +
                        "CF."+TABLE_COLAFOTOS_COL_IDDB+", " +
                        "CF."+TABLE_COLAFOTOS_COL_PATH+
                        " FROM "+
                        TABLE_COLAFOTOS+" as CF"+
                        " WHERE "+
                        "CF."+TABLE_COLAFOTOS_COL_IDDB+"="+String.valueOf(id)
                , null);
        Log.d("locomata","cantidad isnercio"+cursor.getCount());
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            res = new ColaUploadFotosVO();
            res.setIdBD(cursor.getInt(0));
            res.setPath(cursor.getString(1));
        }
        cursor.close();
        db.close();
        conn.close();
        return res;
    }

    public ColaUploadFotosVO consultarById(int id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ColaUploadFotosVO res = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "CF."+TABLE_COLAFOTOS_COL_IDDB+", " +
                            "CF."+TABLE_COLAFOTOS_COL_PATH+
                            " FROM "+
                            TABLE_COLAFOTOS+" as CF"+
                            " WHERE "+
                            "CF."+TABLE_COLAFOTOS_COL_IDDB+"="+String.valueOf(id)
                    , null);
            Log.d("locomata","cantidad isnercio"+cursor.getCount());
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                res = new ColaUploadFotosVO();
                res.setIdBD(cursor.getInt(0));
                res.setPath(cursor.getString(1));
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
        }
        c.close();
        return res;
    }


    public ColaUploadFotosVO consultarById_Upload(int id) {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        ColaUploadFotosVO res = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "CF."+TABLE_COLAFOTOS_COL_IDDB+", " +
                            "CF."+TABLE_COLAFOTOS_COL_PATH+
                            " FROM "+
                            TABLE_COLAFOTOS+" as CF"+
                            " WHERE "+
                            "CF."+TABLE_COLAFOTOS_COL_IDDB+"="+String.valueOf(id)
                    , null);
            Log.d("locomata","cantidad isnercio"+cursor.getCount());
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                res = new ColaUploadFotosVO();
                res.setIdBD(cursor.getInt(0));
                res.setPath(cursor.getString(1));

                try{

                    Uri uri = Uri.fromFile(new File(res.getPath()));

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(ctx.getContentResolver(), uri);

                    //redimensionando bitmap

                    final int tam=600;

                    int ancho = bitmap.getWidth();
                    int alto = bitmap.getHeight();
                    float scalaAlto;
                    float scalaAncho;
                    if(alto>=ancho){
                        float nuevoAlto = tam;
                        float nuevoAncho = (1.0f*ancho*nuevoAlto)/alto;
                        scalaAlto = nuevoAlto/alto;
                        scalaAncho = nuevoAncho/ancho;
                        //si la imagen esta parada
                    }else{
                        //si la imagen esta echada
                        float nuevoAncho = tam;
                        float nuevoAlto = (1.0f*alto*nuevoAncho)/ancho;
                        scalaAlto = nuevoAlto/alto;
                        scalaAncho = nuevoAncho/ancho;
                    }
                    Matrix matrix = new Matrix();
                    matrix.postScale(scalaAncho,scalaAlto);

                    bitmap = Bitmap.createBitmap(bitmap,0,0,ancho,alto,matrix,false);

                    //terminado redimensionado de bitmap
                    ByteArrayOutputStream array = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,70,array);
                    byte[] imageByte = array.toByteArray();
                    String imageString = Base64.encodeToString(imageByte,Base64.DEFAULT);
                    //  String imageString = StrinimageByte;
                    res.setStringBitmap(imageString);

                }catch (Exception e){
                    Log.d("fotosJson","Error"+e.toString());
                }
            }
            cursor.close();
        }catch (Exception e){
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        c.close();
        return res;
    }

    public int contarColaUploadFotos(){
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        Cursor mCount= db.rawQuery(
                "select " +
                        "count(*)" +
                        " from " +
                        TABLE_COLAFOTOS
                , null);
        mCount.moveToFirst();
        int i= mCount.getInt(0);
        mCount.close();
        return i;
    }


    public boolean borrarById(int idDB){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(idDB),
                };

        int res = db.delete(TABLE_COLAFOTOS,TABLE_COLAFOTOS_COL_IDDB+"=?",parametros);

        if(res>0){
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }
}
