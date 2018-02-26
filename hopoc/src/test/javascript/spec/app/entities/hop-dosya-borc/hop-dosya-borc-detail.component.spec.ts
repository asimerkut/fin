/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcDetailComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc-detail.component';
import { HopDosyaBorcService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc.service';
import { HopDosyaBorc } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc.model';

describe('Component Tests', () => {

    describe('HopDosyaBorc Management Detail Component', () => {
        let comp: HopDosyaBorcDetailComponent;
        let fixture: ComponentFixture<HopDosyaBorcDetailComponent>;
        let service: HopDosyaBorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcDetailComponent],
                providers: [
                    HopDosyaBorcService
                ]
            })
            .overrideTemplate(HopDosyaBorcDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopDosyaBorc(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopDosyaBorc).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
