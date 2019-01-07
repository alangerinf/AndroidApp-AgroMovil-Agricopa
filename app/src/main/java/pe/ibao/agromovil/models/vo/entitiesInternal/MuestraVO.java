package pe.ibao.agromovil.models.vo.entitiesInternal;

import pe.ibao.agromovil.models.vo.entitiesDB.CriterioVO;

public class MuestraVO extends CriterioVO {

    private int ids;
    private int idEvaluacion;
    private int idCriterio;
    private String value;//parsearlo a int float o boolean segun se crea conveniente
    private String time;
    private int idTipoInspeccion;
    private String coment;
    private boolean statusComent;

    public MuestraVO(){
        super();
    }

    public String getValue() {
        return value;

    }


    public int getIdTipoInspeccion() {
        return idTipoInspeccion;
    }

    public void setIdTipoInspeccion(int idTipoInspeccion) {
        this.idTipoInspeccion = idTipoInspeccion;
    }

    public String getComent() {
        return coment;
    }

    public void setComent(String coment) {
        this.coment = coment;
    }

    public boolean isStatusComent() {
        return statusComent;
    }

    public void setStatusComent(boolean statusComent) {
        this.statusComent = statusComent;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getId() {
        return ids;
    }


    public void setId(int id) {
        this.ids = id;
    }

    public int getIdEvaluacion() {
        return idEvaluacion;
    }

    public void setIdEvaluacion(int idEvaluacion) {
        this.idEvaluacion = idEvaluacion;
    }

    public int getIdCriterio() {
        return idCriterio;
    }

    public void setIdCriterio(int idCriterio) {
        this.idCriterio = idCriterio;
    }
}
