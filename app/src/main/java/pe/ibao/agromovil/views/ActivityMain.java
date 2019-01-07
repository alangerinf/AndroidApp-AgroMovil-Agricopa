package pe.ibao.agromovil.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.models.dao.UsuarioDAO;
import pe.ibao.agromovil.models.dao.VisitaDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;

public class ActivityMain extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentMain.OnFragmentInteractionListener,
        FragmentListVisitas.OnFragmentInteractionListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
        ,LocationListener{

    public static final int REQUEST_PERMISION_GPS=12;
    private Fragment myFragment = null;
    public static final String TAG = ActivityMain.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private LocationRequest mLocationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myFragment = new FragmentMain();


        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();

        //verificar y estan actualizar de inmediato

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        String NombreU = new UsuarioDAO(getBaseContext()).verficarLogueo().getName();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        Menu menu = navigationView.getMenu();
        MenuItem inspeccion = menu.findItem(R.id.nav_new_inspection);
        ((TextView) headerView.findViewById(R.id.tViewUserName)).setText(NombreU);
        if (new VisitaDAO(getBaseContext()).getEditing() != null) {
            inspeccion.setTitle("Continuar Inspección");
        }


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            if (new VisitaDAO(getBaseContext()).listAll().size() > 0) {
                if(new VisitaDAO(getBaseContext()).getEditing()==null){
                    Toast.makeText(getBaseContext(), "   ¡ Aun no sincroniza\ntodas sus Inspecciones !", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(), "   ¡ No puede cerrar sesión\nculmine su inspección actual!", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Cerrando Sesión...", Toast.LENGTH_LONG).show();
                new UsuarioDAO(getBaseContext()).borrarTable();
                Intent intent = new Intent(getBaseContext(), ActivityPreloader.class);
                startActivity(intent);
                finish();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        boolean isFrame = false;
        Intent i = null;

        boolean isUpload = false;
        boolean isDownload = false;

        if (id == R.id.nav_new_inspection) {
            isFrame = false;
            i = new Intent(this, ActivityVisita.class);
            String lat="";
            String lon="";
            if(new VisitaDAO(this).getEditing()==null){
                lat = String.valueOf(getCurrentLatitude());
                lon =String.valueOf(getCurrentLongitude());
                VisitaVO visitaTemp = new VisitaDAO(this).intentarNuevo();
                new VisitaDAO(this).setLatLonIniById(visitaTemp.getId(),lat,lon);
                visitaTemp = new VisitaDAO(this).intentarNuevo();
                i.putExtra("isEditable", true);
                i.putExtra("idVisita", visitaTemp.getId());
            }else {
                VisitaVO visitaTemp = new VisitaDAO(this).intentarNuevo();
                i.putExtra("isEditable", true);
                i.putExtra("idVisita", visitaTemp.getId());
            }
            i.putExtra("isClosedVisita",false);

            myFragment = new FragmentMain();
        } else if (id == R.id.nav_recent_visits) {
            //Toast.makeText(getBaseContext(), "recent visit!",Toast.LENGTH_SHORT).show();
            isFrame = true;
            myFragment = new FragmentListVisitas();
            i = new Intent(this, FragmentListVisitas.class);
        } else if (id == R.id.upload) {
            //comprobar internet
            isUpload = true;
        } else if (id == R.id.download) {
            isDownload = true;
        }

        if (!(isUpload || isDownload)) {
            if (isFrame) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main, myFragment).commit();
            } else {
                startActivity(i);
            }
        } else {
            isConnectedToInternetToUpdate(isDownload ? "down" : "up");
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void isConnectedToInternetToUpdate(final String caso) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                //verificamos si tenemos internet
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    // Si hay conexión a Internet en este momento
                    //Toast.makeText(getBaseContext(),"Conectando...",Toast.LENGTH_SHORT).show();
                    switch (caso) {
                        case "up":

                            if (new VisitaDAO(getBaseContext()).listarNoEditable().size() > 0) {
                                startActivity(
                                        new Intent(getBaseContext(), ActivityUpload.class)
                                );
                                finish();
                            } else {
                                Toast.makeText(getBaseContext(), "No hay Inspecciones para sincronizar",
                                        Toast.LENGTH_LONG).show();
                            }


                            break;
                        case "down":
                            startActivity(
                                    new Intent(getBaseContext(), ActivityUpdate.class)
                            );
                            finish();
                            break;
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Comprueba tu conexión a internet.\n" +
                                    "Inténtalo nuevamente.",
                            Toast.LENGTH_SHORT).show();

                }

            }
        }, 500);


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
        ActivityMain.currentLatitude = currentLatitude;
    }

    public static double getCurrentLongitude() {
        return currentLongitude;
    }

    public static void setCurrentLongitude(double currentLongitude) {
        ActivityMain.currentLongitude = currentLongitude;
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
