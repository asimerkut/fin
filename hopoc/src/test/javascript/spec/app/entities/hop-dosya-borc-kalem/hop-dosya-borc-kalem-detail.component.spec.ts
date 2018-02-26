/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcKalemDetailComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem-detail.component';
import { HopDosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.service';
import { HopDosyaBorcKalem } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.model';

describe('Component Tests', () => {

    describe('HopDosyaBorcKalem Management Detail Component', () => {
        let comp: HopDosyaBorcKalemDetailComponent;
        let fixture: ComponentFixture<HopDosyaBorcKalemDetailComponent>;
        let service: HopDosyaBorcKalemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcKalemDetailComponent],
                providers: [
                    HopDosyaBorcKalemService
                ]
            })
            .overrideTemplate(HopDosyaBorcKalemDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcKalemDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcKalemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopDosyaBorcKalem(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopDosyaBorcKalem).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
