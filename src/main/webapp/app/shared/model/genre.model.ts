import { IArtist } from 'app/shared/model//artist.model';

export interface IGenre {
    id?: number;
    name?: string;
    artists?: IArtist[];
}

export class Genre implements IGenre {
    constructor(public id?: number, public name?: string, public artists?: IArtist[]) {}
}
