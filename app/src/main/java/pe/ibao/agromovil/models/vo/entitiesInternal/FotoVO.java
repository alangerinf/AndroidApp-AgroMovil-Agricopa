package pe.ibao.agromovil.models.vo.entitiesInternal;

import android.graphics.Bitmap;

public class FotoVO {
    private int id;
    private String fechaHora;
    private String path;
    private int IdMuestra;
    private Bitmap bitmap;
    private String StringBitmap;

    public FotoVO(int id, String fechaHora, String path) {
        this.id = id;
        this.fechaHora = fechaHora;
        this.path = path;
    }

    public String getStringBitmap() {
        return StringBitmap;
    }

    public void setStringBitmap(String stringBitmap) {
        StringBitmap = stringBitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public FotoVO() {

    }

    public int getIdMuestra() {
        return IdMuestra;
    }

    public void setIdMuestra(int idMuestra) {
        IdMuestra = idMuestra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fecha) {
        this.fechaHora = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
