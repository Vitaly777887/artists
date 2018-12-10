import { IAlbum } from 'app/shared/model//album.model';

export interface ISong {
    id?: number;
    title?: string;
    duration?: number;
    album?: IAlbum;
}

export class Song implements ISong {
    constructor(public id?: number, public title?: string, public duration?: number, public album?: IAlbum) {}
}
