export class UploadImageDto {
    photo: File;
    noticia: String;

    constructor(photo: File, noticia: String) {
        this.photo = photo;
        this.noticia = noticia;
    }
}
