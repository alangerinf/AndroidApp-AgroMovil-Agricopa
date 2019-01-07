package pe.ibao.agromovil.models.vo.entitiesDB;

public class CriterioVO {

    private int id;
    private String name;
    private String type;
    private String magnitud;
    private int idTipoInspseccion;

    public CriterioVO(int id, String name, String type, String magnitud, int idTipoInspseccion) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.magnitud = magnitud;
        this.idTipoInspseccion = idTipoInspseccion;
    }

    public CriterioVO(int id, String name, String type, String magnitud) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.magnitud = magnitud;
    }

    public CriterioVO() {

    }

    public int getIdTipoInspseccion() {
        return idTipoInspseccion;
    }

    public void setIdTipoInspseccion(int idTipoInspseccion) {
        this.idTipoInspseccion = idTipoInspseccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMagnitud() {
        return magnitud;
    }

    public void setMagnitud(String magnitud) {
        this.magnitud = magnitud;
    }
}
