import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ArtistsSharedModule } from 'app/shared';
import {
    Group0Component,
    Group0DetailComponent,
    Group0UpdateComponent,
    Group0DeletePopupComponent,
    Group0DeleteDialogComponent,
    group0Route,
    group0PopupRoute
} from './';

const ENTITY_STATES = [...group0Route, ...group0PopupRoute];

@NgModule({
    imports: [ArtistsSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [Group0Component, Group0DetailComponent, Group0UpdateComponent, Group0DeleteDialogComponent, Group0DeletePopupComponent],
    entryComponents: [Group0Component, Group0UpdateComponent, Group0DeleteDialogComponent, Group0DeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtistsGroup0Module {}
