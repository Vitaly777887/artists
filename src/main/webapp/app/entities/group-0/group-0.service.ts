import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGroup0 } from 'app/shared/model/group-0.model';

type EntityResponseType = HttpResponse<IGroup0>;
type EntityArrayResponseType = HttpResponse<IGroup0[]>;

@Injectable({ providedIn: 'root' })
export class Group0Service {
    public resourceUrl = SERVER_API_URL + 'api/group-0-s';

    constructor(private http: HttpClient) {}

    create(group0: IGroup0): Observable<EntityResponseType> {
        return this.http.post<IGroup0>(this.resourceUrl, group0, { observe: 'response' });
    }

    update(group0: IGroup0): Observable<EntityResponseType> {
        return this.http.put<IGroup0>(this.resourceUrl, group0, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGroup0>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGroup0[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
