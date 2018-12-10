import { ISong } from 'app/shared/model//song.model';
import { IGroup0 } from 'app/shared/model//group-0.model';
import { IArtist } from 'app/shared/model//artist.model';

export interface IAlbum {
    id?: number;
    title?: string;
    year?: number;
    countSongs?: number;
    songs?: ISong[];
    groups?: IGroup0[];
    artists?: IArtist[];
}

export class Album implements IAlbum {
    constructor(
        public id?: number,
        public title?: string,
        public year?: number,
        public countSongs?: number,
        public songs?: ISong[],
        public groups?: IGroup0[],
        public artists?: IArtist[]
    ) {}
}
