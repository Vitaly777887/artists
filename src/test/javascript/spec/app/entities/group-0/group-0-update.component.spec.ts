/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { ArtistsTestModule } from '../../../test.module';
import { Group0UpdateComponent } from 'app/entities/group-0/group-0-update.component';
import { Group0Service } from 'app/entities/group-0/group-0.service';
import { Group0 } from 'app/shared/model/group-0.model';

describe('Component Tests', () => {
    describe('Group0 Management Update Component', () => {
        let comp: Group0UpdateComponent;
        let fixture: ComponentFixture<Group0UpdateComponent>;
        let service: Group0Service;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArtistsTestModule],
                declarations: [Group0UpdateComponent]
            })
                .overrideTemplate(Group0UpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Group0UpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Group0Service);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Group0(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.group0 = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Group0();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.group0 = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
