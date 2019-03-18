package com.example.apptualidad.Responses;

public class UploadPhotoUser {
    private String id;
    private String noticia;
    private String imgurLink;

    public UploadPhotoUser(String id, String noticia, String imgurLink) {
        this.id = id;
        this.noticia = noticia;
        this.imgurLink = imgurLink;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticia() {
        return noticia;
    }

    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }

    public String getImgurLink() {
        return imgurLink;
    }

    public void setImgurLink(String imgurLink) {
        this.imgurLink = imgurLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UploadPhotoUser that = (UploadPhotoUser) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (noticia != null ? !noticia.equals(that.noticia) : that.noticia != null) return false;
        return imgurLink != null ? imgurLink.equals(that.imgurLink) : that.imgurLink == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (noticia != null ? noticia.hashCode() : 0);
        result = 31 * result + (imgurLink != null ? imgurLink.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UploadPhotoUser{" +
                "id='" + id + '\'' +
                ", noticia='" + noticia + '\'' +
                ", imgurLink='" + imgurLink + '\'' +
                '}';
    }
}
