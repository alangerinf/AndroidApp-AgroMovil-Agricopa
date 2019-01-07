package pe.ibao.agromovil.helpers.downloaders;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.app.AppController;
import pe.ibao.agromovil.helpers.LoginHelper;
import pe.ibao.agromovil.models.dao.VariedadDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.UsuarioVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_ZONA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_ZONA_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_ZONA_COL_NAME;
import static pe.ibao.agromovil.utilities.Utilities.URL_DOWNLOAD_TABLE_CONFIGURACIONRECOMENDACION;
import static pe.ibao.agromovil.utilities.Utilities.URL_DOWNLOAD_TABLE_VARIEDAD;

public class DownloaderConfiguracionRecomendacion {
    public static int status;
    Context ctx;
   // ProgressDialog progress;
    public DownloaderConfiguracionRecomendacion(Context ctx){
        this.ctx = ctx;
        status = 0;
    }
    public boolean clearConfiguracionRecomendacionUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();

        int res = db.delete(TABLE_CONFIGURACIONRECOMENDACION,null,null);
        if(res>0){
            flag=true;
            //new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }
    public void download(){
        status = 1;
     //   progress = new ProgressDialog(ctx);
      //  progress.setCancelable(false);
      //  progress.setMessage("Intentando descargar ConfiguracionRecomendacion");
//        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_DOWNLOAD_TABLE_CONFIGURACIONRECOMENDACION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
    //                    progress.dismiss();

                        try {
                            JSONArray main = new JSONArray(response);
                            if(main.length()>0){
                                clearConfiguracionRecomendacionUpload();
                                status=2;
                            }
                            String insert = "INSERT INTO " +
                                        TABLE_CONFIGURACIONRECOMENDACION+
                                    "("+TABLE_CONFIGURACIONRECOMENDACION_COL_ID+","+
                                        TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD+","+
                                        TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION+
                                    ")"+
                                    "VALUES ";
                            for(int i=0;i<main.length();i++){
                                /*
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                int idFundoVariedad = data.getInt("idFundoVariedad");
                                int idCriterioRecomendacion = data.getInt("idCriterioRecomendacion");

                               // Log.d("VARIEDADDOWN","fila "+i+" : "+id+" "+nombre+" "+idCultivo);
                                ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,1 );
                                SQLiteDatabase db = conn.getWritableDatabase();

                                ContentValues values = new ContentValues();
                                values.put(Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_ID,id);
                                values.put(Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD,idFundoVariedad);
                                values.put(Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION,idCriterioRecomendacion);
                                Long temp = db.insert(Utilities.TABLE_CONFIGURACIONRECOMENDACION,Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_ID,values);

                                if(temp>0){
                                    Log.d("CONFRECO","logro insertar");
                                }

                                db.close();
                                conn.close();
                            */
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                int idFundoVariedad = data.getInt("idFundoVariedad");
                                int idCriterioRecomendacion = data.getInt("idCriterioRecomendacion");
                               // Log.d("CONFRECO",""+id);
                                insert=insert+"("+id+","+idFundoVariedad+","+idCriterioRecomendacion+")";

                                if(i%1000==0&& i>0){
                                    try{
                                        Log.d("CONFRECO",""+id);
                                        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                        SQLiteDatabase db = conn.getWritableDatabase();
                                        db.execSQL(insert);
                                        db.close();
                                        conn.close();
                                        insert = "INSERT INTO " +
                                                TABLE_CONFIGURACIONRECOMENDACION+
                                                "("+TABLE_CONFIGURACIONRECOMENDACION_COL_ID+","+
                                                TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD+","+
                                                TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION+
                                                ")"+
                                                "VALUES ";

                                    }catch (Exception e){
                                        Log.d("errorCR",e.toString());
                                    }
                                }else {
                                    if(main.length()-1!=i ){
                                        insert=insert+",";
                                    }
                                }




                            }



                            try{
                            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                            SQLiteDatabase db = conn.getWritableDatabase();
                                db.execSQL(insert);
                                db.close();
                                conn.close();
                            }catch (Exception e){
                                Log.d("errorCR",e.toString());
                            }


                            status = 3;
                        } catch (JSONException e) {
                            Log.d("CONFRECO ",e.toString());
                            status = -1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
  //                      progress.dismiss();
                        Toast.makeText(ctx,"CONFRECO Error conectando con el servidor",Toast.LENGTH_LONG).show();
                        status = -2;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
               /* params.put(POST_USER, user);
                params.put(POST_PASSWORD, pass);
                */
                UsuarioVO temp = new LoginHelper(ctx).verificarLogueo();
                params.put("id",String.valueOf(temp.getId()));
                params.put("idInspector",String.valueOf(temp.getCodigo()));


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }

    public void download(final TextView porcentaje, final TextView mensaje, final int ini, final int tam) {
       /* progress = new ProgressDialog(ctx);
        progress.setCancelable(false);
        progress.setMessage("Intentando descargar ConfiguracionRecomendacion");
        progress.show();
        */
        status = 1;
       StringRequest sr = new StringRequest(Request.Method.POST,
               URL_DOWNLOAD_TABLE_CONFIGURACIONRECOMENDACION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
       //                 progress.dismiss();
                        try {
                            mensaje.setText("Descargando Configuraciones de Recomendaciones");
                            JSONArray main = new JSONArray(response);
                            final int length = main.length();
                            if(main.length()>0){
                                clearConfiguracionRecomendacionUpload();
                                status=2;
                            }
                            for(int i=0;i<main.length();i++){
                                JSONObject data = new JSONObject(main.get(i).toString());
                                int id = data.getInt("id");
                                int idFundoVariedad = data.getInt("idFundoVariedad");
                                int idCriterioRecomendacion = data.getInt("idCriterioRecomendacion");

                                // Log.d("VARIEDADDOWN","fila "+i+" : "+id+" "+nombre+" "+idCultivo);
                                ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                                SQLiteDatabase db = conn.getWritableDatabase();

                                ContentValues values = new ContentValues();
                                values.put(Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_ID,id);
                                values.put(Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD,idFundoVariedad);
                                values.put(Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION,idCriterioRecomendacion);
                                Long temp = db.insert(Utilities.TABLE_CONFIGURACIONRECOMENDACION,Utilities.TABLE_CONFIGURACIONRECOMENDACION_COL_ID,values);

                                if(temp>0){
                                    Log.d("CONFRECO","logro insertar"+i);
                                    android.os.Handler handler = new android.os.Handler();
                                    final int finalI = i;
                                    handler.post(new Runnable() {
                                        public void run() {
                                            porcentaje.setText("" + (ini + ((finalI * tam) / length)) + "%");
                                        }
                                    });

                                }

                                db.close();
                                conn.close();
                            }
                            status = 3;
                      //      porcentaje.setText(String.valueOf(tam));

                        } catch (JSONException e) {
                            Log.d("VARIEDADDOWN ",e.toString());
                            status = -1;
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   progress.dismiss();
                        Toast.makeText(ctx,"Error conectando con el servidor",Toast.LENGTH_LONG).show();
                        status = -2;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
               /* params.put(POST_USER, user);
                params.put(POST_PASSWORD, pass);
                */

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }
        };

        AppController.getInstance().addToRequestQueue(sr);
    }
}
