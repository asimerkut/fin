/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { IslemKoduDetailComponent } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu-detail.component';
import { IslemKoduService } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu.service';
import { IslemKodu } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu.model';

describe('Component Tests', () => {

    describe('IslemKodu Management Detail Component', () => {
        let comp: IslemKoduDetailComponent;
        let fixture: ComponentFixture<IslemKoduDetailComponent>;
        let service: IslemKoduService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [IslemKoduDetailComponent],
                providers: [
                    IslemKoduService
                ]
            })
            .overrideTemplate(IslemKoduDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IslemKoduDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IslemKoduService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new IslemKodu(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.islemKodu).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
