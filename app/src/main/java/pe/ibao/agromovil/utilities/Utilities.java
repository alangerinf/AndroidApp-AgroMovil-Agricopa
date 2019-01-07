package pe.ibao.agromovil.utilities;


public class Utilities {

    public static final String URL_ROOT="http://apps.ibao.pe/agromovil/incavo/requests/";
    public static final String URL_AUTENTIFICATION=URL_ROOT+"autenticar.php";
    public static final String URL_DOWNLOAD_TABLE_EMPRESA=URL_ROOT+"getEnterprises.php";
    public static final String URL_DOWNLOAD_TABLE_FUNDO=URL_ROOT+"getFundos.php";
    public static final String URL_DOWNLOAD_TABLE_CULTIVO=URL_ROOT+"getCultivos.php";
    public static final String URL_DOWNLOAD_TABLE_VARIEDAD=URL_ROOT+"getVariedades.php";
    public static final String URL_DOWNLOAD_TABLE_FUNDOVARIEDAD=URL_ROOT+"getFundoVariedades.php";
    public static final String URL_DOWNLOAD_TABLE_TIPOINSPECCION=URL_ROOT+"getTipoInspecciones.php";
    public static final String URL_DOWNLOAD_TABLE_CRITERIO=URL_ROOT+"getCriterioInspecciones.php";
    public static final String URL_DOWNLOAD_TABLE_CONFIGURACIONCRITERIO=URL_ROOT+"getConfiguracionCriterios.php";
    public static final String URL_DOWNLOAD_TABLE_CONTACTO= URL_ROOT+"getContactos.php";
    public static final String URL_DOWNLOAD_TABLE_CRITERIORECOMENDACION=URL_ROOT+"getCriterioRecomendaciones.php";
    public static final String URL_DOWNLOAD_TABLE_TIPORECOMENDACION=URL_ROOT+"getTipoRecomendaciones.php";
    public static final String URL_DOWNLOAD_TABLE_CONFIGURACIONRECOMENDACION=URL_ROOT+"getConfiguracionRecomendaciones.php";
    public static final String URL_DOWNLOAD_TABLE_ZONA=URL_ROOT+"getZonas.php";

    public static final String URL_UPLOAD_MASTER=URL_ROOT+"insertDataFromMovil.php";
    public static final String URL_UPLOAD_FOTOS=URL_ROOT+"insertFotos.php";

    public static final String DATABASE_NAME="data";


    public static final String TABLE_ZONA="zona",
            TABLE_ZONA_COL_ID       ="id",
            TABLE_ZONA_TYPECOL_ID   ="INTEGER",
            TABLE_ZONA_COL_NAME     ="name",
            TABLE_ZONA_TYPECOL_NAME ="VARCHAR(50)";

    public static final String TABLE_COLAFOTOS="colaFotos",
            TABLE_COLAFOTOS_COL_IDDB         ="idBD",
            TABLE_COLAFOTOS_TYPECOL_IDDB     ="INTEGER",
            TABLE_COLAFOTOS_COL_PATH         ="path",
            TABLE_COLAFOTOS_TYPECOL_PATH     ="VARCHAR(100)";

    public static final String TABLE_CONTACTO="contacto",
            TABLE_CONTACTO_COL_ID           ="id",
            TABLE_CONTACTO_TYPECOL_ID       ="INTEGER",
            TABLE_CONTACTO_COL_NAME         ="name",
            TABLE_CONTACTO_TYPECOL_NAME     ="VARCHAR(50)",
            TABLE_CONTACTO_COL_IDFUNDO      ="idFundo",
            TABLE_CONTACTO_TYPECOL_IDFUNDO  ="INTEGER";

    public static final String TABLE_USUARIO="usuario",
            TABLE_USUARIO_COL_ID                ="id",
            TABLE_USUARIO_TYPECOL_ID            ="INTEGER",
            TABLE_USUARIO_COL_USER              ="user",
            TABLE_USUARIO_TYPECOL_USER          ="varchar(50)",
            TABLE_USUARIO_COL_PASSWORD          ="password",
            TABLE_USUARIO_TYPECOL_PASSWORD      ="varchar(50)",
            TABLE_USUARIO_COL_NAME              ="name",
            TABLE_USUARIO_TYPECOL_NAME          ="varchar(50)",
            TABLE_USUARIO_COL_LASTNAME          ="lastname",
            TABLE_USUARIO_TYPECOL_LASTNAME      ="varchar(50)",
            TABLE_USUARIO_COL_CODIGO            ="codigo",
            TABLE_USUARIO_TYPECOL_CODIGO        ="INTEGER";

    public static final String TABLE_EMPRESA="empresa",
            TABLE_EMPRESA_COL_ID                ="id",
            TABLE_EMPRESA_TYPECOL_ID            ="INTEGER",
            TABLE_EMPRESA_COL_NAME              ="name",
            TABLE_EMPRESA_TYPECOL_NAME          ="varchar(50)",
            TABLE_EMPRESA_COL_IDZONA ="idZona",
            TABLE_EMPRESA_TYPECOL_IDZONA        ="INTEGER";

    public static final String TABLE_FUNDO  ="fundo",
            TABLE_FUNDO_COL_ID                  ="id",
            TABLE_FUNDO_TYPECOL_ID              ="INTEGER",
            TABLE_FUNDO_COL_NAME                ="name",
            TABLE_FUNDO_TYPECOL_NAME            ="varchar(50)",
            TABLE_FUNDO_COL_IDEMPRESA           ="idEmpresa",
            TABLE_FUNDO_TYPECOL_IDEMPRESA       ="INTEGER",
            TABLE_FUNDO_COL_SISTEMARIEGO           ="sistemaRiego",
            TABLE_FUNDO_TYPECOL_SISTEMARIEGO       ="INTEGER";

    public static final String TABLE_CULTIVO="cultivo",
            TABLE_CULTIVO_COL_ID                ="id",
            TABLE_CULTIVO_TYPECOL_ID            ="INTEGER",
            TABLE_CULTIVO_COL_NAME              ="name",
            TABLE_CULTIVO_TYPECOL_NAME          ="varchar(50)";

    public static final String TABLE_VARIEDAD="variedad",
            TABLE_VARIEDAD_COL_ID               ="id",
            TABLE_VARIEDAD_TYPECOL_ID           ="id",
            TABLE_VARIEDAD_COL_NAME             ="name",
            TABLE_VARIEDAD_TYPECOL_NAME         ="name",
            TABLE_VARIEDAD_COL_IDCULTIVO        ="idCultivo",
            TABLE_VARIEDAD_TYPECOL_IDCULTIVO    ="idCultivo";


    public static final String TABLE_FUNDOVARIEDAD="fundoVariedad",
            TABLE_FUNDOVARIEDAD_COL_ID="id",
            TABLE_FUNDOVARIEDAD_TYPECOL_ID="INTEGER",
            TABLE_FUNDOVARIEDAD_COL_IDFUNDO="idFundo",
            TABLE_FUNDOVARIEDAD_TYPECOL_IDFUNDO="INTEGER",
            TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD="idVariedad",
            TABLE_FUNDOVARIEDAD_TYPECOL_IDVARIEDAD="INTEGER",
            TABLE_FUNDOVARIEDAD_COL_AREA="areaProduccion",
            TABLE_FUNDOVARIEDAD_TYPECOL_AREA="VARCHAR(20)";

    public static final String TABLE_CONFIGURACIONCRITERIO="configuracionCriterio",
            TABLE_CONFIGURACIONCRITERIO_COL_ID                  ="id",
            TABLE_CONFIGURACIONCRITERIO_TYPECOL_ID              ="INTEGER",
            TABLE_CONFIGURACIONCRITERIO_COL_IDFUNDOVARIEDAD     ="idFundoVariedad",
            TABLE_CONFIGURACIONCRITERIO_TYPECOL_IDFUNDOVARIEDAD ="INTEGER",
            TABLE_CONFIGURACIONCRITERIO_COL_IDCRITERIO          ="idCriterio",
            TABLE_CONFIGURACIONCRITERIO_TYPECOL_IDCRITERIO      ="INTEGER";

    public static final String TABLE_CRITERIO       ="criterio",
            TABLE_CRITERIO_COL_ID                   ="id",//
            TABLE_CRITERIO_TYPECOL_ID               ="INTEGER",//
            TABLE_CRITERIO_COL_NAME                 ="name",//
            TABLE_CRITERIO_TYPECOL_NAME             ="varchar(50)",//
            TABLE_CRITERIO_COL_TIPO                 ="tipo",//
            TABLE_CRITERIO_TYPECOL_TIPO             ="varchar(50)", //"int","string","list",float,boolean
            TABLE_CRITERIO_COL_MAGNITUD             ="magnitud",
            TABLE_CRITERIO_TYPECOL_MAGNITUD         ="varchar(50)",//"°C" , "mm." , "ml/s²" , "bajo-medio-alto"
            TABLE_CRITERIO_COL_IDTIPOINSPECCION     ="idTipoInspeccion",
            TABLE_CRITERIO_TYPECOL_IDTIPOINSPECCION ="INTEGER";

    public static final String TABLE_TIPOINSPECCION="tipoInspeccion",
            TABLE_TIPOINSPECCION_COL_ID             ="id",
            TABLE_TIPOINSPECCION_TYPECOL_ID         ="INTEGER",
            TABLE_TIPOINSPECCION_COL_NAME           ="name",
            TABLE_TIPOINSPECCION_TYPECOL_NAME       ="varchar(50)";


    public static final String TABLE_VISITA="visita",
            TABLE_VISITA_COL_ID             ="id",
            TABLE_VISITA_TYPECOL_ID         ="INTEGER",
            TABLE_VISITA_COL_FECHAHORAINI   ="fechaHoraInicio",
            TABLE_VISITA_TYPECOL_FECHAHORAINI="date",
            TABLE_VISITA_COL_FECHAHORAFIN    ="fechaHoraFin",
            TABLE_VISITA_TYPECOL_FECHAHORAFIN="date",
            TABLE_VISITA_COL_EDITING        ="editing",
            TABLE_VISITA_TYPECOL_EDITING    ="BOOLEAN",
            TABLE_VISITA_COL_LATITUDINI        ="latitudIni",
            TABLE_VISITA_TYPECOL_LATITUDINI    ="varchar(50)",
            TABLE_VISITA_COL_LONGITUDINI       ="longitudIni",
            TABLE_VISITA_TYPECOL_LONGITUDINI   ="varchar(50)",
            TABLE_VISITA_COL_LATITUDFIN        ="latitudFin",
            TABLE_VISITA_TYPECOL_LATITUDFIN    ="varchar(50)",
            TABLE_VISITA_COL_LONGITUDFIN       ="longitudFin",
            TABLE_VISITA_TYPECOL_LONGITUDFIN   ="varchar(50)",
            TABLE_VISITA_COL_IDFUNDO        ="idFundo",
            TABLE_VISITA_TYPECOL_IDFUNDO    ="INTEGER",
            TABLE_VISITA_COL_IDVARIEDAD     ="idVariedad",
            TABLE_VISITA_TYPECOL_IDVARIEDAD ="INTEGER",
            TABLE_VISITA_COL_CONTACTOPERSONALIZADO  ="contactoPersonalizado",
            TABLE_VISITA_TYPECOL_CONTACTOPERSONALIZADO  ="VARCHAR(50)",
            TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO = "isContactoPersonalizado",
            TABLE_VISITA_TYPECOL_ISCONTACTOPERSONALIZADO = "BOOLEAN",
            TABLE_VISITA_COL_IDCONTACTO       ="idContacto",
            TABLE_VISITA_TYPECOL_IDCONTACTO   ="INTEGER",
            TABLE_VISITA_COL_COMENTARIO       ="coment",
            TABLE_VISITA_TYPECOL_COMENTARIO   ="VARCHAR(100)";

    public static final String TABLE_EVALUACION="evaluacion",
            TABLE_EVALUACION_COL_ID                     ="id",
            TABLE_EVALUACION_TYPECOL_ID                 ="INTEGER",
            TABLE_EVALUACION_COL_TIMEINI                ="timeIni",
            TABLE_EVALUACION_TYPECOL_TIMEINI            ="DATE",
            TABLE_EVALUACION_COL_TIMEFIN                ="timeFin",
            TABLE_EVALUACION_TYPECOL_TIMEFIN            ="DATE",
            TABLE_EVALUACION_COL_QR                     ="qr",
            TABLE_EVALUACION_TYPECOL_QR                 ="VARCHAR(80)",
            TABLE_EVALUACION_COL_LATITUD                ="latitud",
            TABLE_EVALUACION_TYPECOL_LATITUD            ="DOUBLE",
            TABLE_EVALUACION_COL_LONGITUD               ="longitud",
            TABLE_EVALUACION_TYPECOL_LONGITUD           ="DOUBLE",
            TABLE_EVALUACION_COL_IDTIPOINSPECCION       ="idTipoInspeccion",
            TABLE_EVALUACION_TYPECOL_IDTIPOINSPECCION   ="INTEGER",
            TABLE_EVALUACION_COL_IDVISITA               ="idVisita",
            TABLE_EVALUACION_TYPECOL_IDVISITA           ="INTEGER";

    public static final String TABLE_MUESTRA="muestra",
            TABLE_MUESTRA_COL_ID = "id",
            TABLE_MUESTRA_TYPECOL_ID = "INTEGER",
            TABLE_MUESTRA_COL_COMENTARIO = "coment",
            TABLE_MUESTRA_TYPECOL_COMENTARIO = "VARCHAR(50)",
            TABLE_MUESTRA_COL_TIME = "time",
            TABLE_MUESTRA_TYPECOL_TIME= "varchar(50)",
            TABLE_MUESTRA_COL_VALUE = "value",
            TABLE_MUESTRA_TYPECOL_VALUE="VACHAR(80)",
            TABLE_MUESTRA_COL_IDEVALUACION="idEvaluacion",
            TABLE_MUESTRA_TYPECOL_IDEVALUACION="INTEGER",
            TABLE_MUESTRA_COL_IDCRITERIO="idCriterio",
            TABLE_MUESTRA_TYPECOL_IDCRITERIO="INTEGER",
            TABLE_MUESTRA_COL_IDTIPOINSPECCION="idTipoInspeccion",
            TABLE_MUESTRA_TYPECOL_IDTIPOINSPECCION="INTEGER";


    public static final String TABLE_FOTO="foto",
            TABLE_FOTO_COL_ID = "id",
            TABLE_FOTO_TYPECOL_ID ="INTEGER",
            TABLE_FOTO_COL_HORAFECHA ="fechaHora",
            TABLE_FOTO_TYPECOL_HORAFECHA ="DATE",
            TABLE_FOTO_COL_PATH = "path",
            TABLE_FOTO_TYPECOL_PATH = "VARCHAR(100)",
            TABLE_FOTO_COL_IDMUESTRA="idMuestra",
            TABLE_FOTO_TYPECOL_IDMUESTRA="INTEGER";

    public static final String TABLE_TIPORECOMENDACION="tipoRecomendacion",
            TABLE_TIPORECOMENDACION_COL_ID = "id",
            TABLE_TIPORECOMENDACION_TYPECOL_ID = "INTEGER",
            TABLE_TIPORECOMENDACION_COL_NAME ="name",
            TABLE_TIPORECOMENDACION_TYPECOL_NAME ="VARCHAR(50)";

    public static final String TABLE_CRITERIORECOMENDACION="criterioRecomendacion",
            TABLE_CRITERIORECOMENDACION_COL_ID = "id",
            TABLE_CRITERIORECOMENDACION_TYPECOL_ID ="INTEGER",
            TABLE_CRITERIORECOMENDACION_COL_NAME = "name",
            TABLE_CRITERIORECOMENDACION_TYPECOL_NAME ="VARCHAR(50)",
            TABLE_CRITERIORECOMENDACION_COL_LISTUNIDADES = "listUnidades",
            TABLE_CRITERIORECOMENDACION_TYPECOL_LISTUNIDADES ="VARCHAR(80)",
            TABLE_CRITERIORECOMENDACION_COL_LISTFRECUENCIAS = "listFrecuencias",
            TABLE_CRITERIORECOMENDACION_TYPECOL_LISTFRECUENCIAS ="VARCHAR(80)",
            TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION = "idTipoRecomendacion",
            TABLE_CRITERIORECOMENDACION_TYPECOL_IDTIPORECOMENDACION ="INTEGER";

    public static final String TABLE_CONFIGURACIONRECOMENDACION="configuracionRecomendacion",
            TABLE_CONFIGURACIONRECOMENDACION_COL_ID = "id",
            TABLE_CONFIGURACIONRECOMENDACION_TYPECOL_ID ="INTEGER",
            TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD = "idFundoVariedad",
            TABLE_CONFIGURACIONRECOMENDACION_TYPECOL_IDFUNDOVARIEDAD ="INTEGER",
            TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION = "idCriterioRecomendacion",
            TABLE_CONFIGURACIONRECOMENDACION_TYPECOL_IDCRITERIORECOMENDACION ="INTEGER";

    public static final String TABLE_RECOMENDACION="recomendacion",
            TABLE_RECOMENDACION_COL_ID = "id",
            TABLE_RECOMENDACION_TYPECOL_ID = "INTEGER",
            TABLE_RECOMENDACION_COL_FRECUENCIA = "FRECUENCIA",
            TABLE_RECOMENDACION_TYPECOL_FRECUENCIA = "INTEGER",
            TABLE_RECOMENDACION_COL_UNIDAD  = "unidad",
            TABLE_RECOMENDACION_TYPECOL_UNIDAD = "INTEGER",
            TABLE_RECOMENDACION_COL_CANTIDAD = "cantidad",
            TABLE_RECOMENDACION_TYPECOL_CANTIDAD= "VARCHAR(20)",
            TABLE_RECOMENDACION_COL_COMENTARIO = "comentario",
            TABLE_RECOMENDACION_TYPECOL_COMENTARIO = "VARCHAR(50)",
            TABLE_RECOMENDACION_COL_IDVISITA = "idVisita",
            TABLE_RECOMENDACION_TYPECOL_IDVISITA = "INTEGER",
            TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION = "idCriterio",
            TABLE_RECOMENDACION_TYPECOL_IDCRITERIORECOMENDACION = "INTEGER";


    //SCRIPTS SQL CREATE TABLES

    public static final String CREATE_TABLE_ZONA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_ZONA+" ("+
                TABLE_ZONA_COL_ID   +" "+TABLE_ZONA_TYPECOL_ID+" PRIMARY KEY ,"+
                TABLE_ZONA_COL_NAME +" "+TABLE_ZONA_TYPECOL_NAME+" "+
            ")";

    public static final String CREATE_TABLE_FOTO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_FOTO+" ("+
                    TABLE_FOTO_COL_ID       +" "+TABLE_FOTO_TYPECOL_ID       +" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_FOTO_COL_PATH +" "+ TABLE_FOTO_TYPECOL_PATH +", "+
                    TABLE_FOTO_COL_HORAFECHA+" "+TABLE_FOTO_TYPECOL_HORAFECHA+" DEFAULT (datetime('now','localtime')), "+
                    TABLE_FOTO_COL_IDMUESTRA+" "+TABLE_FOTO_TYPECOL_IDMUESTRA+" NOT NULL"+
            ")";

    public static final String CREATE_TABLE_MUESTRA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_MUESTRA+" ("+
                    TABLE_MUESTRA_COL_ID              +" "+TABLE_MUESTRA_TYPECOL_ID           +" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_MUESTRA_COL_TIME            +" "+TABLE_MUESTRA_TYPECOL_TIME         +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_MUESTRA_COL_COMENTARIO      +" "+TABLE_MUESTRA_TYPECOL_COMENTARIO   +" DEFAULT '', "+
                    TABLE_MUESTRA_COL_VALUE           +" "+TABLE_MUESTRA_TYPECOL_VALUE        +" DEFAULT '', "+
                    TABLE_MUESTRA_COL_IDCRITERIO      +" "+TABLE_MUESTRA_TYPECOL_IDCRITERIO   +" NOT NULL, "+
                    TABLE_MUESTRA_COL_IDEVALUACION    +" "+TABLE_MUESTRA_TYPECOL_IDEVALUACION +" NOT NULL,"+
                    TABLE_MUESTRA_COL_IDTIPOINSPECCION+" "+TABLE_MUESTRA_TYPECOL_IDTIPOINSPECCION+" NOT NULL"+
            ")";

    public static final String CREATE_TABLE_EVALUACION=
            " CREATE TABLE IF NOT EXISTS "+TABLE_EVALUACION+" ("+
                    TABLE_EVALUACION_COL_ID                 +" "+TABLE_EVALUACION_TYPECOL_ID            +" PRIMARY KEY AUTOINCREMENT, "+
                    TABLE_EVALUACION_COL_TIMEINI            +" "+TABLE_EVALUACION_TYPECOL_TIMEINI       +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_EVALUACION_COL_TIMEFIN            +" "+TABLE_EVALUACION_TYPECOL_TIMEFIN       +", "+
                    TABLE_EVALUACION_COL_QR                 +" "+TABLE_EVALUACION_TYPECOL_QR            +", "+
                    TABLE_EVALUACION_COL_LATITUD            +" "+TABLE_EVALUACION_TYPECOL_LATITUD       +", "+
                    TABLE_EVALUACION_COL_LONGITUD           +" "+TABLE_EVALUACION_TYPECOL_LONGITUD      +", "+
                    TABLE_EVALUACION_COL_IDTIPOINSPECCION   +" "+TABLE_EVALUACION_TYPECOL_IDTIPOINSPECCION+", "+
                    TABLE_EVALUACION_COL_IDVISITA           +" "+TABLE_EVALUACION_TYPECOL_IDVISITA+" NOT NULL"+
            ")";


    public static final String CREATE_TABLE_VISITA=
            " CREATE TABLE IF NOT EXISTS "+TABLE_VISITA+" (" +
                    TABLE_VISITA_COL_ID            +" "+TABLE_VISITA_TYPECOL_ID        +" PRIMARY KEY AUTOINCREMENT, " +
                    TABLE_VISITA_COL_FECHAHORAINI  +" "+TABLE_VISITA_TYPECOL_FECHAHORAINI +" DEFAULT (datetime('now','localtime')), "+
                    TABLE_VISITA_COL_FECHAHORAFIN  +" "+TABLE_VISITA_TYPECOL_FECHAHORAFIN + ", "+
                    TABLE_VISITA_COL_EDITING       +" "+TABLE_VISITA_TYPECOL_EDITING   +", "+
                    TABLE_VISITA_COL_LATITUDINI    +" "+TABLE_VISITA_TYPECOL_LATITUDINI   +", "+
                    TABLE_VISITA_COL_LONGITUDINI   +" "+TABLE_VISITA_TYPECOL_LONGITUDINI  +", "+
                    TABLE_VISITA_COL_LATITUDFIN    +" "+TABLE_VISITA_TYPECOL_LATITUDFIN   +", "+
                    TABLE_VISITA_COL_LONGITUDFIN   +" "+TABLE_VISITA_TYPECOL_LONGITUDFIN  +", "+
                    TABLE_VISITA_COL_IDFUNDO       +" "+TABLE_VISITA_TYPECOL_IDFUNDO   +", "+
                    TABLE_VISITA_COL_IDVARIEDAD    +" "+TABLE_VISITA_TYPECOL_IDVARIEDAD+", "+
                    TABLE_VISITA_COL_ISCONTACTOPERSONALIZADO+" "+TABLE_VISITA_TYPECOL_ISCONTACTOPERSONALIZADO+" DEFAULT 0, "+
                    TABLE_VISITA_COL_CONTACTOPERSONALIZADO  +" "+TABLE_VISITA_TYPECOL_CONTACTOPERSONALIZADO+", "+
                    TABLE_VISITA_COL_IDCONTACTO    +" "+TABLE_VISITA_TYPECOL_IDCONTACTO+","+
                    TABLE_VISITA_COL_COMENTARIO    +" "+TABLE_VISITA_TYPECOL_COMENTARIO+
            ")";

    /*** agregar
     * area - float     <-----------table Fundovariedad
     * SistemaRiego - float <-----------table fundo
     */

    public static final String CREATE_TABLE_TIPOINSPECCION=
            " CREATE TABLE IF NOT EXISTS "+TABLE_TIPOINSPECCION+" (" +
                    TABLE_TIPOINSPECCION_COL_ID     +" "+TABLE_TIPOINSPECCION_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_TIPOINSPECCION_COL_NAME   +" "+TABLE_TIPOINSPECCION_TYPECOL_NAME+
            ")";

    public static final String CREATE_TABLE_CRITERIO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CRITERIO+" (" +
                    TABLE_CRITERIO_COL_ID               +" "+TABLE_CRITERIO_TYPECOL_ID+" PRIMARY KEY,"+
                    TABLE_CRITERIO_COL_NAME             +" "+TABLE_CRITERIO_TYPECOL_NAME+","+
                    TABLE_CRITERIO_COL_TIPO             +" "+TABLE_CRITERIO_TYPECOL_TIPO+","+
                    TABLE_CRITERIO_COL_MAGNITUD         +" "+TABLE_CRITERIO_TYPECOL_MAGNITUD+","+
                    TABLE_CRITERIO_COL_IDTIPOINSPECCION +" "+TABLE_CRITERIO_TYPECOL_IDTIPOINSPECCION+
            ")";

    public static final String CREATE_TABLE_CONFIGURACIONCRITERIO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CONFIGURACIONCRITERIO+" (" +
                    TABLE_CONFIGURACIONCRITERIO_COL_ID                  +" "+TABLE_CONFIGURACIONCRITERIO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_CONFIGURACIONCRITERIO_COL_IDFUNDOVARIEDAD     +" "+TABLE_CONFIGURACIONCRITERIO_TYPECOL_IDFUNDOVARIEDAD+","+
                    TABLE_CONFIGURACIONCRITERIO_COL_IDCRITERIO          +" "+TABLE_CONFIGURACIONCRITERIO_TYPECOL_IDCRITERIO+
            ")";

    public static final String CREATE_TABLE_FUNDOVARIEDAD =
            " CREATE TABLE IF NOT EXISTS "+TABLE_FUNDOVARIEDAD+" (" +
                    TABLE_FUNDOVARIEDAD_COL_ID        +" "+TABLE_FUNDOVARIEDAD_TYPECOL_ID+" PRIMARY KEY, " +
                    TABLE_FUNDOVARIEDAD_COL_IDFUNDO   +" "+TABLE_FUNDOVARIEDAD_TYPECOL_IDFUNDO+", "+
                    TABLE_FUNDOVARIEDAD_COL_AREA      +" "+TABLE_FUNDOVARIEDAD_TYPECOL_AREA+", "+
                    TABLE_FUNDOVARIEDAD_COL_IDVARIEDAD+" "+TABLE_FUNDOVARIEDAD_TYPECOL_IDVARIEDAD+
            ")";

    public static final String CREATE_TABLE_VARIEDAD =
            " CREATE TABLE IF NOT EXISTS "+TABLE_VARIEDAD+" (" +
                    TABLE_VARIEDAD_COL_ID        +" "+TABLE_VARIEDAD_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_VARIEDAD_COL_NAME      +" "+TABLE_VARIEDAD_TYPECOL_NAME+","+
                    TABLE_VARIEDAD_COL_IDCULTIVO +" "+TABLE_VARIEDAD_TYPECOL_IDCULTIVO+
            ")";

    public static final String CREATE_TABLE_CULTIVO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CULTIVO+" (" +
                    TABLE_CULTIVO_COL_ID        +" "+TABLE_CULTIVO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_CULTIVO_COL_NAME      +" "+TABLE_CULTIVO_TYPECOL_NAME+
            ")";

    public static final String CREATE_TABLE_FUNDO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_FUNDO+" (" +
                    TABLE_FUNDO_COL_ID        +" "+TABLE_FUNDO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_FUNDO_COL_NAME      +" "+TABLE_FUNDO_TYPECOL_NAME+","+
                    TABLE_FUNDO_COL_SISTEMARIEGO+" "+TABLE_FUNDO_TYPECOL_SISTEMARIEGO+", "+
                    TABLE_FUNDO_COL_IDEMPRESA +" "+TABLE_FUNDO_TYPECOL_IDEMPRESA+
            ")";

    public static final String CREATE_TABLE_EMPRESA =
            " CREATE TABLE IF NOT EXISTS "+TABLE_EMPRESA+" (" +
                    TABLE_EMPRESA_COL_ID        +" "+TABLE_EMPRESA_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_EMPRESA_COL_NAME      +" "+TABLE_EMPRESA_TYPECOL_NAME+", "+
                    TABLE_EMPRESA_COL_IDZONA +" "+TABLE_EMPRESA_TYPECOL_IDZONA+
            ")";

    public static final String CREATE_TABLE_USUARIO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_USUARIO+" (" +
                    TABLE_USUARIO_COL_ID        +" "+TABLE_USUARIO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_USUARIO_COL_USER      +" "+TABLE_USUARIO_TYPECOL_USER+"," +
                    TABLE_USUARIO_COL_PASSWORD  +" "+TABLE_USUARIO_TYPECOL_PASSWORD+"," +
                    TABLE_USUARIO_COL_NAME      +" "+TABLE_USUARIO_TYPECOL_NAME+","+
                    TABLE_USUARIO_COL_LASTNAME  +" "+TABLE_USUARIO_TYPECOL_LASTNAME+", "+
                    TABLE_USUARIO_COL_CODIGO    +" "+TABLE_USUARIO_TYPECOL_CODIGO+
            ")";

    public static final String CREATE_TABLE_CONTACTO =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CONTACTO+" (" +
                    TABLE_CONTACTO_COL_ID        +" "+TABLE_CONTACTO_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_CONTACTO_COL_NAME      +" "+TABLE_CONTACTO_TYPECOL_NAME+"," +
                    TABLE_CONTACTO_COL_IDFUNDO   +" "+TABLE_CONTACTO_TYPECOL_IDFUNDO +
                    ")";
    public static final String CREATE_TABLE_TIPORECOMENDACION =
            " CREATE TABLE IF NOT EXISTS "+TABLE_TIPORECOMENDACION+" (" +
                    TABLE_TIPORECOMENDACION_COL_ID        +" "+TABLE_TIPORECOMENDACION_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_TIPORECOMENDACION_COL_NAME      +" "+TABLE_TIPORECOMENDACION_TYPECOL_NAME+
                    ")";
    public static final String CREATE_TABLE_CRITERIORECOMENDACION =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CRITERIORECOMENDACION+" (" +
                    TABLE_CRITERIORECOMENDACION_COL_ID                  +" "+TABLE_CRITERIORECOMENDACION_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_CRITERIORECOMENDACION_COL_NAME                +" "+TABLE_CRITERIORECOMENDACION_TYPECOL_NAME+" , "+
                    TABLE_CRITERIORECOMENDACION_COL_LISTUNIDADES        +" "+TABLE_CRITERIORECOMENDACION_TYPECOL_LISTUNIDADES+" , "+
                    TABLE_CRITERIORECOMENDACION_COL_LISTFRECUENCIAS     +" "+TABLE_CRITERIORECOMENDACION_TYPECOL_LISTFRECUENCIAS+" , "+
                    TABLE_CRITERIORECOMENDACION_COL_IDTIPORECOMENDACION + " "+TABLE_CRITERIORECOMENDACION_TYPECOL_IDTIPORECOMENDACION+
                    ")";
    public static final String CREATE_TABLE_CONFIGURACIONRECOMENDACION =
            " CREATE TABLE IF NOT EXISTS "+TABLE_CONFIGURACIONRECOMENDACION+" (" +
                    TABLE_CONFIGURACIONRECOMENDACION_COL_ID                     +" "+TABLE_CONFIGURACIONRECOMENDACION_TYPECOL_ID+" PRIMARY KEY," +
                    TABLE_CONFIGURACIONRECOMENDACION_COL_IDFUNDOVARIEDAD        +" "+TABLE_CONFIGURACIONRECOMENDACION_TYPECOL_IDFUNDOVARIEDAD+" , "+
                    TABLE_CONFIGURACIONRECOMENDACION_COL_IDCRITERIORECOMENDACION+" "+TABLE_CONFIGURACIONRECOMENDACION_TYPECOL_IDCRITERIORECOMENDACION+
                    ")";
    public static final String CREATE_TABLE_RECOMENDACION =
            " CREATE TABLE IF NOT EXISTS "+TABLE_RECOMENDACION+" (" +
                    TABLE_RECOMENDACION_COL_ID                      +" "+TABLE_RECOMENDACION_TYPECOL_ID+" PRIMARY KEY AUTOINCREMENT , "+
                    TABLE_RECOMENDACION_COL_FRECUENCIA              +" "+TABLE_RECOMENDACION_TYPECOL_FRECUENCIA+" DEFAULT 0 , "+
                    TABLE_RECOMENDACION_COL_UNIDAD                  +" "+TABLE_RECOMENDACION_TYPECOL_UNIDAD +" DEFAULT 0 , "+
                    TABLE_RECOMENDACION_COL_CANTIDAD                +" "+TABLE_RECOMENDACION_TYPECOL_CANTIDAD  +" DEFAULT '' , "+
                    TABLE_RECOMENDACION_COL_COMENTARIO              +" "+TABLE_RECOMENDACION_TYPECOL_COMENTARIO +" DEFAULT '' , "+
                    TABLE_RECOMENDACION_COL_IDVISITA                +" "+TABLE_RECOMENDACION_TYPECOL_IDVISITA+" NOT NULL , "+
                    TABLE_RECOMENDACION_COL_IDCRITERIORECOMENDACION +" "+TABLE_RECOMENDACION_TYPECOL_IDCRITERIORECOMENDACION+" NOT NULL "+
                    ")";
    public static final String CREATE_TABLE_COLAFOTOS =
            " CREATE TABLE IF NOT EXISTS "+TABLE_COLAFOTOS+" ("+
                    TABLE_COLAFOTOS_COL_IDDB    +" "+TABLE_COLAFOTOS_TYPECOL_IDDB+" PRIMARY KEY, "+
                    TABLE_COLAFOTOS_COL_PATH    +" "+TABLE_COLAFOTOS_TYPECOL_PATH+" NOT NULL "+
                    ")";
}
