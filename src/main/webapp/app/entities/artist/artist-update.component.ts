import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';

import { IArtist } from 'app/shared/model/artist.model';
import { ArtistService } from './artist.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album';
import { IGenre } from 'app/shared/model/genre.model';
import { GenreService } from 'app/entities/genre';
import { ICountry } from 'app/shared/model/country.model';
import { CountryService } from 'app/entities/country';

@Component({
    selector: 'jhi-artist-update',
    templateUrl: './artist-update.component.html'
})
export class ArtistUpdateComponent implements OnInit {
    artist: IArtist;
    isSaving: boolean;

    albums: IAlbum[];

    genres: IGenre[];

    countries: ICountry[];
    birthdayDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private artistService: ArtistService,
        private albumService: AlbumService,
        private genreService: GenreService,
        private countryService: CountryService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ artist }) => {
            this.artist = artist;
        });
        this.albumService.query().subscribe(
            (res: HttpResponse<IAlbum[]>) => {
                this.albums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.genreService.query().subscribe(
            (res: HttpResponse<IGenre[]>) => {
                this.genres = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.countryService.query().subscribe(
            (res: HttpResponse<ICountry[]>) => {
                this.countries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.artist.id !== undefined) {
            this.subscribeToSaveResponse(this.artistService.update(this.artist));
        } else {
            this.subscribeToSaveResponse(this.artistService.create(this.artist));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IArtist>>) {
        result.subscribe((res: HttpResponse<IArtist>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAlbumById(index: number, item: IAlbum) {
        return item.id;
    }

    trackGenreById(index: number, item: IGenre) {
        return item.id;
    }

    trackCountryById(index: number, item: ICountry) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
