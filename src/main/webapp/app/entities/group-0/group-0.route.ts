import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Group0 } from 'app/shared/model/group-0.model';
import { Group0Service } from './group-0.service';
import { Group0Component } from './group-0.component';
import { Group0DetailComponent } from './group-0-detail.component';
import { Group0UpdateComponent } from './group-0-update.component';
import { Group0DeletePopupComponent } from './group-0-delete-dialog.component';
import { IGroup0 } from 'app/shared/model/group-0.model';

@Injectable({ providedIn: 'root' })
export class Group0Resolve implements Resolve<IGroup0> {
    constructor(private service: Group0Service) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Group0> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Group0>) => response.ok),
                map((group0: HttpResponse<Group0>) => group0.body)
            );
        }
        return of(new Group0());
    }
}

export const group0Route: Routes = [
    {
        path: 'group-0',
        component: Group0Component,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Group0S'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-0/:id/view',
        component: Group0DetailComponent,
        resolve: {
            group0: Group0Resolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Group0S'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-0/new',
        component: Group0UpdateComponent,
        resolve: {
            group0: Group0Resolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Group0S'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'group-0/:id/edit',
        component: Group0UpdateComponent,
        resolve: {
            group0: Group0Resolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Group0S'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const group0PopupRoute: Routes = [
    {
        path: 'group-0/:id/delete',
        component: Group0DeletePopupComponent,
        resolve: {
            group0: Group0Resolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Group0S'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
