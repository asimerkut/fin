/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcKalemDetailComponent } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem-detail.component';
import { DosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.service';
import { DosyaBorcKalem } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.model';

describe('Component Tests', () => {

    describe('DosyaBorcKalem Management Detail Component', () => {
        let comp: DosyaBorcKalemDetailComponent;
        let fixture: ComponentFixture<DosyaBorcKalemDetailComponent>;
        let service: DosyaBorcKalemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcKalemDetailComponent],
                providers: [
                    DosyaBorcKalemService
                ]
            })
            .overrideTemplate(DosyaBorcKalemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcKalemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcKalemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DosyaBorcKalem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dosyaBorcKalem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
