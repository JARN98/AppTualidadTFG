package com.example.apptualidad.Model;

public class AddNoticiaDto {
    private String title;
    private String description;
    private String direccion;
    private String localizacion;

    public AddNoticiaDto(String title, String description, String direccion, String localizacion) {
        this.title = title;
        this.description = description;
        this.direccion = direccion;
        this.localizacion = localizacion;
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

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddNoticiaDto that = (AddNoticiaDto) o;

        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (direccion != null ? !direccion.equals(that.direccion) : that.direccion != null)
            return false;
        return localizacion != null ? localizacion.equals(that.localizacion) : that.localizacion == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (direccion != null ? direccion.hashCode() : 0);
        result = 31 * result + (localizacion != null ? localizacion.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddNoticiaDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", direccion='" + direccion + '\'' +
                ", localizacion='" + localizacion + '\'' +
                '}';
    }
}
