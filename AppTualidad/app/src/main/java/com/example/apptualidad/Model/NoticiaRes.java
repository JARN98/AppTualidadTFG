package com.example.apptualidad.Model;

public class NoticiaRes {
    public String id, title, likes, localizacion;
    public Photos photos;

    public NoticiaRes(String id, String title, String likes, String localizacion, Photos photos) {
        this.id = id;
        this.title = title;
        this.likes = likes;
        this.localizacion = localizacion;
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getLinkPhoto() {
        if(photos != null){
            return photos.link;
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NoticiaRes that = (NoticiaRes) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (likes != null ? !likes.equals(that.likes) : that.likes != null) return false;
        if (localizacion != null ? !localizacion.equals(that.localizacion) : that.localizacion != null)
            return false;
        return photos != null ? photos.equals(that.photos) : that.photos == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (localizacion != null ? localizacion.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NoticiaRes{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", likes='" + likes + '\'' +
                ", localizacion='" + localizacion + '\'' +
                ", photos=" + photos +
                '}';
    }
}

class Photos {
    public String id, idNoticia, link;

    public Photos(String id, String idNoticia, String link) {
        this.id = id;
        this.idNoticia = idNoticia;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdNoticia() {
        return idNoticia;
    }

    public void setIdNoticia(String idNoticia) {
        this.idNoticia = idNoticia;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photos photos = (Photos) o;

        if (id != null ? !id.equals(photos.id) : photos.id != null) return false;
        if (idNoticia != null ? !idNoticia.equals(photos.idNoticia) : photos.idNoticia != null)
            return false;
        return link != null ? link.equals(photos.link) : photos.link == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idNoticia != null ? idNoticia.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }
}
