package pe.ibao.agromovil.models.vo.entitiesDB;

public class CriterioRecomendacionVO {

    private int id;
    private String name;
    private String listUnidades;
    private String listFrecuancias;
    private int idTipoRecomendacion;

    public CriterioRecomendacionVO() {

    }

    public int getId() {
        return id;
    }

    public String getListUnidades() {
        return listUnidades;
    }

    public void setListUnidades(String listUnidades) {
        this.listUnidades = listUnidades;
    }

    public String getListFrecuancias() {
        return listFrecuancias;
    }

    public void setListFrecuancias(String listFrecuancias) {
        this.listFrecuancias = listFrecuancias;
    }

    public int getIdTipoRecomendacion() {
        return idTipoRecomendacion;
    }

    public void setIdTipoRecomendacion(int idTipoRecomendacion) {
        this.idTipoRecomendacion = idTipoRecomendacion;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
