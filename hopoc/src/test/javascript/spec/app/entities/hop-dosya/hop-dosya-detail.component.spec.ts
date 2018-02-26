/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopDosyaDetailComponent } from '../../../../../../main/webapp/app/entities/hop-dosya/hop-dosya-detail.component';
import { HopDosyaService } from '../../../../../../main/webapp/app/entities/hop-dosya/hop-dosya.service';
import { HopDosya } from '../../../../../../main/webapp/app/entities/hop-dosya/hop-dosya.model';

describe('Component Tests', () => {

    describe('HopDosya Management Detail Component', () => {
        let comp: HopDosyaDetailComponent;
        let fixture: ComponentFixture<HopDosyaDetailComponent>;
        let service: HopDosyaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaDetailComponent],
                providers: [
                    HopDosyaService
                ]
            })
            .overrideTemplate(HopDosyaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopDosya(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopDosya).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
