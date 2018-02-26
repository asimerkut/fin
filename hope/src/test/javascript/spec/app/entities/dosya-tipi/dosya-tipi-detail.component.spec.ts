/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DosyaTipiDetailComponent } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi-detail.component';
import { DosyaTipiService } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi.service';
import { DosyaTipi } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi.model';

describe('Component Tests', () => {

    describe('DosyaTipi Management Detail Component', () => {
        let comp: DosyaTipiDetailComponent;
        let fixture: ComponentFixture<DosyaTipiDetailComponent>;
        let service: DosyaTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaTipiDetailComponent],
                providers: [
                    DosyaTipiService
                ]
            })
            .overrideTemplate(DosyaTipiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaTipiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DosyaTipi(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dosyaTipi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
