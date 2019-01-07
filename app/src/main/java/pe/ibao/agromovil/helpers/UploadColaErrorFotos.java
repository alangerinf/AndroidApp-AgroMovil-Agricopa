package pe.ibao.agromovil.helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pe.ibao.agromovil.app.AppController;
import pe.ibao.agromovil.models.dao.ColaUploadFotosDAO;
import pe.ibao.agromovil.models.dao.FotoDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.FotoVO;

import static pe.ibao.agromovil.utilities.Utilities.URL_UPLOAD_FOTOS;

public class UploadColaErrorFotos {

    Context ctx;
    ProgressDialog progress;

    public static int status;

    public UploadColaErrorFotos(Context ctx){
        status =0;
        this.ctx = ctx;
    }

    public void Upload(final int idEvidencia, final String bitmap,int i){
/*        progress = new ProgressDialog(ctx);
        progress.setCancelable(false);
        progress.setMessage("Subiendo Imagen "+i+"/"+cantidad);
        progress.show();*/
        status =1;
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_UPLOAD_FOTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //progress.dismiss();
                        status=2;
                        try {
                            JSONObject main = new JSONObject(response);
                            if(main.getInt("success")==1){
                               // Toast.makeText(ctx,""+idEvidencia+" success="+1,Toast.LENGTH_SHORT).show();
                                new ColaUploadFotosDAO(ctx).borrarById(idEvidencia);
                                status =3;
                            }else{
                                if(main.getInt("success")==0)
                                {
                                   // Toast.makeText(ctx,""+idEvidencia+" success="+0,Toast.LENGTH_SHORT).show();
                                    status =-3;
                                }
                            }
                        } catch (JSONException e) {
                            //Log.d("CONFCRITERIODOWN ",e.toString());

                            Toast.makeText(ctx,e.toString(),Toast.LENGTH_LONG).show();
                            status=-1;
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //progress.dismiss();
                        Log.d("subiendoFotos",error.toString());
                        Toast.makeText(ctx,"Error conectando con el servidor",Toast.LENGTH_LONG).show();
                        status =-2;
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("idInspeccionEvidencia", String.valueOf(idEvidencia));
                params.put("imagen",bitmap);
                Log.d("idEvidadncoa",String.valueOf(idEvidencia));
                Log.d("bitmapmandado",bitmap);
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