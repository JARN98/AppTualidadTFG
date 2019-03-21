package com.example.apptualidad.Model;

public class Photo {
        private String id;
        private String idNoticia;
        private String link;

        public Photo(String id, String idNoticia, String link) {
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

        Photo photo = (Photo) o;

        if (id != null ? !id.equals(photo.id) : photo.id != null) return false;
        if (idNoticia != null ? !idNoticia.equals(photo.idNoticia) : photo.idNoticia != null)
            return false;
        return link != null ? link.equals(photo.link) : photo.link == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idNoticia != null ? idNoticia.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", idNoticia='" + idNoticia + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
