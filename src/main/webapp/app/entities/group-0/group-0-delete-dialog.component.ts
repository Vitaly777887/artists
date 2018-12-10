import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGroup0 } from 'app/shared/model/group-0.model';
import { Group0Service } from './group-0.service';

@Component({
    selector: 'jhi-group-0-delete-dialog',
    templateUrl: './group-0-delete-dialog.component.html'
})
export class Group0DeleteDialogComponent {
    group0: IGroup0;

    constructor(private group0Service: Group0Service, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.group0Service.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'group0ListModification',
                content: 'Deleted an group0'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-group-0-delete-popup',
    template: ''
})
export class Group0DeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ group0 }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(Group0DeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.group0 = group0;
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
