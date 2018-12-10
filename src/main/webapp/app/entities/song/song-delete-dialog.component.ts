import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISong } from 'app/shared/model/song.model';
import { SongService } from './song.service';

@Component({
    selector: 'jhi-song-delete-dialog',
    templateUrl: './song-delete-dialog.component.html'
})
export class SongDeleteDialogComponent {
    song: ISong;

    constructor(private songService: SongService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.songService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'songListModification',
                content: 'Deleted an song'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-song-delete-popup',
    template: ''
})
export class SongDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ song }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SongDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.song = song;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
