package com.example.apptualidad.Responses;

import com.example.apptualidad.Model.Comentarios;

import java.util.ArrayList;
import java.util.List;

public class GetOneNoticiaResponse {

    public String id;
    public String title;
    public String description;
    public String likes;
    public List<Comentarios> comentarios = null;
    public String localizacion;
    public List<Photo> photos = null;
    public Autor autor;


    public GetOneNoticiaResponse(String id, String title, String description, String likes, List<Comentarios> comentarios, String localizacion, List<Photo> photos, Autor autor) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.likes = likes;
        this.comentarios = comentarios;
        this.localizacion = localizacion;
        this.photos = photos;
        this.autor = autor;
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

    public List<Comentarios> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentarios> comentarios) {
        this.comentarios = comentarios;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<String> getPhotoLink(){
        List<String> fotos = new ArrayList<>();
        for (int i = 0; i < photos.size(); i++) {
            fotos.add(photos.get(i).getLink());
        }
        return fotos;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GetOneNoticiaResponse that = (GetOneNoticiaResponse) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (likes != null ? !likes.equals(that.likes) : that.likes != null) return false;
        if (comentarios != null ? !comentarios.equals(that.comentarios) : that.comentarios != null)
            return false;
        if (localizacion != null ? !localizacion.equals(that.localizacion) : that.localizacion != null)
            return false;
        if (photos != null ? !photos.equals(that.photos) : that.photos != null) return false;
        return autor != null ? autor.equals(that.autor) : that.autor == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (comentarios != null ? comentarios.hashCode() : 0);
        result = 31 * result + (localizacion != null ? localizacion.hashCode() : 0);
        result = 31 * result + (photos != null ? photos.hashCode() : 0);
        result = 31 * result + (autor != null ? autor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GetOneNoticiaResponse{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", likes='" + likes + '\'' +
                ", comentarios=" + comentarios +
                ", localizacion='" + localizacion + '\'' +
                ", photos=" + photos +
                ", autor=" + autor +
                '}';
    }
}

class Comentario {
    private String contenido;
    private String noticia;
    private String id;
    private Autor autor;

    public Comentario(String contenido, String noticia, String id, Autor autor) {
        this.contenido = contenido;
        this.noticia = noticia;
        this.id = id;
        this.autor = autor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNoticia() {
        return noticia;
    }

    public void setNoticia(String noticia) {
        this.noticia = noticia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comentario that = (Comentario) o;

        if (contenido != null ? !contenido.equals(that.contenido) : that.contenido != null)
            return false;
        if (noticia != null ? !noticia.equals(that.noticia) : that.noticia != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return autor != null ? autor.equals(that.autor) : that.autor == null;
    }

    @Override
    public int hashCode() {
        int result = contenido != null ? contenido.hashCode() : 0;
        result = 31 * result + (noticia != null ? noticia.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (autor != null ? autor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "contenido='" + contenido + '\'' +
                ", noticia='" + noticia + '\'' +
                ", id='" + id + '\'' +
                ", autor=" + autor +
                '}';
    }
}

class Autor {
    private String picture;
    private String name;
    private String email;
    private String id;

    public Autor(String picture, String name, String email, String id) {
        this.picture = picture;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Autor autor = (Autor) o;

        if (picture != null ? !picture.equals(autor.picture) : autor.picture != null) return false;
        if (name != null ? !name.equals(autor.name) : autor.name != null) return false;
        if (email != null ? !email.equals(autor.email) : autor.email != null) return false;
        return id != null ? id.equals(autor.id) : autor.id == null;
    }

    @Override
    public int hashCode() {
        int result = picture != null ? picture.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
