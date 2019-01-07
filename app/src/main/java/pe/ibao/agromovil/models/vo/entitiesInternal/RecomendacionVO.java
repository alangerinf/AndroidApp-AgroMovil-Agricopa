package pe.ibao.agromovil.models.vo.entitiesInternal;

import pe.ibao.agromovil.models.vo.entitiesDB.CriterioRecomendacionVO;

public class    RecomendacionVO extends CriterioRecomendacionVO {


    private int ids;
    private int frecuencia;
    private int unidad;
    private String cantidad;
    private String comentario;
    private int idCriterioRecomendacion;
    private int idVisita;

    public RecomendacionVO() {

    }

    @Override
    public int getId() {
        return ids;
    }

    @Override
    public void setId(int id) {
        this.ids = id;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getUnidad() {
        return unidad;
    }

    public void setUnidad(int unidad) {
        this.unidad = unidad;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdCriterioRecomendacion() {
        return idCriterioRecomendacion;
    }

    public void setIdCriterioRecomendacion(int idCriterioRecomendacion) {
        this.idCriterioRecomendacion = idCriterioRecomendacion;
    }

    public int getIdVisita() {
        return idVisita;
    }

    public void setIdVisita(int idVisita) {
        this.idVisita = idVisita;
    }
}
