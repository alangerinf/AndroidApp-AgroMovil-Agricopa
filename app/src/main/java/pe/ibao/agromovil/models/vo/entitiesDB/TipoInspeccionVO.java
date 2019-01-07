package pe.ibao.agromovil.models.vo.entitiesDB;

public class TipoInspeccionVO {
    private int id;
    private String name;

    public TipoInspeccionVO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public TipoInspeccionVO() {

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
