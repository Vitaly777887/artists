import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from './album.service';
import { IArtist } from 'app/shared/model/artist.model';
import { ArtistService } from 'app/entities/artist';

@Component({
    selector: 'jhi-album-update',
    templateUrl: './album-update.component.html'
})
export class AlbumUpdateComponent implements OnInit {
    album: IAlbum;
    isSaving: boolean;

    artists: IArtist[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private albumService: AlbumService,
        private artistService: ArtistService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ album }) => {
            this.album = album;
        });
        this.artistService.query().subscribe(
            (res: HttpResponse<IArtist[]>) => {
                this.artists = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.album.id !== undefined) {
            this.subscribeToSaveResponse(this.albumService.update(this.album));
        } else {
            this.subscribeToSaveResponse(this.albumService.create(this.album));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAlbum>>) {
        result.subscribe((res: HttpResponse<IAlbum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackArtistById(index: number, item: IArtist) {
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
