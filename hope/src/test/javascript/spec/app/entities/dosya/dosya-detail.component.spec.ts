/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DosyaDetailComponent } from '../../../../../../main/webapp/app/entities/dosya/dosya-detail.component';
import { DosyaService } from '../../../../../../main/webapp/app/entities/dosya/dosya.service';
import { Dosya } from '../../../../../../main/webapp/app/entities/dosya/dosya.model';

describe('Component Tests', () => {

    describe('Dosya Management Detail Component', () => {
        let comp: DosyaDetailComponent;
        let fixture: ComponentFixture<DosyaDetailComponent>;
        let service: DosyaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaDetailComponent],
                providers: [
                    DosyaService
                ]
            })
            .overrideTemplate(DosyaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Dosya(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dosya).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
