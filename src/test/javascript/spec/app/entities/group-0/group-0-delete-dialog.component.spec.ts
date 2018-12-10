/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ArtistsTestModule } from '../../../test.module';
import { Group0DeleteDialogComponent } from 'app/entities/group-0/group-0-delete-dialog.component';
import { Group0Service } from 'app/entities/group-0/group-0.service';

describe('Component Tests', () => {
    describe('Group0 Management Delete Component', () => {
        let comp: Group0DeleteDialogComponent;
        let fixture: ComponentFixture<Group0DeleteDialogComponent>;
        let service: Group0Service;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArtistsTestModule],
                declarations: [Group0DeleteDialogComponent]
            })
                .overrideTemplate(Group0DeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Group0DeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Group0Service);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
