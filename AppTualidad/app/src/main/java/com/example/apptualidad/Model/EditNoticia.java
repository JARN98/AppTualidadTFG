package com.example.apptualidad.Model;

public class EditNoticia {
    private String title;
    private String description;
    private String direccion;

    public EditNoticia(String title, String description, String direccion) {
        this.title = title;
        this.description = description;
        this.direccion = direccion;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EditNoticia that = (EditNoticia) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        return direccion != null ? direccion.equals(that.direccion) : that.direccion == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EditNoticia{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
