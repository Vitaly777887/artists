import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISong } from 'app/shared/model/song.model';
import { SongService } from './song.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album';

@Component({
    selector: 'jhi-song-update',
    templateUrl: './song-update.component.html'
})
export class SongUpdateComponent implements OnInit {
    song: ISong;
    isSaving: boolean;

    albums: IAlbum[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private songService: SongService,
        private albumService: AlbumService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ song }) => {
            this.song = song;
        });
        this.albumService.query().subscribe(
            (res: HttpResponse<IAlbum[]>) => {
                this.albums = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.song.id !== undefined) {
            this.subscribeToSaveResponse(this.songService.update(this.song));
        } else {
            this.subscribeToSaveResponse(this.songService.create(this.song));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISong>>) {
        result.subscribe((res: HttpResponse<ISong>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
