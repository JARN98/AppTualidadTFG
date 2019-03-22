export class UploadImageDetailsDto {
    image: Array<any>;

    constructor(image?: Array<any>) {
        this.image = image;
    }
}