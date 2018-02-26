/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcDetailComponent } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc-detail.component';
import { DosyaBorcService } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.service';
import { DosyaBorc } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.model';

describe('Component Tests', () => {

    describe('DosyaBorc Management Detail Component', () => {
        let comp: DosyaBorcDetailComponent;
        let fixture: ComponentFixture<DosyaBorcDetailComponent>;
        let service: DosyaBorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcDetailComponent],
                providers: [
                    DosyaBorcService
                ]
            })
            .overrideTemplate(DosyaBorcDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new DosyaBorc(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dosyaBorc).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
