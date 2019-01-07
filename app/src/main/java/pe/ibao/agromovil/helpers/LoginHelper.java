package pe.ibao.agromovil.helpers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

import pe.ibao.agromovil.app.AppController;
import pe.ibao.agromovil.models.dao.UsuarioDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.UsuarioVO;
import pe.ibao.agromovil.views.ActivityUpdate;
import pe.ibao.agromovil.views.ActivityUpdate;

import static pe.ibao.agromovil.utilities.Utilities.URL_AUTENTIFICATION;

public class LoginHelper {

    public static String POST_USER = "user";
    public static String POST_PASSWORD = "password";

    Context ctx;
    ProgressDialog progress;

    public LoginHelper(Context ctx){
        this.ctx = ctx;
    }

    public UsuarioVO verificarLogueo(){
        UsuarioVO  temp = new UsuarioDAO(ctx).verficarLogueo();
        return temp;
    }

    public void intentoLogueo(final String user, final String pass){
        progress = new ProgressDialog(ctx);
        progress.setCancelable(false);
        progress.setMessage("Comprobando Credenciales");
        progress.show();
        StringRequest sr = new StringRequest(Request.Method.POST,
                URL_AUTENTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        try {

                            new UsuarioDAO(ctx).borrarTable();

                            JSONObject data = new JSONObject(response);
                            int success = data.getInt("success");
                            if (success == 1) {
                                Log.d("autentification","success1");
                                Log.d("autentification",user);
                                Log.d("autentification",pass);
                                Log.d("autentification",response);
                                JSONArray main = data.getJSONArray("login");
                                for(int i=0;i<main.length();i++){
                                    JSONObject temp = new JSONObject(main.get(i).toString());
                                    UsuarioVO usuarioVO = new UsuarioVO();
                                    usuarioVO.setId(temp.getInt("id"));
                                    usuarioVO.setUser(user);
                                    usuarioVO.setPassword(pass);
                                    usuarioVO.setCodigo(temp.getString("codigo"));
                                    usuarioVO.setName(temp.getString("inspector"));
                                    usuarioVO.setLastName("");
                                    new UsuarioDAO(ctx).guardarUsuarioNuevo(usuarioVO);
                                    if( verificarLogueo() != null){
                                        UsuarioVO u = verificarLogueo();
                                        if(u!=null){
                                            Toast.makeText(ctx,"Espere un momento...",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ctx, ActivityUpdate.class);
                                            ctx.startActivity(intent);
                                        }else {
                                            Toast.makeText(ctx,"Error de Base de Datos Interna",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(ctx,"Verifique su Usuario y/o Contraseña",Toast.LENGTH_LONG).show();
                                Log.d("autentification","success0");
                                Log.d("autentification",user);
                                Log.d("autentification",pass);
                            }

                        } catch (JSONException e) {
                            Log.d("autentification",e.toString());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progress.dismiss();
                        Toast.makeText(ctx,"Error conectando con el servidor",Toast.LENGTH_LONG).show();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put(POST_USER, user);
                params.put(POST_PASSWORD, pass);
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
