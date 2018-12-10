import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IGroup0 } from 'app/shared/model/group-0.model';
import { Group0Service } from './group-0.service';
import { IAlbum } from 'app/shared/model/album.model';
import { AlbumService } from 'app/entities/album';

@Component({
    selector: 'jhi-group-0-update',
    templateUrl: './group-0-update.component.html'
})
export class Group0UpdateComponent implements OnInit {
    group0: IGroup0;
    isSaving: boolean;

    albums: IAlbum[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private group0Service: Group0Service,
        private albumService: AlbumService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ group0 }) => {
            this.group0 = group0;
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
        if (this.group0.id !== undefined) {
            this.subscribeToSaveResponse(this.group0Service.update(this.group0));
        } else {
            this.subscribeToSaveResponse(this.group0Service.create(this.group0));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGroup0>>) {
        result.subscribe((res: HttpResponse<IGroup0>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
