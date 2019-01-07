package pe.ibao.agromovil.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import pe.ibao.agromovil.R;
import pe.ibao.agromovil.helpers.adapters.MyRecyclerViewAdapter;
import pe.ibao.agromovil.models.dao.FotoDAO;
import pe.ibao.agromovil.models.dao.MuestrasDAO;
import pe.ibao.agromovil.models.vo.entitiesInternal.FotoVO;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ActivityPhotoGallery extends AppCompatActivity {

    public static final String CARPETA_RAIZ = "misImagenes/";
    public static final String CARPETA_IMAGEN = "misFotos";
    public static final String DIRECTORIO_IMAGEN = CARPETA_RAIZ +CARPETA_IMAGEN;
    static private String PATH = "";
    static ImageView iViewLienzo;
    static File fileImagen;
    static Bitmap bitmap;
    static TextView nameCriterio;

    private static int idMuestra;
    private static String value;
    private static String fechaHora;
    private static List<FotoVO> listFotos;
    private static boolean isEditable;
    private static ListView listViewFotos;
    static RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    static int idFotoFocus;
    private static ImageView btnDelete;



    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);

        iViewLienzo = (ImageView) findViewById(R.id.iViewPhoto);

        Bundle mybundle = getIntent().getExtras();

        idMuestra = mybundle.getInt("idMuestra");
        value =  mybundle.getString("value");
        fechaHora =  mybundle.getString("fechaHora");
        isEditable = mybundle.getBoolean("isEditable");
        listFotos = new FotoDAO(this).listarByIdMuestra(idMuestra);
        nameCriterio = (TextView) findViewById(R.id.tViewNameCriterio);
        //listViewFotos = (ListView) findViewById(R.id.foto_listView);
       // Toast.makeText(this,mybundle.toString(),Toast.LENGTH_LONG).show();

        btnDelete = (ImageView) findViewById(R.id.iViewBtnDelete);
        nameCriterio.setText(new MuestrasDAO(getBaseContext()).consultById(idMuestra).getName());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Toast.makeText(getBaseContext(),"id foto :"+MyRecyclerViewAdapter.idFotoFocus,Toast.LENGTH_SHORT).show();
                boolean delete=false;
                try {
                    File file = new File(new FotoDAO(getBaseContext()).consultarById(MyRecyclerViewAdapter.idFotoFocus).getPath());
                    delete = file.delete();
                }catch (Exception e) {
                    Toast.makeText(getBaseContext(),e.toString(),Toast.LENGTH_LONG).show();
                }

                if(new FotoDAO(getBaseContext()).borrarById(MyRecyclerViewAdapter.idFotoFocus)){
                        if(!delete){
                            Log.d("fotoxdxd"," Error eliminando");
                        }else{
                            Log.d("fotoxdxd","eliminado");
                        }

                    listFotos = new FotoDAO(getBaseContext()).listarByIdMuestra(idMuestra);
                    adapter = new MyRecyclerViewAdapter(getBaseContext(),listFotos,iViewLienzo,isEditable,idFotoFocus,btnDelete);
//              adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener) this);
                    recyclerView.setAdapter(adapter);
                    iViewLienzo.setImageResource(R.drawable.ic_photo_ddd_100dp);
                    btnDelete.setVisibility(View.INVISIBLE);
                }else{
                    Toast.makeText(getBaseContext(),"Lo sentimos no se pudo Eliminar",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(!isEditable){
            ((FloatingActionButton) findViewById(R.id.floatingBtnCamara)).setVisibility(View.INVISIBLE);
            btnDelete.setVisibility(View.INVISIBLE);
        }


        if (validarPermisos()){

        } else{

        }

        //AdapterListPhoto adapterListFotos = new AdapterListPhoto(getBaseContext(), listFotos);
        //listViewFotos.setAdapter(adapterListFotos);//seteanis ek adaotadir

      //  setListViewHeightBasedOnChildren(listViewMuestas);//tamañap respecto a hijos




        recyclerView = findViewById(R.id.foto_listView);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(ActivityPhotoGallery.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        adapter = new MyRecyclerViewAdapter(this,listFotos,iViewLienzo,isEditable,idFotoFocus,btnDelete);
//        adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener) this);
        recyclerView.setAdapter(adapter);

    }

    private boolean validarPermisos(){
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }

        if((checkSelfPermission(CAMERA)== PackageManager.PERMISSION_GRANTED)
           &&
           (checkSelfPermission(WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)){
                return true;
        }

        if((shouldShowRequestPermissionRationale(CAMERA))
                ||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE,CAMERA},100);
        }
        return false;

    }

    private void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityPhotoGallery.this);
        dialog.setTitle("Permisos Desactivados");
        dialog.setMessage("Debe aceptar todos los permisos para poder tomar fotos");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                solicitarPermisosManual();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
        //    moveTaskToBack(true);

    }

    public void TomarFoto(View view){
        if(isEditable){
            fileImagen = new File(Environment.getExternalStorageDirectory(), DIRECTORIO_IMAGEN);
            Boolean isCreated  = fileImagen.exists();
            String nombre="";

            if(!isCreated){
                isCreated = fileImagen.mkdirs();
            }

            if(isCreated){
                nombre = System.currentTimeMillis()/100+".jpg";
            }

            PATH=Environment.getExternalStorageDirectory()+File.separator+ DIRECTORIO_IMAGEN +File.separator+nombre;

            File imagen = new File(PATH);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                String autorities = getApplicationContext().getPackageName()+".provider";
                Log.d("locomata",autorities);
                Uri imageUri= FileProvider.getUriForFile(ActivityPhotoGallery.this,autorities,imagen);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            }else {
                intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imagen));
            }
            startActivityForResult(intent,20);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            if (grantResults.length == 2
                    &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                    &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {

            } else {
                solicitarPermisosManual();

            }

        }
    }

    private void solicitarPermisosManual() {
        final CharSequence[] opciones = {
                "si",
                "no"
        };
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(ActivityPhotoGallery.this);
        alertOpciones.setTitle("¿Desea configurar los permisos de Forma Manual?");
        alertOpciones.setItems(
                opciones,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(opciones[i].equals("si")){
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",getPackageName(),null);
                            intent.setData(uri);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Los permisos no fueron aceptados",Toast.LENGTH_LONG).show();
                            dialog.dismiss();
                        }

                    }
                });
        alertOpciones.show();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 20:
                    MediaScannerConnection.scanFile(this,new String[]{PATH}, null ,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String s, Uri uri) {
                                    Log.d("Ruta","path : "+PATH);
                                }
                            });


                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Bitmap bitmap = BitmapFactory.decodeFile(PATH);

                            int rotate = 0;
                            try {
                                File imageFile = new File(PATH);
                                ExifInterface exif = new ExifInterface(
                                        imageFile.getAbsolutePath());
                                int orientation = exif.getAttributeInt(
                                        ExifInterface.TAG_ORIENTATION,
                                        ExifInterface.ORIENTATION_NORMAL);

                                switch (orientation) {
                                    case ExifInterface.ORIENTATION_ROTATE_270:
                                        rotate = 270;
                                        break;
                                    case ExifInterface.ORIENTATION_ROTATE_180:
                                        rotate = 180;
                                        break;
                                    case ExifInterface.ORIENTATION_ROTATE_90:
                                        rotate = 90;
                                        break;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Matrix matrix = new Matrix();
                            matrix.postRotate(rotate);
                            matrix.postScale(0.3f,0.3f);
                            //double scale = bitmap.getWidth()/(bitmap.getHeight()*1.0);
                            bitmap = Bitmap.createBitmap(bitmap , 0, 0, (bitmap.getWidth()),  (bitmap.getHeight()), matrix, true);

                            iViewLienzo.setImageBitmap(bitmap);
                        }
                    });


                   /* if (bitmap.getWidth() > bitmap.getHeight()) {
                        Matrix matrix = new Matrix();
                        matrix.postRotate(90);
                        bitmap = Bitmap.createBitmap(bitmap , 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    }
*/

       //             double scale = bitmap.getWidth()/(bitmap.getHeight()*1.0);
       //             bitmap = Bitmap.createScaledBitmap(bitmap,(int)(iViewLienzo.getHeight()*scale), iViewLienzo.getHeight(), true);



                 //   iViewLienzo.setScaleType(ImageView.ScaleType.MATRIX);
                    new FotoDAO(getBaseContext()).nuevoByIdMuestra(idMuestra,PATH);
                    listFotos = new FotoDAO(this).listarByIdMuestra(idMuestra);
                    /*

                    AdapterListPhoto adapterListFotos = new AdapterListPhoto(getBaseContext(), listFotos);

                    listViewFotos.setAdapter(adapterListFotos);//seteanis ek adaotadir
                    adapterListFotos.notifyDataSetChanged();
                    */

                    recyclerView = findViewById(R.id.foto_listView);
                    LinearLayoutManager horizontalLayoutManager
                            = new LinearLayoutManager(ActivityPhotoGallery.this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(horizontalLayoutManager);
                    adapter = new MyRecyclerViewAdapter(this,listFotos, iViewLienzo,isEditable,idFotoFocus,btnDelete);
//        adapter.setClickListener((MyRecyclerViewAdapter.ItemClickListener) this);
                    recyclerView.setAdapter(adapter);
                    break;
            }
        }
    }
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;

        int stretch_width = Math.round((float)width / (float)reqWidth);
        int stretch_height = Math.round((float)height / (float)reqHeight);

        if (stretch_width <= stretch_height)
            return stretch_height;
        else
            return stretch_width;
    }

}
