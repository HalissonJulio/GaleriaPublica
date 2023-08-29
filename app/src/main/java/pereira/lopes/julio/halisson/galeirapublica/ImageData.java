package pereira.lopes.julio.halisson.galeirapublica;

import android.graphics.Bitmap;
import android.net.Uri;

import java.util.Date;

public class ImageData {
    // Guarda o endereço uri do arquivo de foto;
    public Uri uri;
    // Guarda a imagem em minitura da foto;
    public Bitmap thumb;
    // Guarda o nome do arquivo de foto;
    public String fileName;
    // Guarda a data em que a foto foi criada;
    public Date date;
    // Guarda o tamanho em bytes do arquivo de foto
    public int size;

    public ImageData(Uri uri, Bitmap thumb, String fileName, Date date, int size) {
        this.uri = uri;
        this.thumb = thumb;
        this.fileName = fileName;
        this.date = date;
        this.size = size;
    }
}
