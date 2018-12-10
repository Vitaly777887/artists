import { IAlbum } from 'app/shared/model//album.model';

export interface IGroup0 {
    id?: number;
    nickname?: string;
    album?: IAlbum;
}

export class Group0 implements IGroup0 {
    constructor(public id?: number, public nickname?: string, public album?: IAlbum) {}
}
