package pe.ibao.agromovil.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.ContactoDAO;
import pe.ibao.agromovil.models.dao.CultivoDAO;
import pe.ibao.agromovil.models.dao.EmpresaDAO;
import pe.ibao.agromovil.models.dao.EvaluacionDAO;
import pe.ibao.agromovil.models.dao.FundoDAO;
import pe.ibao.agromovil.models.dao.TipoRecomendacionDAO;
import pe.ibao.agromovil.models.dao.VariedadDAO;
import pe.ibao.agromovil.models.dao.VisitaDAO;
import pe.ibao.agromovil.models.vo.entitiesDB.TipoRecomendacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.EvaluacionVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;

public class ActivityVisita extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
        ,LocationListener {


    public static final String TAG = ActivityVisita.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;

    static final public int REQUEST_PERMISION_GPS=12;
    static final public int REQUEST_BASICS_DATA = 1;  // The request code
    static final public int REQUEST_EDIT_EVALUATION=2;
    static final public String REQUEST_EMPRESA = "empresa_request";
    static final public String REQUEST_FUNDO = "fundo_request";
    static final public String REQUEST_CULTIVO = "cultivo_request";
    static final public String REQUEST_VARIEDAD = "variedad_request";
    static final public String REQUEST_CONTACTO = "contacto_request";

    private TextView tViewEmpresa;
    private TextView tViewContacto;
    private TextView tViewFundo;
    private TextView tViewCultivo;
    private TextView tViewVariedad;
    private TextView tViewHora;

    private Dialog dialogClose;
    private Button btnDialogClose;
    private ImageView iViewDialogClose;

    private  ListView listViewEvaluaciones;
    private VisitaDAO visitaDAO;
    private static VisitaVO visita;
    private static BaseAdapter baseAdapter;
    private  List<EvaluacionVO> evaluacionVOList = new ArrayList<>();

    public static Context ctx;
    static Bundle xx;

    public static int lastTipoRecomendacionSelected=0;

    private static List<TipoRecomendacionVO> listTipoRecomendaciones;



    static Button btnFinalizar;
    static FloatingActionButton floatBtnNuevo;

    private static boolean isEditable;
    private static boolean isClosedVisita;

    public static int REQUEST_RECOMENDACION=56;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xx= savedInstanceState;
        setContentView(R.layout.activity_new_visita);
       // setupActionBar();
        ctx=this;

        Intent i = getIntent();
        Bundle b = i.getExtras();

        isEditable= b.getBoolean("isEditable",false);
        isClosedVisita = b.getBoolean("isClosedVisita",true);
        int idTemp =b.getInt("idVisita");

        visitaDAO = new VisitaDAO(this);
        visita = visitaDAO.buscarById((long)idTemp);

        tViewEmpresa    = (TextView) findViewById(R.id.tViewEmpresa);
        tViewContacto   = (TextView) findViewById(R.id.tViewContacto);
        tViewFundo      = (TextView) findViewById(R.id.tViewFundo);
        tViewCultivo    = (TextView) findViewById(R.id.tViewCultivo);
        tViewVariedad   = (TextView) findViewById(R.id.tViewVariedad);
        tViewHora       = (TextView) findViewById(R.id.tViewHora);

        btnFinalizar    = (Button) findViewById(R.id.button_ok);
        floatBtnNuevo   = (FloatingActionButton) findViewById(R.id.floatingNuevoCriterio);
        listViewEvaluaciones = (ListView) findViewById(R.id.list_evaluaciones);

        dialogClose = new Dialog(this);

        if(!isEditable){
            btnFinalizar.setVisibility(View.INVISIBLE);
            floatBtnNuevo.setVisibility(View.INVISIBLE);
            ((FloatingActionButton) findViewById(R.id.floatingActionButton)).setTranslationY(120);
            setTitle("Datos Inspección");
        }

        EvaluacionDAO evaluacionDAO = new EvaluacionDAO(this);
        evaluacionVOList.addAll(evaluacionDAO.listarByIdVisita(visita.getId()));

        baseAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return evaluacionVOList.size();
            }

            @Override
            public Object getItem(int position) {
                return evaluacionVOList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return evaluacionVOList.get(position).getId();
            }


            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View v = convertView;
                final LayoutInflater inflater;
                inflater = LayoutInflater.from(getBaseContext());
                v = inflater.inflate(R.layout.evaluacion_itemeditor_list_view,null);

                TextView tViewTipoInspeccion= (TextView) v.findViewById(R.id.tipo_inspeccion);
                TextView tViewFechaHora     = (TextView) v.findViewById(R.id.fecha_hora);
                TextView tViewPorcerntaje   = (TextView) v.findViewById(R.id.porcentaje);
                ImageView btnDelete         = (ImageView)v.findViewById(R.id.deleter);

                if(!isEditable){
                    btnDelete.setVisibility(View.INVISIBLE);
                }

                v.setClickable(true);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EvaluacionVO evtemp =evaluacionVOList.get(position);

                        Intent intent = new Intent(getBaseContext(),ActivityEvaluacion.class);
                        Bundle mybundle = new Bundle();
                        //visita = new VisitaDAO(ctx).getEditing();
                            mybundle.putInt("idEvaluacion",evtemp.getId());
                            mybundle.putInt("idTipoInspeccion",evtemp.getIdTipoInspeccion());
                            mybundle.putInt("idVariedad",visita.getIdVariedad());
                            mybundle.putInt("idFundo",visita.getIdFundo());
                            mybundle.putInt("isNewTest",0);
                            mybundle.putString("fechaHora",evtemp.getTimeIni());
                            mybundle.putBoolean("isEditable",isEditable);
                        intent.putExtras(mybundle);
                        ActivityEvaluacion.setLastItemSelected(0);
                        startActivityForResult(intent,REQUEST_EDIT_EVALUATION);

                    }
                });



                btnDelete.setClickable(true);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //tomarFoto();
                        //Toast.makeText(this,"Borrando"+position,Toast.LENGTH_LONG).show();
                        try{
                           // Toast.makeText(getBaseContext(),"borrando->"+position,Toast.LENGTH_LONG).show();

                            dialogClose.setContentView(R.layout.dialog_danger);
                            Button btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close);
                            Button btnDialogAcept = (Button) dialogClose.findViewById(R.id.buton_acept);
                            iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);
                            TextView mensaje = (TextView) dialogClose.findViewById(R.id.textView11);
                            mensaje.setText("Esta a punto de eliminar una evaluación\n¿DESEA CONTINUAR?");
                            iViewDialogClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogClose.dismiss();
                                }
                            });
                            btnDialogClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialogClose.dismiss();

                                }
                            });

                            btnDialogAcept.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    EvaluacionDAO evaluacionDAO = new EvaluacionDAO(getBaseContext());
                                    evaluacionDAO.borrarById(evaluacionVOList.get(position).getId());
                                    evaluacionVOList.remove(position);
                                    baseAdapter.notifyDataSetChanged();
                                    setListViewHeightBasedOnChildren(listViewEvaluaciones);
                                    dialogClose.dismiss();

                                }
                            });

                            dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialogClose.show();




                            //autoAdapter();
                        }catch (Exception e){
                            Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
                            Log.d("qwerty",e.toString());
                        }
                    }
                });

                tViewFechaHora.setText(evaluacionVOList.get(position).getTimeIni());

                int por = evaluacionVOList.get(position).getPorcentaje();
                tViewPorcerntaje.setText(String.valueOf(por+"%"));

                if(evaluacionVOList.get(position).getNameInspeccion()==null){
                    tViewTipoInspeccion.setText("Sin Tipo");
                }else{
                    tViewTipoInspeccion.setText(evaluacionVOList.get(position).getNameInspeccion());
                }

                int porcentaje = por;
                if(porcentaje>=100){
                    tViewPorcerntaje.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.p100));

                }else {
                    if(porcentaje>=80){
                        tViewPorcerntaje.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.p80));
                    }else{
                        if(porcentaje>=60){
                            tViewPorcerntaje.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.p60));
                        }else{
                            if(porcentaje>=40){
                                tViewPorcerntaje.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.p40));
                            }else{
                                if(porcentaje>=20){
                                    tViewPorcerntaje.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.p20));
                                }else{
                                    if(porcentaje>=0){
                                        tViewPorcerntaje.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.p0));
                                    }
                                }
                            }
                        }
                    }
                }
                return v;
            }

        };

        if(visita.getIdEmpresa()>0){
            EmpresaDAO empresaDAO  = new EmpresaDAO(this);
            tViewEmpresa.setText(empresaDAO.consultarEmpresaByid(visita.getIdEmpresa()).getName());
        }


        if(visita.getIdFundo()>0){
            FundoDAO fundoDAO = new FundoDAO(this);
            tViewFundo.setText(fundoDAO.consultarById(visita.getIdFundo()).getName());

        }
        if(visita.getIdCultivo()>0){
            CultivoDAO cultivoDAO = new CultivoDAO(this);
            tViewCultivo.setText(cultivoDAO.consultarCultivoByid(visita.getIdCultivo()).getName());
        }
        if(visita.getIdVariedad()>0){
            VariedadDAO variedadDAO = new VariedadDAO(this);
            tViewVariedad.setText(variedadDAO.consultarVariedadById(visita.getIdVariedad()).getName());
        }

        if(!visita.isStatusContactoPersonalizado()){
            if(visita.getIdContacto()>0){
                ContactoDAO contactoDAO = new ContactoDAO(this);
                tViewContacto.setText(contactoDAO.consultarContactoByid(visita.getIdContacto()).getName());
            }
        }else{
                tViewContacto.setText(visita.getContactoPersonalizado());
        }


        if(visita.getFechaHoraIni()!=null){
            tViewHora.setText(visita.getFechaHoraIni());
        }

        listViewEvaluaciones.setAdapter(baseAdapter);
        setListViewHeightBasedOnChildren(listViewEvaluaciones);
/*
        if(evaluacionVOList.size()==0){
            Toast.makeText(
                    this,
                    "Agrege Evaluaciones",
                    Toast.LENGTH_SHORT)
                    .show();
        }
*/

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationRequest = LocationRequest.create ()
                .setPriority (LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval (1 * 1000) // 10 segundos, en milisegundos
                .setFastestInterval (1 * 1000); // 1 segundo, en milisegundos

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISION_GPS) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

                if (location == null) {
                    Log.i(TAG, "locacion nula al conectar");
                    //      LocationServices.FusedLocationApi.requestLocationUpdates (mGoogleApiClient, mLocationRequest, this);
                }
                else {
                    handleNewLocation (location);
                }

            } else {
                //Permiso denegado:
                //Deberíamos deshabilitar toda la funcionalidad relativa a la localización.

                Log.e(TAG, "Permiso denegado");
            }
        }
    }


    public void openEvaluacion(View view){
        Intent intent = new Intent(this,ActivityEvaluacion.class);

        /**falta insertar primero en ta tabla  evaluaciones
        con el id de la visita
        **/

        if(visita.getIdFundo()>0 && visita.getIdVariedad()>0){
            EvaluacionVO evtemp =  new EvaluacionDAO(ctx).nuevoByIdVisita(-8045.56,262.9,visita.getId());
            Bundle mybundle = new Bundle();
            mybundle.putInt("idEvaluacion",evtemp.getId());
            mybundle.putInt("idTipoInspeccion",evtemp.getIdTipoInspeccion());
            mybundle.putInt("idVariedad",visita.getIdVariedad());
            mybundle.putInt("idFundo",visita.getIdFundo());
            mybundle.putInt("isNewTest",0);
            mybundle.putString("fechaHora",evtemp.getTimeIni());
            mybundle.putBoolean("isEditable",isEditable);
            /*evaluacionVOList= new EvaluacionDAO(ctx).listarByIdVisita(visita.getId());
            baseAdapter.notifyDataSetChanged();
            setListViewHeightBasedOnChildren(listViewEvaluaciones);
            */
            intent.putExtras(mybundle);
            ActivityEvaluacion.setLastItemSelected(0);
            startActivityForResult(intent,REQUEST_EDIT_EVALUATION);
        }else{
            Toast.makeText(ctx,"Primero configura tus Datos Basicos",Toast.LENGTH_LONG).show();
        }

    }

/*
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
*/





    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    public void onBackPressed() {
        if(isEditable){
            boolean temp = new VisitaDAO(getBaseContext()).buscarById((long)visita.getId()).isEditing();
            if(temp){
                showClosePopup();
            }else{
                finish();
            }

        }else {
            finish();
        }
        //    moveTaskToBack(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to

        //Toast.makeText(this,"onactivity resytk",Toast.LENGTH_LONG).show();
       // visita = new VisitaDAO(ctx).getEditing();
        switch (requestCode){
            case REQUEST_BASICS_DATA :
                // Make sure the request was successful
                if (resultCode == RESULT_OK) {
                    String temp = data.getStringExtra(REQUEST_FUNDO);
                    tViewFundo.setText(temp);
                    temp = data.getStringExtra(REQUEST_EMPRESA);
                    tViewEmpresa.setText(temp);
                    temp=data.getStringExtra(REQUEST_CULTIVO);
                    tViewCultivo.setText(temp);
                    temp=data.getStringExtra(REQUEST_VARIEDAD);
                    tViewVariedad.setText(temp);
                    temp=data.getStringExtra(REQUEST_CONTACTO);
                    tViewContacto.setText(temp);
                    visita = new VisitaDAO(ctx).buscarById((long)visita.getId());
                }else{
                    Toast.makeText(ctx,"Edición no Guardada",Toast.LENGTH_LONG).show();
                }

            break;
            case REQUEST_EDIT_EVALUATION :
                evaluacionVOList = new EvaluacionDAO(this)
                        .listarByIdVisita(
                                visita.getId()
                        );
                baseAdapter.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listViewEvaluaciones);
        }

    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
        //listView.setDividerHeight(params.height);
    }

    public void openbasics(View view){

        if(isEditable){
            if(!evaluacionVOList.isEmpty()) {
                Toast.makeText(ctx, "No puede editar, ya tiene evaluaciones agregadas",Toast.LENGTH_LONG).show();
            }else{
                Intent intent = new Intent(this,ActivityBasic.class);
                Bundle mybundle = new Bundle();
                if(visita.getIdFundo()>0 && visita.getIdVariedad()>0){
                    mybundle.putInt("isFirst",0);
                }else{
                    mybundle.putInt("isFirst",1);
                }
                mybundle.putInt("idEmpresa",visita.getIdEmpresa());
                mybundle.putInt("idFundo",visita.getIdFundo());
                mybundle.putInt("idCultivo",visita.getIdCultivo());
                mybundle.putInt("idVariedad",visita.getIdVariedad());
                mybundle.putInt("idVisita",visita.getId());
                mybundle.putInt("idContacto",visita.getIdContacto());
                intent.putExtras(mybundle);
                startActivityForResult(intent, REQUEST_BASICS_DATA);
            }
        }
    }




    public void showListTipoRecomendacion(View view){

        if(visita.getIdFundo()>0 && visita.getIdVariedad()>0){
            //if(isEditable){
            listTipoRecomendaciones = new TipoRecomendacionDAO(getBaseContext()).listarByIdFundoIdVariedad(visita.getIdFundo(),visita.getIdVariedad());
            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);

            final CharSequence[] items = new CharSequence[ listTipoRecomendaciones.size()];

            for(int i = 0; i< listTipoRecomendaciones.size(); i++){
                items[i]= listTipoRecomendaciones.get(i).getName();
            }

            dialogo.setTitle("Tipos de Recomendación")
                    .setSingleChoiceItems(items, lastTipoRecomendacionSelected, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            lastTipoRecomendacionSelected = which;
                           /*
                            Toast.makeText(
                                    getBaseContext(),
                                    "Seleccionaste: " + listTipoRecomendaciones.get(which).getName(),
                                    Toast.LENGTH_SHORT)
                                    .show();
                           */
                            TipoRecomendacionVO temp = listTipoRecomendaciones.get(which);
                            Intent i = new Intent(getBaseContext(), ActivityRecomendacion.class);
                            i.putExtra("idVisita",visita.getId());
                            i.putExtra("idTipoRecomendacion",temp.getId());
                            i.putExtra("isEditable",isEditable);
                            startActivityForResult(i, REQUEST_RECOMENDACION);//cambie  aqui de 1
                            overridePendingTransition(R.anim.bot_in, R.anim.fade_out);


                        }
                    });
            dialogo.show();
            //}
        }else{
                Toast.makeText(ctx,"Primero configura tus Datos Basicos",Toast.LENGTH_LONG).show();
        }


    }

    public void end(View view){
        //verificando si todas las listas  estan en 100% de completadas
        if(isEditable){
            boolean flag=true;
            for(EvaluacionVO ev : evaluacionVOList){
                if(ev.getPorcentaje()!=100){
                    flag=false;
                    break;
                }
            }
            if(evaluacionVOList.isEmpty()){
                flag=false;
            }
            if(flag){
                //guardar y finalizar
                if(new VisitaDAO(ctx).save(visita.getId())){
                    /*
                    Toast.makeText(
                            getBaseContext()
                            ,"Visita Guardada"
                            ,Toast.LENGTH_SHORT).show();
                    */
                    Intent i = new Intent(this,ActivityMain.class);
                    startActivity(i);
                    if(!isClosedVisita){
                        new VisitaDAO(ctx).setLatLonFinById(visita.getId(),String.valueOf(currentLatitude),String.valueOf(currentLongitude));
                        new VisitaDAO(ctx).setFechaHoraFinById(visita.getId());
                    }

                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                }else{
                    Toast.makeText(
                            getBaseContext()
                            ,"Visita no se pudo guardar"
                            ,Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(
                        getBaseContext()
                        ,"Complete todas las  Evaluaciones para poder Finalizar"
                        ,Toast.LENGTH_LONG).show();
            }
        }else{
            finish();
        }


    }

    private void showClosePopup(){
        dialogClose.setContentView(R.layout.dialog_guardar_progreso);
        btnDialogClose = (Button) dialogClose.findViewById(R.id.buton_close_dialog);
        iViewDialogClose = (ImageView) dialogClose.findViewById(R.id.iViewDialogClose);

        iViewDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogClose.dismiss();
            }
        });
        btnDialogClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogClose.dismiss();
                finish();
            }
        });

        dialogClose.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogClose.show();
    }


    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG, "Location services connected.");

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISION_GPS);
        } else {

            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {
                Log.i(TAG, "locacion nula al conectar");
                LocationServices.FusedLocationApi.requestLocationUpdates (mGoogleApiClient, mLocationRequest, this);
            }
            else {
                handleNewLocation (location);
            }

        }



    }

    public void showComent(View view){
        final String coment = new VisitaDAO(getBaseContext()).buscarById((long)visita.getId()).getComentario();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comentario");

        // Set up the input
        final EditText input = new EditText(this);
        input.setText(coment);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);


// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(isEditable){
                    new VisitaDAO(getBaseContext()).setComentario(visita.getId(),input.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Location services suspended. Please reconnect.");
    }

    private static double currentLatitude= 0.0d;
    private static double currentLongitude= 0.0d;

    public static double getCurrentLatitude() {
        return currentLatitude;
    }

    public static void setCurrentLatitude(double currentLatitude) {
        ActivityVisita.currentLatitude = currentLatitude;
    }

    public static double getCurrentLongitude() {
        return currentLongitude;
    }

    public static void setCurrentLongitude(double currentLongitude) {
        ActivityVisita.currentLongitude = currentLongitude;
    }


    private void handleNewLocation(Location location) {
        Log.d(TAG,"hola " + location.toString());
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);
        Log.d(TAG,latLng.toString());
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution ()) {
            try {
                // Inicie una actividad que intente resolver el error
                connectionResult.startResolutionForResult (this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace ();
            }
        } else{
            Log.i (TAG, "La conexión de los servicios de ubicación falló con el código" + connectionResult.getErrorCode ());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGoogleApiClient.connect();
    }
    @Override
    protected void onPause() {
        super.onPause ();
        if (mGoogleApiClient.isConnected ()) {
            LocationServices.FusedLocationApi.removeLocationUpdates (mGoogleApiClient, this);
            mGoogleApiClient.disconnect ();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation (location);
    }

}
