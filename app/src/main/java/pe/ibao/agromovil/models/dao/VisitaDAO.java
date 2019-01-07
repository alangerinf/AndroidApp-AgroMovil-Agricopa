package pe.ibao.agromovil.models.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import pe.ibao.agromovil.ConexionSQLiteHelper;
import pe.ibao.agromovil.models.vo.entitiesDB.ContactoVO;
import pe.ibao.agromovil.models.vo.entitiesDB.CultivoVO;
import pe.ibao.agromovil.models.vo.entitiesDB.EmpresaVO;
import pe.ibao.agromovil.models.vo.entitiesDB.FundoVO;
import pe.ibao.agromovil.models.vo.entitiesInternal.VisitaVO;
import pe.ibao.agromovil.utilities.Utilities;

import static pe.ibao.agromovil.ConexionSQLiteHelper.VERSION_DB;
import static pe.ibao.agromovil.utilities.Utilities.DATABASE_NAME;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_AREA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDFUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_FUNDO_COL_SISTEMARIEGO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_COMENTARIO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_CONTACTOPERSONALIZADO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_EDITING;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_FECHAHORAFIN;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_FECHAHORAINI;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_ID;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_IDCONTACTO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_IDFUNDO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_IDVARIEDAD;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_LATITUDFIN;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_LATITUDINI;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_LONGITUDFIN;
import static pe.ibao.agromovil.utilities.Utilities.TABLE_VISITA_COL_LONGITUDINI;

public class VisitaDAO {

    String TAG ="VisitaDAO";
    Context ctx;

    public VisitaDAO(Context ctx){
        this.ctx = ctx;
    }

    public List<VisitaVO> listarNoEditable(){
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        List<VisitaVO> visitaVOS = new ArrayList<>();
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "V."+TABLE_VISITA_COL_ID        +", " +//0
                            "V."+TABLE_VISITA_COL_FECHAHORAINI +", " +//1
                            "V."+TABLE_VISITA_COL_EDITING   +", " +//2
                            "V."+TABLE_VISITA_COL_LATITUDINI   +", " +//3
                            "V."+TABLE_VISITA_COL_LONGITUDINI  +", " +//4
                            "V."+TABLE_VISITA_COL_IDFUNDO   +", " +//5
                            "V."+TABLE_VISITA_COL_IDVARIEDAD+", " +//6
                            "V."+TABLE_VISITA_COL_IDCONTACTO+", "+//7
                            "V."+TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO+", "+//8
                            "V."+TABLE_VISITA_COL_CONTACTOPERSONALIZADO+", "+//9
                            "V."+TABLE_VISITA_COL_FECHAHORAFIN+", "+//10
                            "V."+TABLE_VISITA_COL_LATITUDFIN+", "+//11
                            "V."+TABLE_VISITA_COL_LONGITUDFIN+", "+//12+
                            "V."+TABLE_VISITA_COL_COMENTARIO+//13
                        " FROM "+
                            TABLE_VISITA+" as V "+
                        " WHERE "+
                            TABLE_VISITA_COL_EDITING+"="+"0"
                    ,null);
           // Toast.makeText(ctx,"contando visitas"+cursor.getCount(),Toast.LENGTH_LONG).show();
            while (cursor.moveToNext() && cursor.getCount()>0){
                VisitaVO temp;
                Log.d(TAG,"hay primero");
                temp = new VisitaVO();
                Log.d(TAG,"0");
                temp.setId(cursor.getInt(0));
                Log.d(TAG,"1");
                temp.setFechaHoraIni(cursor.getString(1));
                Log.d(TAG,"2");
                temp.setEditing(cursor.getInt(2)>0);
                Log.d(TAG,"3");
                temp.setLatIni(cursor.getString(3));
                Log.d(TAG,"4");
                temp.setLonIni(cursor.getString(4));
                Log.d(TAG,"5");
                temp.setIdFundo(cursor.getInt(5));
                Log.d(TAG,"6");
                temp.setIdVariedad(cursor.getInt(6));
                Log.d(TAG,"7");
                temp.setIdContacto(cursor.getInt(7));
                Log.d(TAG,"8");
                temp.setStatusContactoPersonalizado(cursor.getInt(8)>0);
                Log.d(TAG,"9");
                temp.setContactoPersonalizado(cursor.getString(9));
                Log.d(TAG,"10");
                temp.setFechaHoraFin(cursor.getString(10));
                Log.d(TAG,"11");
                temp.setLatFin(cursor.getString(11));
                Log.d(TAG,"12");
                temp.setLonFin(cursor.getString(12));
                Log.d(TAG,"13");
                temp.setComentario(cursor.getString(13));

                if(!temp.isStatusContactoPersonalizado()){
                    temp.setContactoPersonalizado(new ContactoDAO(ctx).consultarContactoByid(temp.getIdContacto()).getName());
                 //   Toast.makeText(ctx,temp.getContactoPersonalizado(),Toast.LENGTH_LONG).show();
                }

                if(temp.getIdContacto()>0){//verifica si devuelve un id fundo

                    //obteniedo datos d e fundo
                    ContactoDAO contactoDAO = new ContactoDAO(ctx);
                    ContactoVO contactoVo =  contactoDAO.consultarContactoByid(temp.getIdContacto());
                    String nameContacto = contactoVo.getName();
                    temp.setNameFundo(nameContacto);
                    //obteniedno datos d e empresa
                }

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);
                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByid(temp.getIdFundo());

                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                }
                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarCultivoByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                ConexionSQLiteHelper c2 = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                SQLiteDatabase db2 = c2.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery(
                        " SELECT " +
                                "FV."+TABLE_FUNDOVARIEDAD_COL_AREA+
                            " FROM "+
                                TABLE_FUNDOVARIEDAD+" AS FV"+
                            " WHERE "+
                                "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+temp.getIdVariedad()+
                                " AND "+
                                "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+temp.getIdFundo()
                        ,null
                        );
                if(cursor2.getCount()>0){
                    cursor2.moveToFirst();
                    temp.setAreaFundoVariedad(cursor2.getString(0)==null?"0":cursor2.getString(0));
                }
                cursor2.close();
                db2.close();

                ConexionSQLiteHelper c3 = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                SQLiteDatabase db3 = c3.getReadableDatabase();
                Cursor cursor3 = db3.rawQuery(
                        " SELECT " +
                                "F."+TABLE_FUNDO_COL_SISTEMARIEGO+
                                " FROM "+
                                TABLE_FUNDO+" AS F"+
                                " WHERE "+
                                "F."+TABLE_FUNDO_COL_ID+"="+temp.getIdFundo()
                        ,null
                );

                if(cursor3.getCount()>0){
                    cursor3.moveToFirst();
                    temp.setSistemaRiego(cursor3.getString(0)==null?"0":cursor3.getString(0));
                }
                cursor3.close();
                db3.close();

                visitaVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d("visitaError","visita Error "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();


        }

        return visitaVOS;
    }


    public List<VisitaVO> listAll() {
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getReadableDatabase();
        List<VisitaVO> visitaVOS =  new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "V."+TABLE_VISITA_COL_ID        +", " +//0
                            "V."+TABLE_VISITA_COL_FECHAHORAINI+", " +//1
                            "V."+TABLE_VISITA_COL_EDITING   +", " +//2
                            "V."+TABLE_VISITA_COL_LATITUDINI +", " +//3
                            "V."+TABLE_VISITA_COL_LONGITUDINI+", " +//4
                            "V."+TABLE_VISITA_COL_IDFUNDO   +", " +//5
                            "V."+TABLE_VISITA_COL_IDVARIEDAD+", " +//6
                            "V."+TABLE_VISITA_COL_IDCONTACTO+", "+//7
                            "V."+TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO+", "+//8
                            "V."+TABLE_VISITA_COL_CONTACTOPERSONALIZADO+", "+//9
                            "V."+TABLE_VISITA_COL_FECHAHORAFIN+", "+//10
                            "V."+TABLE_VISITA_COL_LATITUDFIN+", "+//11
                            "V."+TABLE_VISITA_COL_LONGITUDFIN+", "+//12+
                            "V."+TABLE_VISITA_COL_COMENTARIO+//13
                            " FROM "+
                            TABLE_VISITA+" as V "
                    ,null);
            while (cursor.moveToNext() && cursor.getCount()>0){
                VisitaVO temp;
                Log.d(TAG,"hay primero");
                temp = new VisitaVO();
                Log.d(TAG,"0");
                temp.setId(cursor.getInt(0));
                Log.d(TAG,"1");
                temp.setFechaHoraIni(cursor.getString(1));
                Log.d(TAG,"2");
                temp.setEditing(cursor.getInt(2)>0);
                Log.d(TAG,"3");
                temp.setLatIni(cursor.getString(3));
                Log.d(TAG,"4");
                temp.setLonIni(cursor.getString(4));
                Log.d(TAG,"5");
                temp.setIdFundo(cursor.getInt(5));
                Log.d(TAG,"6");
                temp.setIdVariedad(cursor.getInt(6));
                Log.d(TAG,"7");
                temp.setIdContacto(cursor.getInt(7));
                Log.d(TAG,"8");
                temp.setStatusContactoPersonalizado(cursor.getInt(8)>0);
                Log.d(TAG,"9");
                temp.setContactoPersonalizado(cursor.getString(9));
                Log.d(TAG,"10");
                temp.setFechaHoraFin(cursor.getString(10));
                Log.d(TAG,"11");
                temp.setLatFin(cursor.getString(11));
                Log.d(TAG,"12");
                temp.setLonFin(cursor.getString(12));
                Log.d(TAG,"13");
                temp.setComentario(cursor.getString(13));
                if(!temp.isStatusContactoPersonalizado()){
                    temp.setContactoPersonalizado(new ContactoDAO(ctx).consultarContactoByid(temp.getIdContacto()).getName());
                    //   Toast.makeText(ctx,temp.getContactoPersonalizado(),Toast.LENGTH_LONG).show();
                }

                if(temp.getIdContacto()>0){//verifica si devuelve un id fundo

                    //obteniedo datos d e fundo
                    ContactoDAO contactoDAO = new ContactoDAO(ctx);
                    ContactoVO contactoVo =  contactoDAO.consultarContactoByid(temp.getIdContacto());
                    String nameContacto = contactoVo.getName();
                    temp.setNameFundo(nameContacto);
                    //obteniedno datos d e empresa
                }

                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    String nameFundo = f.getName();
                    temp.setNameFundo(nameFundo);
                    //obteniedno datos d e empresa
                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByid(temp.getIdFundo());

                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                }
                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarCultivoByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                }
                ConexionSQLiteHelper c2 = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                SQLiteDatabase db2 = c2.getReadableDatabase();
                Cursor cursor2 = db2.rawQuery(
                        " SELECT " +
                                "FV."+TABLE_FUNDOVARIEDAD_COL_AREA+
                                " FROM "+
                                TABLE_FUNDOVARIEDAD+" AS FV"+
                                " WHERE "+
                                "FV."+TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+"="+temp.getIdVariedad()+
                                " AND "+
                                "FV."+TABLE_FUNDOVARIEDAD_COL_IDFUNDO+"="+temp.getIdFundo()
                        ,null
                );
                if(cursor2.getCount()>0){
                    cursor2.moveToFirst();
                    temp.setAreaFundoVariedad(cursor2.getString(0));
                }
                cursor2.close();
                db2.close();

                ConexionSQLiteHelper c3 = new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
                SQLiteDatabase db3 = c3.getReadableDatabase();
                Cursor cursor3 = db3.rawQuery(
                        " SELECT " +
                                "F."+TABLE_FUNDO_COL_SISTEMARIEGO+
                                " FROM "+
                                TABLE_FUNDO+" AS F"+
                                " WHERE "+
                                "F."+TABLE_FUNDO_COL_ID+"="+temp.getIdFundo()
                        ,null
                );

                if(cursor3.getCount()>0){
                    cursor3.moveToFirst();
                    temp.setSistemaRiego(cursor3.getString(0));
                }
                cursor3.close();
                db3.close();

                visitaVOS.add(temp);
            }
            cursor.close();
        }catch (Exception e){
            Log.d("visitaError","Error al listar Visitas "+e.toString());
            Toast.makeText(ctx,e.toString(),Toast.LENGTH_SHORT).show();
        }
        db.close();
        c.close();
        return visitaVOS;
    }


    public VisitaVO getEditing(){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();

        VisitaVO temp = null;
        try{
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "V."+TABLE_VISITA_COL_ID        +", " +//0
                            "V."+TABLE_VISITA_COL_FECHAHORAINI +", " +//1
                            "V."+TABLE_VISITA_COL_EDITING   +", " +//2
                            "V."+TABLE_VISITA_COL_LATITUDINI   +", " +//3
                            "V."+TABLE_VISITA_COL_LONGITUDINI  +", " +//4
                            "V."+TABLE_VISITA_COL_IDFUNDO   +", " +//5
                            "V."+TABLE_VISITA_COL_IDVARIEDAD+", " +//6
                            "V."+TABLE_VISITA_COL_IDCONTACTO+", "+//7
                            "V."+TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO+", "+//8
                            "V."+TABLE_VISITA_COL_CONTACTOPERSONALIZADO+", "+//9
                            "V."+TABLE_VISITA_COL_FECHAHORAFIN+", "+//10
                            "V."+TABLE_VISITA_COL_LATITUDFIN+", "+//11
                            "V."+TABLE_VISITA_COL_LONGITUDFIN+", "+//12+
                            "V."+TABLE_VISITA_COL_COMENTARIO+//13
                        " FROM "+
                            TABLE_VISITA+" as V "+

                        " WHERE "+
                            TABLE_VISITA_COL_EDITING+"="+"1"
                    ,null);

            if(cursor.getCount()>0){
                        cursor.moveToFirst() ;
                        Log.d(TAG,"hay primero");
                        temp = new VisitaVO();
                        Log.d(TAG,"0");
                        temp.setId(cursor.getInt(0));
                        Log.d(TAG,"1");
                        temp.setFechaHoraIni(cursor.getString(1));
                        Log.d(TAG,"2");
                        temp.setEditing(cursor.getInt(2)>0);
                        Log.d(TAG,"3");
                        temp.setLatIni(cursor.getString(3));
                        Log.d(TAG,"4");
                        temp.setLonIni(cursor.getString(4));
                        Log.d(TAG,"5");
                        temp.setIdFundo(cursor.getInt(5));
                        Log.d(TAG,"6");
                        temp.setIdVariedad(cursor.getInt(6));
                        Log.d(TAG,"7");
                        temp.setIdContacto(cursor.getInt(7));
                        Log.d(TAG,"8");
                        temp.setStatusContactoPersonalizado(cursor.getInt(8)>0);
                        Log.d(TAG,"9");
                        temp.setContactoPersonalizado(cursor.getString(9));
                        Log.d(TAG,"10");
                        temp.setFechaHoraFin(cursor.getString(10));
                        Log.d(TAG,"11");
                        temp.setLatFin(cursor.getString(11));
                        Log.d(TAG,"12");
                        temp.setLonFin(cursor.getString(12));
                Log.d(TAG,"13");
                temp.setComentario(cursor.getString(13));
                if(temp.getIdContacto()>0){//verifica si devuelve un id fundo

                            //obteniedo datos d e fundo
                            ContactoDAO contactoDAO = new ContactoDAO(ctx);
                            ContactoVO contactoVo =  contactoDAO.consultarContactoByid(temp.getIdContacto());
                            String nameContacto = contactoVo.getName();
                            temp.setNameFundo(nameContacto);
                            //obteniedno datos d e empresa
                        }

                        if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                            Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                            //obteniedo datos d e fundo
                            FundoDAO fundoDAO = new FundoDAO(ctx);
                            FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                            Log.d(TAG,"getEditing -2 "+temp.getIdFundo());
                            String nameFundo = f.getName();
                            Log.d(TAG,"getEditing -3 "+temp.getIdFundo());
                            temp.setNameFundo(nameFundo);
                            Log.d(TAG,"getEditing -4 "+temp.getIdFundo());
                           //obteniedno datos d e empresa

                            EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByid(temp.getIdFundo());

                            temp.setIdEmpresa(empresaVO.getId());
                            temp.setNameEmpresa(empresaVO.getName());

                        }
                        if(temp.getIdVariedad()>0){
                            //obteniendo datos  de cultivo
                            CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                            CultivoVO cultivoVO = cultivoDAO.consultarCultivoByIdVariedad(temp.getIdVariedad());
                            temp.setNameCultivo(cultivoVO.getName());
                            temp.setIdCultivo(cultivoVO.getId());
                            //obteiedno datos de  variedad
                            VariedadDAO variedadDAO = new VariedadDAO(ctx);
                            temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());
                        }
                    }
                cursor.close();

        }catch (Exception e){
            Log.d(TAG,"1getEditing "+e.toString());
            Toast.makeText(ctx,"1getEditing "+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return temp;
    }


    public boolean cambiarIdFundoIdVariedadIdContactoIsPersonalizadoContacto(int id,int idFundo, int idVariedad,int idContacto, boolean isPersonalizado, String Contacto){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_VISITA_COL_IDFUNDO,String.valueOf(idFundo));
        values.put(TABLE_VISITA_COL_IDVARIEDAD,String.valueOf(idVariedad));
        values.put(TABLE_VISITA_COL_IDCONTACTO,idContacto);
        values.put(TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO,isPersonalizado);
        values.put(TABLE_VISITA_COL_CONTACTOPERSONALIZADO,Contacto);
        int res = db.update(TABLE_VISITA,values,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return  flag;
    }
    public boolean setLatLonIniById(int id,String lat, String lon){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_VISITA_COL_LATITUDINI,lat);
        values.put(TABLE_VISITA_COL_LONGITUDINI,lon);
        int res = db.update(TABLE_VISITA,values,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return  flag;
    }
    public boolean setFechaHoraFinById(int id){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();

        /*String[] parametros =
                {
                        String.valueOf(id),
                };

        ContentValues values = new ContentValues();
        values.put(TABLE_VISITA_COL_LATITUDINI,lat);
        values.put(TABLE_VISITA_COL_LONGITUDINI,lon);
        */
        String sql = "UPDATE "+
                        TABLE_VISITA+
                     " SET "+
                        TABLE_VISITA_COL_FECHAHORAFIN+" = datetime('now','localtime')  "+
                     " WHERE " +
                        TABLE_VISITA_COL_ID+"="+String.valueOf(id);
        db.execSQL(sql);
        /*int res = db.update(TABLE_VISITA,values,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        */
        return  flag;
    }

    public boolean setComentario(int id,String coment){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();

        /*String[] parametros =
                {
                        String.valueOf(id),
                };

        ContentValues values = new ContentValues();
        values.put(TABLE_VISITA_COL_LATITUDINI,lat);
        values.put(TABLE_VISITA_COL_LONGITUDINI,lon);
        */
        String sql = "UPDATE "+
                TABLE_VISITA+
                " SET "+
                TABLE_VISITA_COL_COMENTARIO+" = \""+coment+"\""+
                " WHERE " +
                TABLE_VISITA_COL_ID+"="+String.valueOf(id);
        db.execSQL(sql);
        /*int res = db.update(TABLE_VISITA,values,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        */
        return  flag;
    }
    public boolean setLatLonFinById(int id,String lat, String lon){
        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_VISITA_COL_LATITUDFIN,lat);
        values.put(TABLE_VISITA_COL_LONGITUDFIN,lon);
        int res = db.update(TABLE_VISITA,values,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return  flag;
    }




    public VisitaVO intentarNuevo(){
        //obtener el q  se esta editando
        VisitaVO resVisita = getEditing();
        Long id = null;
        if(resVisita==null){//si retorno vacio osea q no hay editando creamos uno nuevo
            try {
                ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
                SQLiteDatabase db = c.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(Utilities.TABLE_VISITA_COL_EDITING, true);
                id = db.insert(Utilities.TABLE_VISITA, Utilities.TABLE_VISITA_COL_ID, values);
                c.close();
                resVisita = getEditing();
                Log.d(TAG,"--->"+resVisita.getFechaHoraIni());
               // Toast.makeText(ctx, "Nueva Visita Registrada:" + id, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e){
                Log.d(TAG,"Intentar nuevo"+e.toString());
            }
        } else{
           // Toast.makeText(ctx,"Abriendo Visita "/*+resVisita.getId()*/,Toast.LENGTH_SHORT).show();
        }
        return resVisita;
    }


    public boolean borrarById(int id){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };

        int res = db.delete(TABLE_VISITA,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
            new EvaluacionDAO(ctx).borrarByIdVisita(id);
        }
        db.close();
        conn.close();
        return flag;
    }

    public boolean clearTableUpload(){
        boolean flag = false;
        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = conn.getWritableDatabase();
        List<VisitaVO> listVisita = new VisitaDAO(ctx).listarNoEditable();
        for(int i=0;i<listVisita.size();i++){
            new EvaluacionDAO(ctx).clearTableUpload(listVisita.get(i).getId());
            borrarById(listVisita.get(i).getId());
            flag=true;
        }
        db.close();
        conn.close();
        return flag;
    }


    public  VisitaVO buscarById(Long id){
        ConexionSQLiteHelper c= new ConexionSQLiteHelper(ctx, DATABASE_NAME,null,VERSION_DB );
        SQLiteDatabase db = c.getReadableDatabase();
        VisitaVO temp = null;
        try{
/*
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "V."+TABLE_VISITA_COL_ID+", " +
                            "V."+TABLE_VISITA_COL_EDITING+", "+
                            "V."+TABLE_VISITA_COL_FECHAHORA+", "+
                            "V."+TABLE_VISITA_COL_LATITUD+", "+
                            "V."+TABLE_VISITA_COL_LONGITUD+", "+
                            "V."+TABLE_VISITA_COL_IDFUNDO+", "+
                            "V."+TABLE_VISITA_COL_IDVARIEDAD+
                        " FROM "+
                            TABLE_VISITA+" as V"+
                        " WHERE "+
                            "V."+TABLE_VISITA_COL_ID+"="+String.valueOf(id)
                    ,null);
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                temp = new VisitaVO();
                temp.setId(cursor.getInt(0));
                temp.setEditing(cursor.getInt(1) > 0);
                temp.setFechaHora(cursor.getString(2));
                temp.setLat(cursor.getDouble(3));
                temp.setLon(cursor.getDouble(4));
                temp.setIdFundo(cursor.getInt(5));
                temp.setIdVariedad(cursor.getInt(6));
                //Toast.makeText(ctx, "**"+temp.getFechaHora().toString(), Toast.LENGTH_SHORT).show();
            }
            cursor.close();
            c.close();*/
            Cursor cursor = db.rawQuery(
                    "SELECT " +
                            "V."+TABLE_VISITA_COL_ID        +", " +//0
                            "V."+TABLE_VISITA_COL_FECHAHORAINI+", " +//1
                            "V."+TABLE_VISITA_COL_EDITING   +", " +//2
                            "V."+TABLE_VISITA_COL_LATITUDINI   +", " +//3
                            "V."+TABLE_VISITA_COL_LONGITUDINI  +", " +//4
                            "V."+TABLE_VISITA_COL_IDFUNDO   +", " +//5
                            "V."+TABLE_VISITA_COL_IDVARIEDAD+", " +//6
                            "V."+TABLE_VISITA_COL_IDCONTACTO+", "+//7
                            "V."+TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO+", "+//8
                            "V."+TABLE_VISITA_COL_CONTACTOPERSONALIZADO+", "+//9
                            "V."+TABLE_VISITA_COL_FECHAHORAFIN+", "+//10
                            "V."+TABLE_VISITA_COL_LATITUDFIN+", "+//11
                            "V."+TABLE_VISITA_COL_LONGITUDFIN+", "+//12+
                            "V."+TABLE_VISITA_COL_COMENTARIO+//13
                            " FROM "+
                            TABLE_VISITA+" as V "+

                            " WHERE "+
                            "V."+TABLE_VISITA_COL_ID+"="+String.valueOf(id)
                    ,null);

            if(cursor.getCount()>0){
                cursor.moveToFirst() ;
                Log.d(TAG,"hay primero");
                temp = new VisitaVO();
                Log.d(TAG,"0");
                temp.setId(cursor.getInt(0));
                Log.d(TAG,"1");
                temp.setFechaHoraIni(cursor.getString(1));
                Log.d(TAG,"2");
                temp.setEditing(cursor.getInt(2)>0);
                Log.d(TAG,"3");
                temp.setLatIni(cursor.getString(3));
                Log.d(TAG,"4");
                temp.setLonIni(cursor.getString(4));
                Log.d(TAG,"5");
                temp.setIdFundo(cursor.getInt(5));
                Log.d(TAG,"6");
                temp.setIdVariedad(cursor.getInt(6));
                Log.d(TAG,"7");
                temp.setIdContacto(cursor.getInt(7));
                Log.d(TAG,"8");
                temp.setStatusContactoPersonalizado(cursor.getInt(8)>0);
                Log.d(TAG,"9");
                temp.setContactoPersonalizado(cursor.getString(9));
                Log.d(TAG,"10");
                temp.setFechaHoraFin(cursor.getString(10));
                Log.d(TAG,"11");
                temp.setLatFin(cursor.getString(11));
                Log.d(TAG,"12");
                temp.setLonFin(cursor.getString(12));
                Log.d(TAG,"13");
                temp.setComentario(cursor.getString(13));
                if(temp.getIdContacto()>0){//verifica si devuelve un id fundo

                    //obteniedo datos d e fundo
                    ContactoDAO contactoDAO = new ContactoDAO(ctx);
                    ContactoVO contactoVo =  contactoDAO.consultarContactoByid(temp.getIdContacto());
                    String nameContacto = contactoVo.getName();
                    temp.setNameFundo(nameContacto);
                    //obteniedno datos d e empresa
                }
                if(temp.getIdFundo()>0){//verifica si devuelve un id fundo
                    Log.d(TAG,"getEditing -1 "+temp.getIdFundo());
                    //obteniedo datos d e fundo
                    FundoDAO fundoDAO = new FundoDAO(ctx);
                    FundoVO f =  fundoDAO.consultarById(temp.getIdFundo());
                    Log.d(TAG,"getEditing -2 "+temp.getIdFundo());
                    String nameFundo = f.getName();
                    Log.d(TAG,"getEditing -3 "+temp.getIdFundo());
                    temp.setNameFundo(nameFundo);
                    Log.d(TAG,"getEditing -4 "+temp.getIdFundo());
                    //obteniedno datos d e empresa

                    EmpresaVO empresaVO = new EmpresaDAO(ctx).consultarEmpresaByid(temp.getIdFundo());

                    temp.setIdEmpresa(empresaVO.getId());
                    temp.setNameEmpresa(empresaVO.getName());

                }
                if(temp.getIdVariedad()>0){
                    //obteniendo datos  de cultivo
                    CultivoDAO cultivoDAO = new CultivoDAO(ctx);
                    CultivoVO cultivoVO = cultivoDAO.consultarCultivoByIdVariedad(temp.getIdVariedad());
                    temp.setNameCultivo(cultivoVO.getName());
                    temp.setIdCultivo(cultivoVO.getId());
                    //obteiedno datos de  variedad
                    VariedadDAO variedadDAO = new VariedadDAO(ctx);
                    temp.setNameVariedad(variedadDAO.consultarVariedadById(temp.getIdVariedad()).getName());

                }

            }
            cursor.close();
        }catch (Exception e){
            Log.e(TAG,"buscarbyid"+e.toString());
            Toast.makeText(ctx,"buscarbyId"+e.toString(),Toast.LENGTH_SHORT).show();
        }
        return temp;
    }


    public boolean save(int id) {

        boolean flag = false;
        ConexionSQLiteHelper c = new ConexionSQLiteHelper(ctx,DATABASE_NAME, null, VERSION_DB);
        SQLiteDatabase db = c.getWritableDatabase();
        String[] parametros =
                {
                        String.valueOf(id),
                };
        ContentValues values = new ContentValues();
        values.put(TABLE_VISITA_COL_EDITING,false);
        int res = db.update(TABLE_VISITA,values,TABLE_VISITA_COL_ID+"=?",parametros);
        if(res>0){
            flag=true;
        }
        c.close();
        return flag;

    }
}
