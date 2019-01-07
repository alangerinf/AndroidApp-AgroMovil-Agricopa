package pe.ibao.agromovil.models.vo.entitiesInternal;

public class EvaluacionVO {

    private int id;
    private int idTipoInspeccion;
    private String nameInspeccion;
    private int porcentaje;
    private double lon;
    private double lat;
    private String timeIni;
    private String timeFin;
    private String qr;
    private int idVisita;



    public String getNameInspeccion() {
        return nameInspeccion;
    }

    public void setNameInspeccion(String nameInspeccion) {
        this.nameInspeccion = nameInspeccion;
    }

    public int getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(int porcentaje) {
        this.porcentaje = porcentaje;
    }


    public String getTimeIni() {
        return timeIni;
    }

    public void setTimeIni(String timeIni) {
        this.timeIni = timeIni;
    }

    public String getTimeFin() {
        return timeFin;
    }

    public void setTimeFin(String timeFin) {
        this.timeFin = timeFin;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }


    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTipoInspeccion() {
        return idTipoInspeccion;
    }

    public void setIdTipoInspeccion(int idTipoInspeccion) {
        this.idTipoInspeccion = idTipoInspeccion;
    }


}
