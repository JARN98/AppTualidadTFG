package com.example.apptualidad.Responses;

import com.example.apptualidad.Model.Photo;

import java.util.List;

public class MisPublicacionesResponse {
    private List<String> comentarios = null;
    private String title;
    private String description;
    private String likes;
    private List<Double> localizacion = null;
    private List<Photo> photos = null;
    private String id;

    public List<String> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public List<Double> getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(List<Double> localizacion) {
        this.localizacion = localizacion;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstPhoto() {
        if (photos.size() > 0) {
            return photos.get(0).getLink();
        } else  {
            return "";
        }

    }

    public MisPublicacionesResponse(List<String> comentarios, String title, String description, String likes, List<Double> localizacion, List<Photo> photos, String id) {
        this.comentarios = comentarios;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.localizacion = localizacion;
        this.photos = photos;
        this.id = id;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MisPublicacionesResponse that = (MisPublicacionesResponse) o;

        if (comentarios != null ? !comentarios.equals(that.comentarios) : that.comentarios != null)
            return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (likes != null ? !likes.equals(that.likes) : that.likes != null) return false;
        if (localizacion != null ? !localizacion.equals(that.localizacion) : that.localizacion != null)
            return false;
        if (photos != null ? !photos.equals(that.photos) : that.photos != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = comentarios != null ? comentarios.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (localizacion != null ? localizacion.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}

