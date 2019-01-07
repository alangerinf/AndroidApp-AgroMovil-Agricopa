package pe.ibao.agromovilagricopa.models.vo.entitiesDB;

public class EmpresaVO {

    private int id;
    private String name;
    private int idZona;

    public EmpresaVO() {
    }

    public EmpresaVO(int id, String name,int idZona) {
        this.id = id;
        this.name = name;
        this.idZona = idZona;
    }


    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
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
