import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IArtist } from 'app/shared/model/artist.model';

type EntityResponseType = HttpResponse<IArtist>;
type EntityArrayResponseType = HttpResponse<IArtist[]>;

@Injectable({ providedIn: 'root' })
export class ArtistService {
    public resourceUrl = SERVER_API_URL + 'api/artists';

    constructor(private http: HttpClient) {}

    create(artist: IArtist): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(artist);
        return this.http
            .post<IArtist>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(artist: IArtist): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(artist);
        return this.http
            .put<IArtist>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IArtist>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IArtist[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(artist: IArtist): IArtist {
        const copy: IArtist = Object.assign({}, artist, {
            birthday: artist.birthday != null && artist.birthday.isValid() ? artist.birthday.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((artist: IArtist) => {
                artist.birthday = artist.birthday != null ? moment(artist.birthday) : null;
            });
        }
        return res;
    }
}
