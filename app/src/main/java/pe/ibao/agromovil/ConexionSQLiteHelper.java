package pe.ibao.agromovil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import pe.ibao.agromovil.utilities.Utilities;

public class ConexionSQLiteHelper extends SQLiteOpenHelper{

    public static int VERSION_DB = 2;

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    String TAG = "CREATE_TABLE";
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,Utilities.CREATE_TABLE_USUARIO);
        db.execSQL(Utilities.CREATE_TABLE_USUARIO);
        db.execSQL(Utilities.CREATE_TABLE_ZONA);
        db.execSQL(Utilities.CREATE_TABLE_EMPRESA);
        db.execSQL(Utilities.CREATE_TABLE_FUNDO);
        db.execSQL(Utilities.CREATE_TABLE_CULTIVO);
        db.execSQL(Utilities.CREATE_TABLE_VARIEDAD);
        db.execSQL(Utilities.CREATE_TABLE_FUNDOVARIEDAD);
        db.execSQL(Utilities.CREATE_TABLE_CONFIGURACIONCRITERIO);
        db.execSQL(Utilities.CREATE_TABLE_CRITERIO);
        db.execSQL(Utilities.CREATE_TABLE_TIPOINSPECCION);
        db.execSQL(Utilities.CREATE_TABLE_VISITA);
        db.execSQL(Utilities.CREATE_TABLE_EVALUACION);
        db.execSQL(Utilities.CREATE_TABLE_MUESTRA);
        db.execSQL(Utilities.CREATE_TABLE_FOTO);
        db.execSQL(Utilities.CREATE_TABLE_CONTACTO);
        db.execSQL(Utilities.CREATE_TABLE_TIPORECOMENDACION);
        db.execSQL(Utilities.CREATE_TABLE_CRITERIORECOMENDACION);
        db.execSQL(Utilities.CREATE_TABLE_RECOMENDACION);
        db.execSQL(Utilities.CREATE_TABLE_CONFIGURACIONRECOMENDACION);

        db.execSQL(Utilities.CREATE_TABLE_COLAFOTOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,Utilities.CREATE_TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_EMPRESA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_ZONA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_FUNDO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CULTIVO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_VARIEDAD);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_FUNDOVARIEDAD);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CONFIGURACIONCRITERIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CRITERIO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_TIPOINSPECCION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_VISITA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_EVALUACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_MUESTRA);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CONTACTO);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_TIPORECOMENDACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CRITERIORECOMENDACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_RECOMENDACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_CONFIGURACIONRECOMENDACION);
        db.execSQL("DROP TABLE IF EXISTS "+Utilities.TABLE_COLAFOTOS);
        onCreate(db);
    }
}
