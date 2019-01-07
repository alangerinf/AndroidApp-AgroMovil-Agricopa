package pe.ibao.agromovil.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.ContactoDAO;
import pe.ibao.agromovil.models.dao.CultivoDAO;
import pe.ibao.agromovil.models.dao.EmpresaDAO;
import pe.ibao.agromovil.models.dao.FundoDAO;
import pe.ibao.agromovil.models.dao.VariedadDAO;
import pe.ibao.agromovil.models.dao.VisitaDAO;
import pe.ibao.agromovil.models.dao.ZonaDAO;
import pe.ibao.agromovil.models.vo.entitiesDB.ContactoVO;
import pe.ibao.agromovil.models.vo.entitiesDB.CultivoVO;
import pe.ibao.agromovil.models.vo.entitiesDB.EmpresaVO;
import pe.ibao.agromovil.models.vo.entitiesDB.FundoVO;
import pe.ibao.agromovil.models.vo.entitiesDB.VariedadVO;
import pe.ibao.agromovil.models.vo.entitiesDB.ZonaVO;

public class ActivityBasic extends AppCompatActivity {

    private static List<ZonaVO> listZonas;
    private static List<String> listNombreZonas;

    private static List<EmpresaVO> listEmpresas;
    private static List<String> listNombreEmpresas;

    private static List<FundoVO> listFundos;
    private static List<String> listNombreFundos;

    private static List<CultivoVO> listCultivos;
    private static List<String> listNombreCultivos;

    private static List<VariedadVO> listVariedades;
    private static List<String> listNombreVariedades;

    private static List<ContactoVO> listContactos;
    private static List<String> listNombreContactos;

    String TAG = "1199232";
    private static Spinner spnZona;
    private static Spinner spnEmpresa;
    private static Spinner spnFundo;
    private static Spinner spnVariedad;
    private static Spinner spnCultivo;
    private static Spinner spnContacto;
    private static EditText eTextContacto;
    private static CheckBox checkPersonalizado;

    private static int idZona;
    private static int idEmpresa;
    private static int idFundo;
    private static int idCultivo;
    private static int idVariedad;
    private static int idVisita;
    private static int idContacto;
    private static String contactoPersonalizado;
    private static boolean isContactoPersonalizado;

    private static Bundle recibidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basics);
        //setupActionBar();

        eTextContacto= (EditText) findViewById(R.id.eTextContacto);
        spnZona = (Spinner) findViewById(R.id.spnZona);
        spnEmpresa = (Spinner) findViewById(R.id.spnEmpresa);
        spnFundo = (Spinner) findViewById(R.id.spnFundo);
        spnCultivo = (Spinner) findViewById(R.id.spnCultivo);
        spnVariedad = (Spinner) findViewById(R.id.spnVariedad);
        spnContacto = (Spinner) findViewById(R.id.spnContacto);
        eTextContacto = (EditText) findViewById(R.id.eTextContacto);
        checkPersonalizado = (CheckBox) findViewById(R.id.checkBox);
        spnContacto.setEnabled(false);
        //cargar los nombre a ram
        cargarZonas();
        cargarCultivos();

        //inicializando adaptadpres de spn iniciales
        ArrayAdapter<CharSequence> adaptadorZonas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreZonas);
        ArrayAdapter<CharSequence> adaptadorCultivos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreCultivos);
        //seteando esos adaptadores
        spnZona.setAdapter(adaptadorZonas);
        spnCultivo.setAdapter(adaptadorCultivos);


        //reciviendo parametros de la actividad anterior
        recibidos = getIntent().getExtras();
        if(recibidos!=null){//si se recibe datos
            //cargamos a ram el id de visita
            idVisita=recibidos.getInt("idVisita");
            Log.d(TAG, "SEND IS NOT NULL");

            //si no es la primera ves q se accede a editar
            if(recibidos.getInt("isFirst")==0){//si no es la primera vez q ingresan
                idContacto= recibidos.getInt("idContacto");
                //eTextContacto.setText(contacto);
                idEmpresa=recibidos.getInt("idEmpresa");
                idZona = new EmpresaDAO(getBaseContext()).consultarEmpresaByid(idEmpresa).getIdZona();
                idFundo =recibidos.getInt("idFundo");
                idCultivo=recibidos.getInt("idCultivo");
                idVariedad=recibidos.getInt("idVariedad");
                contactoPersonalizado = new VisitaDAO(this).buscarById((long)idVisita).getContactoPersonalizado();
                isContactoPersonalizado = new VisitaDAO(this).buscarById((long)idVisita).isStatusContactoPersonalizado();
                checkPersonalizado.setChecked(isContactoPersonalizado);
                eTextContacto.setText(contactoPersonalizado);
                Log.d(TAG,""+idEmpresa+" "+idFundo+" "+idCultivo+" "+idVariedad+" "+idVisita+" "+idContacto);
                Log.d(TAG,"ID EMPRESA = "+(idEmpresa));
                Log.d(TAG,"ID ZONA = "+(idZona));
                for(int w=0;w<listZonas.size();w++){
                    if(listZonas.get(w).getId()==idZona){
                        Log.d(TAG,"Zona ENCONTRADA POS = "+(w/*+1*/));
                        spnZona.setSelection(w/*+1*/);
                        spnZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int q, long l) {
                              //  if(q != 0){
                                    cargarEmpresas();
                                    spnEmpresa.setEnabled(true);
                                    ArrayAdapter<CharSequence> adaptadorEmpresas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEmpresas);
                                    spnEmpresa.setAdapter(adaptadorEmpresas);
                                    for(int i=0;i<listEmpresas.size();i++){
                                        if(listEmpresas.get(i).getId()==idEmpresa){
                                            Log.d(TAG,"EMPRESA ENCONTRADA POS = "+i/*+1*/);
                                            spnEmpresa.setSelection(i/*+1*/);
                                            spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                    //if(i != 0){
                                                        cargarFundos();
                                                        spnFundo.setEnabled(true);
                                                        ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreFundos);
                                                        spnFundo.setAdapter(adaptadorFundos);
                                                        for(int j = 0 ; j<listFundos.size();j++){
                                                            if(listFundos.get(j).getId()==idFundo){
                                                                spnFundo.setSelection(j/*+1*/);
                                                                spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()  {
                                                                    @Override
                                                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                                        //if(position !=0){
                                                                            cargarContactos();
                                                                            spnContacto.setEnabled(true);
                                                                            ArrayAdapter<CharSequence> adaptadorContactos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreContactos);
                                                                            spnContacto.setAdapter(adaptadorContactos);
                                                                            for(int k =0 ; k< listContactos.size();k++){
                                                                                if(listContactos.get(k).getId()==idContacto){
                                                                                    spnContacto.setSelection(k/*+1*/);
                                                                                }
                                                                            }
                                                                        /*}else{
                                                                            spnContacto.setEnabled(false);
                                                                            spnContacto.setSelection(0);
                                                                        }*/
                                                                    }

                                                                    @Override
                                                                    public void onNothingSelected(AdapterView<?> parent) {

                                                                    }
                                                                });


                                                            }
                                                        }

                                /*                    }else{
                                                        spnFundo.setEnabled(false);
                                                        spnFundo.setSelection(0);
                                                    }
                                  */              }
                                                public void onNothingSelected(AdapterView<?> adapterView) {
                                                    return;
                                                }
                                            });
                                            break;
                                        }
                                    }
                                /*}else{
                                    spnEmpresa.setEnabled(false);
                                    spnEmpresa.setSelection(0);
                                    spnFundo.setEnabled(false);
                                    spnFundo.setSelection(0);
                                }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });
                        break;
                    }else {
                        Log.d(TAG,"Zona no ENCONTRADA POS = "+(w/*+1*/));
                    }
                }

                for(int i=0;i<listCultivos.size();i++) {
                    if (listCultivos.get(i).getId() == idCultivo) {
                        Log.d(TAG, "CULTIVO ENCONTRADA POS = " + i /*+ 1*/);
                        spnCultivo.setSelection(i /*+ 1*/);
                        spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                               // if(i != 0){
                                    cargarVariedades();
                                    spnVariedad.setEnabled(true);
                                    ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreVariedades);
                                    spnVariedad.setAdapter(adaptadorFundos);
                                    for(int j = 0 ; j<listVariedades.size();j++){
                                        if(listVariedades.get(j).getId()==idVariedad){
                                            spnVariedad.setSelection(j/*+1*/);
                                        }
                                    }/*
                                }else{
                                    spnVariedad.setEnabled(false);
                                    spnVariedad.setSelection(0);
                                }*/
                            }
                            public void onNothingSelected(AdapterView<?> adapterView) {
                                return;
                            }
                        });

                    }
                }



            }else {//si es la primera vez q ingresan
                Log.d(TAG, "es primera edicion");
                spnEmpresa.setEnabled(false);
                spnZona.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int q, long l) {
                        //if(q != 0){
                            cargarEmpresas();
                            spnEmpresa.setEnabled(true);
                            ArrayAdapter<CharSequence> adaptadorEmpresas = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreEmpresas);
                            spnEmpresa.setAdapter(adaptadorEmpresas);
                            spnEmpresa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                 //   if(i != 0){
                                        cargarFundos();
                                        spnFundo.setEnabled(true);
                                        ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreFundos);
                                        spnFundo.setAdapter(adaptadorFundos);
                                        spnFundo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            //    if(position != 0){
                                                    cargarContactos();
                                                    spnContacto.setEnabled(true);
                                                    ArrayAdapter<CharSequence> adaptadorContactos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreContactos);
                                                    spnContacto.setAdapter(adaptadorContactos);
                                            /*   }else{
                                                    spnContacto.setEnabled(false);
                                                    spnContacto.setSelection(0);
                                                }
                                            */
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> parent) {

                                            }
                                        });
                                   /* }else{
                                        spnFundo.setEnabled(false);
                                        spnFundo.setSelection(0);
                                    }*/
                                }
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                    return;
                                }
                            });

                        /*}else{
                            spnEmpresa.setSelection(0);
                            spnEmpresa.setEnabled(false);
                            spnFundo.setSelection(0);
                            spnFundo.setEnabled(false);
                        }*/
                    }
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });




                spnCultivo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        //if(i != 0){
                            cargarVariedades();
                            spnVariedad.setEnabled(true);
                            ArrayAdapter<CharSequence> adaptadorFundos = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listNombreVariedades);
                            spnVariedad.setAdapter(adaptadorFundos);
                        /*}else{
                            spnVariedad.setEnabled(false);
                            spnVariedad.setSelection(0);
                        }*/
                    }
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        return;
                    }
                });
                cargarVariedades();
            }
        }

        checkPersonalizado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    eTextContacto.setVisibility(View.VISIBLE);
                    spnContacto.setVisibility(View.INVISIBLE);
                }else{
                    eTextContacto.setVisibility(View.INVISIBLE);
                    spnContacto.setVisibility(View.VISIBLE);
                }

            }
        });

        if(isContactoPersonalizado){
            checkPersonalizado.setChecked(true);
            eTextContacto.setVisibility(View.VISIBLE);
            spnContacto.setVisibility(View.INVISIBLE);
        }else {
            checkPersonalizado.setChecked(false);
            eTextContacto.setVisibility(View.INVISIBLE);
            spnContacto.setVisibility(View.VISIBLE);
        }
    }

    private void cargarVariedades() {
        VariedadDAO variedadDAO = new VariedadDAO(getBaseContext());
        int posCultivo = spnCultivo.getSelectedItemPosition();
        listVariedades = variedadDAO.listarVariedadByIdCultivo(listCultivos.get(posCultivo/*-1*/).getId());
        cargarNombreVariedades();
    }

    private void cargarNombreVariedades() {
        listNombreVariedades = new ArrayList<String>();
        //listNombreVariedades.add(getString(R.string.cabezeraSpnVariedad));
        for(VariedadVO var : listVariedades){
            listNombreVariedades.add(var.getName());
        }
    }

    private void cargarCultivos(){
        CultivoDAO cultivoDAO = new CultivoDAO(getBaseContext());
        listCultivos = cultivoDAO.listCultivos();
        cargarNombreCultivos();
    }

    private void cargarNombreCultivos() {
        listNombreCultivos = new ArrayList<String>();
    //    listNombreCultivos.add(getString(R.string.cabezeraSpnCultivo));
        for(CultivoVO cul : listCultivos){
            listNombreCultivos.add(cul.getName());
        }
    }

    private void cargarFundos() {
        FundoDAO fundoDAO = new FundoDAO(getBaseContext());
        int posEmpresa = spnEmpresa.getSelectedItemPosition();
        listFundos = fundoDAO.listarByIdEmpresa(listEmpresas.get(posEmpresa/*-1*/).getId());
        cargarNombreFundos();
    }

    private void cargarNombreFundos(){
        listNombreFundos = new ArrayList<String>();
    //    listNombreFundos.add(getString(R.string.cabezeraSpnFundo));
        for(FundoVO fun : listFundos){
            listNombreFundos.add(fun.getName());
        }
    }

    private void cargarZonas() {
        ZonaDAO zonaDAO = new ZonaDAO(getBaseContext());
        listZonas = zonaDAO.listZonas();
      //  Toast.makeText(getBaseContext(),""+listZonas.size(),Toast.LENGTH_LONG).show();
        cargarNombreZonas();
    }
    private void cargarNombreZonas(){
        listNombreZonas = new ArrayList<String>();
      //  listNombreZonas.add(getString(R.string.cabezeraSpnZona));
        for(ZonaVO zon : listZonas){
            listNombreZonas.add(zon.getName());
        }
    }


    private void cargarEmpresas() {
        EmpresaDAO empresaDAO = new EmpresaDAO(getBaseContext());
        int posZona = spnZona.getSelectedItemPosition();
        listEmpresas = empresaDAO.listEmpresasByIdZona(listZonas.get(posZona/*-1*/).getId());
        cargarNombreEmpresas();
    }
    private void cargarNombreEmpresas(){
        listNombreEmpresas = new ArrayList<String>();
        //listNombreEmpresas.add(getString(R.string.cabezeraSpnEmpresa));
        for(EmpresaVO emp : listEmpresas){
            listNombreEmpresas.add(emp.getName());
        }
    }
    private void cargarContactos() {
        ContactoDAO contactoDAO = new ContactoDAO(getBaseContext());
        int posFundo = spnFundo.getSelectedItemPosition();
        listContactos = contactoDAO.listarByIdFundo(listFundos.get(posFundo/*-1*/).getId());
        cargarNombreContactos();
    }
    private void cargarNombreContactos() {
        listNombreContactos = new ArrayList<String>();
      //  listNombreContactos.add(getString(R.string.cabezeraSpnContacto));
        for(ContactoVO co : listContactos){
            listNombreContactos.add(co.getName());
        }
    }

/*
    private void setupActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }*/
    @Override
    public boolean onSupportNavigateUp() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
        onBackPressed();
        return false;
    }

    public void pressListo(View view){

        Intent returnIntent = new Intent();

        /*
        if(spnEmpresa.getSelectedItemPosition()!=0
            && spnCultivo.getSelectedItemPosition()!=0
            && spnFundo.getSelectedItemPosition()!=0
                && spnVariedad.getSelectedItemPosition()!=0

            ){
*/
            if(
                    (
                            (/*spnContacto.getSelectedItemPosition()!=0
                            */
                                    listContactos.size()>0
                            )&&
                            !checkPersonalizado.isChecked()
                    )
                    ||
                    (
                        checkPersonalizado.isChecked()
                        &&
                        !(
                                eTextContacto.getText().toString()==null
                                ||
                                eTextContacto.getText().toString().equals("")
                        )
                    )
            ){

                idFundo = listFundos.get(spnFundo.getSelectedItemPosition()/*-1*/).getId();
                idVariedad = listVariedades.get(spnVariedad.getSelectedItemPosition()/*-1*/).getId();
                String nameContacto ="";
                if(checkPersonalizado.isChecked()){
                    idContacto=0;
                    isContactoPersonalizado=true;
                    contactoPersonalizado=eTextContacto.getText().toString();
                    nameContacto=contactoPersonalizado;
                }else{
                    idContacto = listContactos.get(spnContacto.getSelectedItemPosition()/*-1*/).getId();
                    isContactoPersonalizado=false;
                    contactoPersonalizado="";
                    nameContacto=spnContacto.getSelectedItem().toString();
                }
                returnIntent.putExtra(ActivityVisita.REQUEST_EMPRESA,spnEmpresa.getSelectedItem().toString());
                returnIntent.putExtra(ActivityVisita.REQUEST_FUNDO,spnFundo.getSelectedItem().toString());
                returnIntent.putExtra(ActivityVisita.REQUEST_CULTIVO,spnCultivo.getSelectedItem().toString());
                returnIntent.putExtra(ActivityVisita.REQUEST_VARIEDAD,spnVariedad.getSelectedItem().toString());
                returnIntent.putExtra(ActivityVisita.REQUEST_CONTACTO,nameContacto);

                VisitaDAO visitaDAO = new VisitaDAO(getBaseContext());
                boolean res =  visitaDAO.cambiarIdFundoIdVariedadIdContactoIsPersonalizadoContacto(idVisita,idFundo,idVariedad,idContacto,isContactoPersonalizado,contactoPersonalizado);
                if(!res){
                    Toast.makeText(getBaseContext(),"Error al intententar Modificar",Toast.LENGTH_SHORT).show();
                }else{
                   // Toast.makeText(getBaseContext(),"Informacion Actualizada",Toast.LENGTH_SHORT).show();
                }

                setResult(Activity.RESULT_OK,returnIntent);
                finish();

            }else{
                Toast.makeText(getBaseContext(),"Elija un Contacto",Toast.LENGTH_SHORT).show();
            }

  /*      }else{
            Toast.makeText(getBaseContext(),"Seleccione todos los campos",Toast.LENGTH_SHORT).show();
        }
*/
    }

}
