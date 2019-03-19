package com.example.apptualidad.Model;


public class Comentarios {
    private String contenido;
    private String noticia;
    private String id;
    private Autor autor;

    public Comentarios(String contenido, String noticia, String id, Autor autor) {
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

    public String getNameAutor() {
        return autor.getName();
    }

    public String getPictureAutor() {
        return autor.getPicture();
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comentarios that = (Comentarios) o;

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
        return "Comentarios{" +
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
