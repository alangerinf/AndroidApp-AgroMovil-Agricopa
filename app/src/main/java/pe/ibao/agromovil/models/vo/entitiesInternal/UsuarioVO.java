package pe.ibao.agromovil.models.vo.entitiesInternal;

public class UsuarioVO {
    private int id;
    private String user;
    private String password;
    private String name;
    private String lastName;
    private String codigo;

    public UsuarioVO(int id, String user, String password) {
        this.id = id;
        this.user = user;
        this.password = password;
    }

    public UsuarioVO() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodigo() {
        return this.codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
