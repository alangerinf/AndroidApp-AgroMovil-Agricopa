package pe.ibao.agromovil.services;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.PrecomputedText;
import android.util.Log;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.helpers.downloaders.DownloaderConfiguracionCriterio;
import pe.ibao.agromovil.helpers.downloaders.DownloaderConfiguracionRecomendacion;
import pe.ibao.agromovil.helpers.downloaders.DownloaderContacto;
import pe.ibao.agromovil.helpers.downloaders.DownloaderCriterio;
import pe.ibao.agromovil.helpers.downloaders.DownloaderCriterioRecomendacion;
import pe.ibao.agromovil.helpers.downloaders.DownloaderCultivo;
import pe.ibao.agromovil.helpers.downloaders.DownloaderEmpresa;
import pe.ibao.agromovil.helpers.downloaders.DownloaderFundo;
import pe.ibao.agromovil.helpers.downloaders.DownloaderFundoVariedad;
import pe.ibao.agromovil.helpers.downloaders.DownloaderTipoInspeccion;
import pe.ibao.agromovil.helpers.downloaders.DownloaderTipoRecomendacion;
import pe.ibao.agromovil.helpers.downloaders.DownloaderVariedad;
import pe.ibao.agromovil.helpers.downloaders.DownloaderZona;
import pe.ibao.agromovil.utilities.UpdateDB;

public class UpdateService extends IntentService {

    public static final String NOTIFICATION = "notification";
    public static final String RESULT = "RESULT";
    public static final String RESULT_PORCENT = "PORCENT";
    public static final String RESULT_MENSAJE = "MENSAJE";

    public UpdateService() {
        super("Download Service");
    }

    void reiniciarDownloaders(){
        new DownloaderContacto(getBaseContext());
        new DownloaderEmpresa(getBaseContext());
        new DownloaderFundo(getBaseContext());
        new DownloaderCultivo(getBaseContext());
        new DownloaderVariedad(getBaseContext());
        new DownloaderFundoVariedad(getBaseContext());
        new DownloaderTipoInspeccion(getBaseContext());
        new DownloaderConfiguracionCriterio(getBaseContext());
        new DownloaderCriterio(getBaseContext());
        new DownloaderTipoRecomendacion(getBaseContext());
        new DownloaderConfiguracionRecomendacion(getBaseContext());
        new DownloaderCriterioRecomendacion(getBaseContext());
        new DownloaderZona(getBaseContext());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        actualizar ac = new actualizar(getBaseContext());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            ac.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        }
        else
        {
            ac.execute();
        }

        String ANDROID_CHANNEL_ID="199232";
        int NOTIFICATION_ID=199232;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder builder = new Notification.Builder(this, ANDROID_CHANNEL_ID)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Descargando Actualizacion")
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(NOTIFICATION_ID, notification);
        } else {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.app_name))
                    .setContentText("Descargando Actualizacion")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);
            Notification notification = builder.build();
            startForeground(NOTIFICATION_ID, notification);
        }
        reiniciarDownloaders();
        r.run();
    }

    private void publishResults(int result, String mensaje, int porcent) {
        Intent intent = new Intent(NOTIFICATION);
        intent.putExtra(RESULT, result);
        intent.putExtra(RESULT_PORCENT, porcent);
        intent.putExtra(RESULT_MENSAJE, mensaje);
        sendBroadcast(intent);
    }

    Runnable r = new Runnable() {
        @Override
        public void run() {

            boolean flag = true;

            while (flag){
                final int resultStatus = Activity.RESULT_OK;
                Log.d("holaasd", "empesando");
                int[] i = new int[13];
                i[0] = DownloaderConfiguracionCriterio.status;
                i[1] = DownloaderConfiguracionRecomendacion.status;
                i[2] = DownloaderContacto.status;
                i[3] = DownloaderCriterio.status;
                i[4] = DownloaderCriterioRecomendacion.status;
                i[5] = DownloaderCultivo.status;
                i[6] = DownloaderEmpresa.status;
                i[7] = DownloaderFundo.status;
                i[8] = DownloaderFundoVariedad.status;
                i[9] = DownloaderTipoInspeccion.status;
                i[10] = DownloaderTipoRecomendacion.status;
                i[11] = DownloaderVariedad.status;
                i[12] = DownloaderZona.status;

                Log.d("holaasd", "111");
                String m0 = "Buscando Actulizacion";
                final String m1 = "Actualizando";
                final String m2 = "Buscando Actulizacion";
                final String m3 = "Error de Conversion";
                final String m4 = "Error de Conexion";
                //   mensaje.setText(m0);
                int cont2 = 0;
                int cont3 = 0;
                boolean act = false;
                Log.d("holaasd", "while");
                //verificando si todos las  descargas terminaron
                for (int n = 0; n < i.length; n++) {
                    if (i[n] == 3) {
                        cont3++;
                        Log.d("hola", "cont3:" + cont3);
                        act = true;
                    } else {
                        if (i[n] == 2) {
                            cont2++;
                            act = true;
                        } else {
                            if (i[n] == -1) {
                                Log.d("holaasd", "qqqqq1" + n);
                                String mensaje = m3;
                                int por = -1;
                                publishResults(Activity.RESULT_CANCELED, mensaje, por);
                                return;
                            } else {
                                if (i[n] == -2) {
                                    Log.d("holaasd", "qqqqqq2" + n);
                                    String mensaje = m4;
                                    int por = -2;
                                    flag=false;
                                    publishResults(Activity.RESULT_CANCELED, mensaje, por);
                                    Log.d("holasd","cancle-2");
                                    return;
                                }
                            }
                        }
                    }
                }

                Log.d("holahola3",""+cont3);
                Log.d("holahola2",""+cont2);

                if (cont3 == i.length) {

                    publishResults(Activity.RESULT_OK, "Actulizacion Terminada", 100);
                    flag=false;
                    return;

                } else {
                    Log.d("holahola", "" + i.length);
                    Log.d("holahola", "" + cont3);
                    final boolean a = act;
                    final int porce = (int) ((float) cont3 / (float) i.length * 100);

                    publishResults(Activity.RESULT_OK, m1, porce);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Log.d("hola", e.toString());
                    }


                }
            }
        }

    };
}

class actualizar extends AsyncTask<Void, Integer, Boolean> {
    Context ctx;

    actualizar(Context ctx){
        this.ctx=ctx;
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        Log.d("segundo","doInBackground");

        //   new UpdateDB(ctx);


        DownloaderConfiguracionRecomendacion core= new DownloaderConfiguracionRecomendacion(ctx);
        core.download();
        while(DownloaderConfiguracionRecomendacion.status!=3){
            if(DownloaderConfiguracionRecomendacion.status==-2 || DownloaderConfiguracionRecomendacion.status==-1){
                Log.d("segundo","seperando configuracionrecomendacion"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        DownloaderContacto cont = new DownloaderContacto(ctx);
        cont.download();
        while(DownloaderContacto.status!=3){
            if(DownloaderContacto.status==-2 || DownloaderContacto.status==-1){
                Log.d("segundo","seperando contacto"+DownloaderContacto.status);
                return null;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        DownloaderZona zona = new DownloaderZona(ctx);
        zona.download();
        while(DownloaderZona.status!=3){
            if(DownloaderZona.status==-2 || DownloaderZona.status==-1){
                Log.d("segundo","seperando zona"+DownloaderContacto.status);
                return null;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderEmpresa emp = new DownloaderEmpresa(ctx);
        emp.download();
        while(DownloaderEmpresa.status!=3){
            if(DownloaderEmpresa.status==-2 || DownloaderEmpresa.status==-1){
                Log.d("segundo","seperando empresa"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderFundo fun = new DownloaderFundo(ctx);
        fun.download();
        while(DownloaderFundo.status!=3){
            if(DownloaderFundo.status==-2 || DownloaderFundo.status==-1){
                Log.d("segundo","seperando fundo"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderCultivo cul = new DownloaderCultivo(ctx);
        cul.download();
        while(DownloaderCultivo.status!=3){
            if(DownloaderCultivo.status==-2 || DownloaderCultivo.status==-1){
                Log.d("segundo","seperando cultivo"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderVariedad var = new DownloaderVariedad(ctx);
        var.download();
        while(DownloaderVariedad.status!=3){
            if(DownloaderVariedad.status==-2 || DownloaderVariedad.status==-1){
                Log.d("segundo","seperando vareidad"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderFundoVariedad fva = new DownloaderFundoVariedad(ctx);
        fva.download();
        while(DownloaderFundoVariedad.status!=3){
            if(DownloaderFundoVariedad.status==-2 || DownloaderFundoVariedad.status==-1){
                Log.d("segundo","seperando fundovariedad"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderTipoInspeccion ti = new DownloaderTipoInspeccion(ctx);
        ti.download();
        while(DownloaderTipoInspeccion.status!=3){
            if(DownloaderTipoInspeccion.status==-2 || DownloaderTipoInspeccion.status==-1){
                Log.d("segundo","seperando tipoinspeccion"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderConfiguracionCriterio cc = new DownloaderConfiguracionCriterio(ctx);
        cc.download();
        while(DownloaderConfiguracionCriterio.status!=3){
            if(DownloaderConfiguracionCriterio.status==-2 || DownloaderConfiguracionCriterio.status==-1){
                Log.d("segundo","seperando configuracioncriterio"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderCriterio cri = new DownloaderCriterio(ctx);
        cri.download();
        while(DownloaderCriterio.status!=3){
            if(DownloaderCriterio.status==-2 || DownloaderCriterio.status==-1){
                Log.d("segundo","seperando criterio"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DownloaderTipoRecomendacion tre = new DownloaderTipoRecomendacion(ctx);
        tre.download();
        while(DownloaderTipoRecomendacion.status!=3){
            if(DownloaderTipoRecomendacion.status==-2 || DownloaderTipoRecomendacion.status==-1){
                Log.d("segundo","seperando tiporecomendacion"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        DownloaderCriterioRecomendacion cre = new DownloaderCriterioRecomendacion(ctx);
        cre.download();
        while(DownloaderCriterioRecomendacion.status!=3){
            if(DownloaderCriterioRecomendacion.status==-2 || DownloaderCriterioRecomendacion.status==-1){
                Log.d("segundo","seperando criteriorecomendacion"+DownloaderContacto.status);
                return null;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

/*
            for(int i=1; i<=10; i++) {
                tareaLarga();

                publishProgress(i*10);

                if(isCancelled())
                    break;
            }
*/
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //          int progreso = values[0].intValue();

        //         pbarProgreso.setProgress(progreso);
        Log.d("segundo","doProgressUpdate");
    }

    @Override
    protected void onPreExecute() {
        Log.d("segundo","onPreExecute");
        try {
            //  mensaje.setText("Buscando Actualizacion");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //         pbarProgreso.setMax(100);
        //         pbarProgreso.setProgress(0);
    }


    @Override
    protected void onPostExecute(Boolean result) {
        Log.d("segundo","onPostEXecute");
        super.onPostExecute(false);
        //         if(result)
        //             Toast.makeText(MainHilos.this, "Tarea finalizada!",
        //                     Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCancelled() {
        Log.d("segundo","onCancelled");
        //        Toast.makeText(MainHilos.this, "Tarea cancelada!",
        //                Toast.LENGTH_SHORT).show();
    }
}



