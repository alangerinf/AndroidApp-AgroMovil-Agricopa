package pe.ibao.agromovil.views;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.helpers.adapters.AdapterListRecomendacion;
import pe.ibao.agromovil.models.dao.CriterioRecomendacionDAO;
import pe.ibao.agromovil.models.dao.RecomendacionDAO;
import pe.ibao.agromovil.models.dao.TipoRecomendacionDAO;
import pe.ibao.agromovil.models.dao.VisitaDAO;
import pe.ibao.agromovil.models.vo.entitiesDB.CriterioRecomendacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.RecomendacionVO;
import pe.ibao.agromovil.models.vo.entitiesDB.TipoRecomendacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;

public class ActivityRecomendacion extends Activity {

    private static List<TipoRecomendacionVO> listTipoRecomendaciones;
    private static List<CriterioRecomendacionVO> listCriterioRecomendaciones;

    private static boolean isEditable;
    private static VisitaVO visita;
    private static CriterioRecomendacionVO criterioRecomendacion;
    private static TipoRecomendacionVO tipoRecomendacion;
    private static List<RecomendacionVO> listRecomendaciones;

    private static TextView tViewTipoRecomendacion;
    static ListView lViewRecomendaciones;

     private static int lastCriterioRecomendacionSelect=0;
    private AdapterListRecomendacion adapterListRecomendacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendaciones);
        tViewTipoRecomendacion = (TextView) findViewById(R.id.tViewTipoRecomendacion);
        lViewRecomendaciones = (ListView) findViewById(R.id.listRecomedacion);

        Intent i = getIntent();

        Bundle b = i.getExtras();
            isEditable= b.getBoolean("isEditable",false);
            tipoRecomendacion = new TipoRecomendacionDAO(this).consultarByid(b.getInt("idTipoRecomendacion"));
            visita = new VisitaDAO(this).buscarById((long)b.getInt("idVisita"));
            tViewTipoRecomendacion.setText(tipoRecomendacion.getName());

        listRecomendaciones = new RecomendacionDAO(getBaseContext())
                .listarByIdTipoRecomendacionIdVisita(tipoRecomendacion.getId()
                        ,visita.getId());
        //Toast.makeText(getBaseContext(),""+listRecomendaciones.size(),Toast.LENGTH_LONG).show();
        //actualizar adaptador
        adapterListRecomendacion = new AdapterListRecomendacion(getBaseContext(),listRecomendaciones,isEditable);
        lViewRecomendaciones.setAdapter(adapterListRecomendacion);

        if(!isEditable){
            addInvisible();
        }
    }

    @SuppressLint("RestrictedApi")
    private void addInvisible(){
        FloatingActionButton plus = (FloatingActionButton) findViewById(R.id.fButton_ReturnVisita);
        plus.setClickable(false);
        plus.setVisibility(View.INVISIBLE);
    }

    public void end(View view) {
       onBackPressed();
    }


    public void showListTipoRecomendacion(View view){

        if(adapterListRecomendacion.isAllValid()){
            // if(isEditable){
            listTipoRecomendaciones = new TipoRecomendacionDAO(getBaseContext()).listarByIdFundoIdVariedad(visita.getIdFundo(),visita.getIdVariedad());
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

            final CharSequence[] items = new CharSequence[ listTipoRecomendaciones.size()];

            for(int i = 0; i< listTipoRecomendaciones.size(); i++){
                items[i]= listTipoRecomendaciones.get(i).getName();
            }

            dialogo.setTitle("Tipos de Recomendacion")
                    .setSingleChoiceItems(items, ActivityVisita.lastTipoRecomendacionSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if(ActivityVisita.lastTipoRecomendacionSelected!=which){
                                lastCriterioRecomendacionSelect=0;
                            }
                            ActivityVisita.lastTipoRecomendacionSelected = which;
                            /*
                            Toast.makeText(
                                    getBaseContext(),
                                    "Seleccionaste: " + listTipoRecomendaciones.get(which).getName(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                            */
                            tipoRecomendacion = listTipoRecomendaciones.get(which);
                            tViewTipoRecomendacion.setText(tipoRecomendacion.getName());
                            //actualizar datos de toda la pagina
                            listRecomendaciones = new RecomendacionDAO(getBaseContext())
                                    .listarByIdTipoRecomendacionIdVisita(tipoRecomendacion.getId()
                                            ,visita.getId());
                            // Toast.makeText(getBaseContext(),""+listRecomendaciones.size(),Toast.LENGTH_LONG).show();
                            //actualizar adaptador
                            adapterListRecomendacion = new AdapterListRecomendacion(getBaseContext(),listRecomendaciones,isEditable);
                            lViewRecomendaciones.setAdapter(adapterListRecomendacion);
                        }
                    });
            dialogo.show();
            //}

        }else {
            Toast.makeText(getBaseContext(),"Llene todo los campos Cantidad para continuar",Toast.LENGTH_SHORT).show();
        }




    }

    public void showListCriterioRecomendacion(View view){
        if(isEditable){
            listCriterioRecomendaciones = new CriterioRecomendacionDAO(getBaseContext()).listarByIdTipoRecomendacionIdFundoIdVariedad(tipoRecomendacion.getId(),visita.getIdFundo(),visita.getIdVariedad());
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
            final CharSequence[] items = new CharSequence[ listCriterioRecomendaciones.size()];
            for(int i = 0; i< listCriterioRecomendaciones.size(); i++){
                items[i]= listCriterioRecomendaciones.get(i).getName();
            }
            dialogo.setTitle("Recomendaciones")
                    .setSingleChoiceItems(items, lastCriterioRecomendacionSelect, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                            lastCriterioRecomendacionSelect = which;
                            /*
                            Toast.makeText(
                                    getBaseContext(),
                                    "Seleccionaste: "+ listCriterioRecomendaciones.get(which).getName(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                            */
                            criterioRecomendacion = listCriterioRecomendaciones.get(which);

                            new RecomendacionDAO(getBaseContext())
                                    .nuevoByIdCriterioRecomendacionIdVisita(criterioRecomendacion.getId()
                                                                            ,visita.getId());

                            listRecomendaciones = new RecomendacionDAO(getBaseContext())
                                    .listarByIdTipoRecomendacionIdVisita(tipoRecomendacion.getId()
                                                                        ,visita.getId());
                            //Toast.makeText(getBaseContext(),""+listRecomendaciones.size(),Toast.LENGTH_LONG).show();
                            //actualizar adaptador
                            adapterListRecomendacion = new AdapterListRecomendacion(getBaseContext(),listRecomendaciones,isEditable);
                            lViewRecomendaciones.setAdapter(adapterListRecomendacion);
                        }
                    });
            dialogo.show();
        }

    }

    @Override
    public void onBackPressed() {
        if(adapterListRecomendacion.isAllValid()){
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
            overridePendingTransition(R.anim.fade_in, R.anim.top_out);
        }else {
            Toast.makeText(getBaseContext(),"Llene todo los campos Cantidad para continuar",Toast.LENGTH_SHORT).show();
        }
    }
}
