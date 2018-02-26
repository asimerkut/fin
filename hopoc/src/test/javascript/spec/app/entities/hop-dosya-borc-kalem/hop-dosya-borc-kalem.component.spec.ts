/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcKalemComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.component';
import { HopDosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.service';
import { HopDosyaBorcKalem } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.model';

describe('Component Tests', () => {

    describe('HopDosyaBorcKalem Management Component', () => {
        let comp: HopDosyaBorcKalemComponent;
        let fixture: ComponentFixture<HopDosyaBorcKalemComponent>;
        let service: HopDosyaBorcKalemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcKalemComponent],
                providers: [
                    HopDosyaBorcKalemService
                ]
            })
            .overrideTemplate(HopDosyaBorcKalemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcKalemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcKalemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopDosyaBorcKalem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopDosyaBorcKalems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
