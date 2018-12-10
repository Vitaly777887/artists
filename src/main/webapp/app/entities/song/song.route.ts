import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Song } from 'app/shared/model/song.model';
import { SongService } from './song.service';
import { SongComponent } from './song.component';
import { SongDetailComponent } from './song-detail.component';
import { SongUpdateComponent } from './song-update.component';
import { SongDeletePopupComponent } from './song-delete-dialog.component';
import { ISong } from 'app/shared/model/song.model';

@Injectable({ providedIn: 'root' })
export class SongResolve implements Resolve<ISong> {
    constructor(private service: SongService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Song> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Song>) => response.ok),
                map((song: HttpResponse<Song>) => song.body)
            );
        }
        return of(new Song());
    }
}

export const songRoute: Routes = [
    {
        path: 'song',
        component: SongComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Songs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'song/:id/view',
        component: SongDetailComponent,
        resolve: {
            song: SongResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Songs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'song/new',
        component: SongUpdateComponent,
        resolve: {
            song: SongResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Songs'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'song/:id/edit',
        component: SongUpdateComponent,
        resolve: {
            song: SongResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Songs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const songPopupRoute: Routes = [
    {
        path: 'song/:id/delete',
        component: SongDeletePopupComponent,
        resolve: {
            song: SongResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Songs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
