package pe.ibao.agromovil.models.vo.entitiesDB;

public class VariedadVO {

    private int id;
    private String name;
    private int idCultivo;

    public VariedadVO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public VariedadVO() {

    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public int getId() {
        return id;
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
