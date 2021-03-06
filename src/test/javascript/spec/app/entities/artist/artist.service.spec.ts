/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { ArtistService } from 'app/entities/artist/artist.service';
import { IArtist, Artist, Sex } from 'app/shared/model/artist.model';

describe('Service Tests', () => {
    describe('Artist Service', () => {
        let injector: TestBed;
        let service: ArtistService;
        let httpMock: HttpTestingController;
        let elemDefault: IArtist;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(ArtistService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Artist(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, Sex.MAN, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        birthday: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Artist', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        birthday: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        birthday: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Artist(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Artist', async () => {
                const returnedFromService = Object.assign(
                    {
                        nickname: 'BBBBBB',
                        name: 'BBBBBB',
                        surname: 'BBBBBB',
                        birthday: currentDate.format(DATE_FORMAT),
                        sex: 'BBBBBB',
                        siteUrl: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        birthday: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Artist', async () => {
                const returnedFromService = Object.assign(
                    {
                        nickname: 'BBBBBB',
                        name: 'BBBBBB',
                        surname: 'BBBBBB',
                        birthday: currentDate.format(DATE_FORMAT),
                        sex: 'BBBBBB',
                        siteUrl: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        birthday: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Artist', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
