package pe.ibao.agromovil.views;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import pe.ibao.agromovil.R;
import pe.ibao.agromovil.helpers.LoginHelper;

import pe.ibao.agromovil.models.vo.entitiesInternal.UsuarioVO;


public class ActivityPreloader extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preloader);

        //delay de 1 segundo
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //verificamos si tenemos internet
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    // Si hay conexión a Internet en este momento
                   // Toast.makeText(getBaseContext(),"Online Mode On!",
                      //      Toast.LENGTH_SHORT).show();

                } else {
                    //Toast.makeText(getBaseContext(),"Offline Mode On!",
                    //        Toast.LENGTH_SHORT).show();
                    //varieble para comprovar si esas logueado
                }

                UsuarioVO temp = new LoginHelper(getBaseContext()).verificarLogueo();

                if(temp!=null){
                    ingresar();
                }else{
                    openLogin();
                }

            }
        }, 500);


    }

    void  openLogin(){
        Intent intent = new Intent(this,ActivityLogin.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void  ingresar(){
        Intent intent = new Intent(this,ActivityMain.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /*
    void verifyUpdate(){
        Intent intent = new Intent(this,ActivityUpdate.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }*/
}
