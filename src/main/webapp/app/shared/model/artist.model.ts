import { Moment } from 'moment';
import { IAlbum } from 'app/shared/model//album.model';
import { IGenre } from 'app/shared/model//genre.model';
import { ICountry } from 'app/shared/model//country.model';

export const enum Sex {
    MAN = 'MAN',
    WOMAN = 'WOMAN'
}

export interface IArtist {
    id?: number;
    nickname?: string;
    name?: string;
    surname?: string;
    birthday?: Moment;
    sex?: Sex;
    siteUrl?: string;
    albums?: IAlbum[];
    genre?: IGenre;
    country?: ICountry;
}

export class Artist implements IArtist {
    constructor(
        public id?: number,
        public nickname?: string,
        public name?: string,
        public surname?: string,
        public birthday?: Moment,
        public sex?: Sex,
        public siteUrl?: string,
        public albums?: IAlbum[],
        public genre?: IGenre,
        public country?: ICountry
    ) {}
}
