package pe.ibao.agromovil.models.vo.entitiesDB;

public class FundoVO {

    private int id;
    private String name;
    private String sistemaRiego;
    private int idEmpresa;


    public String getSistemaRiego() {
        return sistemaRiego;
    }

    public void setSistemaRiego(String sistemaRiego) {
        this.sistemaRiego = sistemaRiego;
    }

    public FundoVO() {
    }

    public FundoVO(int id, String name, int idEmpresa) {
        this.id = id;
        this.name = name;
        this.idEmpresa = idEmpresa;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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
