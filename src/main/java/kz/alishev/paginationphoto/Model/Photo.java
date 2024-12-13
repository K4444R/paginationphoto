package kz.alishev.paginationphoto.Model;


public class Photo {

    private String filename;
    private String url;

    // Конструктор
    public Photo(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }

    // Геттеры и сеттеры
    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
