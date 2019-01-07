package pe.ibao.agromovil.models.vo.entitiesInternal;

import android.graphics.Bitmap;

public class ColaUploadFotosVO {

    int idBD;
    String path;
    private Bitmap bitmap;
    private String StringBitmap;

    public ColaUploadFotosVO() {
    }

    public int getIdBD() {
        return idBD;
    }

    public void setIdBD(int idBD) {
        this.idBD = idBD;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getStringBitmap() {
        return StringBitmap;
    }

    public void setStringBitmap(String stringBitmap) {
        StringBitmap = stringBitmap;
    }
}
