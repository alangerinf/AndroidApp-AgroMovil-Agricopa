package pe.ibao.agromovil.models.vo.entitiesDB;

public class ZonaVO {
    private int id;
    private String name;

    public ZonaVO(){
    }

    public ZonaVO(int id, String name) {
        this.id = id;
        this.name = name;
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
