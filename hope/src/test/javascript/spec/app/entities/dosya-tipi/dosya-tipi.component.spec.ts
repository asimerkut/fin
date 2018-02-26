/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DosyaTipiComponent } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi.component';
import { DosyaTipiService } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi.service';
import { DosyaTipi } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi.model';

describe('Component Tests', () => {

    describe('DosyaTipi Management Component', () => {
        let comp: DosyaTipiComponent;
        let fixture: ComponentFixture<DosyaTipiComponent>;
        let service: DosyaTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaTipiComponent],
                providers: [
                    DosyaTipiService
                ]
            })
            .overrideTemplate(DosyaTipiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaTipiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DosyaTipi(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dosyaTipis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
