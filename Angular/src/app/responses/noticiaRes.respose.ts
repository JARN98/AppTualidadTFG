export interface NoticiaResResponse {
    id: String;
    title: String;
    likes: String;
    localizacion: String;
    photos: Photos;
}

class Photos {
    id: String;
    idNoticia: String;
    link: String;
}
