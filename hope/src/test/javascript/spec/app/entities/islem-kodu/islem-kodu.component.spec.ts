/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { IslemKoduComponent } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu.component';
import { IslemKoduService } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu.service';
import { IslemKodu } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu.model';

describe('Component Tests', () => {

    describe('IslemKodu Management Component', () => {
        let comp: IslemKoduComponent;
        let fixture: ComponentFixture<IslemKoduComponent>;
        let service: IslemKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [IslemKoduComponent],
                providers: [
                    IslemKoduService
                ]
            })
            .overrideTemplate(IslemKoduComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IslemKoduComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IslemKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new IslemKodu(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.islemKodus[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
