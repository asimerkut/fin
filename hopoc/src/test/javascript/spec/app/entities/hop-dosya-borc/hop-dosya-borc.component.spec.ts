/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc.component';
import { HopDosyaBorcService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc.service';
import { HopDosyaBorc } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc.model';

describe('Component Tests', () => {

    describe('HopDosyaBorc Management Component', () => {
        let comp: HopDosyaBorcComponent;
        let fixture: ComponentFixture<HopDosyaBorcComponent>;
        let service: HopDosyaBorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcComponent],
                providers: [
                    HopDosyaBorcService
                ]
            })
            .overrideTemplate(HopDosyaBorcComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopDosyaBorc(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopDosyaBorcs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
