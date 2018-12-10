import { IArtist } from 'app/shared/model//artist.model';

export interface ICountry {
    id?: number;
    name?: string;
    artists?: IArtist[];
}

export class Country implements ICountry {
    constructor(public id?: number, public name?: string, public artists?: IArtist[]) {}
}
