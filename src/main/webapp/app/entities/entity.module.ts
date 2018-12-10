import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ArtistsGenreModule } from './genre/genre.module';
import { ArtistsGroup0Module } from './group-0/group-0.module';
import { ArtistsCountryModule } from './country/country.module';
import { ArtistsAlbumModule } from './album/album.module';
import { ArtistsSongModule } from './song/song.module';
import { ArtistsArtistModule } from './artist/artist.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        ArtistsGenreModule,
        ArtistsGroup0Module,
        ArtistsCountryModule,
        ArtistsAlbumModule,
        ArtistsSongModule,
        ArtistsArtistModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ArtistsEntityModule {}
