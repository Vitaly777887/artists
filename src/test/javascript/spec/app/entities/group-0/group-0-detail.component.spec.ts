/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ArtistsTestModule } from '../../../test.module';
import { Group0DetailComponent } from 'app/entities/group-0/group-0-detail.component';
import { Group0 } from 'app/shared/model/group-0.model';

describe('Component Tests', () => {
    describe('Group0 Management Detail Component', () => {
        let comp: Group0DetailComponent;
        let fixture: ComponentFixture<Group0DetailComponent>;
        const route = ({ data: of({ group0: new Group0(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ArtistsTestModule],
                declarations: [Group0DetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Group0DetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Group0DetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.group0).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
