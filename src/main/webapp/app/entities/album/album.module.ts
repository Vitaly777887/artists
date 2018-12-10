import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtistsSharedModule } from 'app/shared';
import {
    AlbumComponent,
    AlbumDetailComponent,
    AlbumUpdateComponent,
    AlbumDeletePopupComponent,
    AlbumDeleteDialogComponent,
    albumRoute,
    albumPopupRoute
} from './';

const ENTITY_STATES = [...albumRoute, ...albumPopupRoute];

@NgModule({
    imports: [ArtistsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [AlbumComponent, AlbumDetailComponent, AlbumUpdateComponent, AlbumDeleteDialogComponent, AlbumDeletePopupComponent],
    entryComponents: [AlbumComponent, AlbumUpdateComponent, AlbumDeleteDialogComponent, AlbumDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtistsAlbumModule {}
