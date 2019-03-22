export interface NoticiaResponse {
    id: String;
    title: String;
    description: String;
    likes: String;
    comentarios: String[];
    localizacion: String;
    photos: Photo[];
    autor: Autor;
}

class Photo {
    id: String;
    idNoticia: String;
    link: String;
}

class Autor {
    email: String;
    name: String;
    picture: String;
}
