/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopDosyaComponent } from '../../../../../../main/webapp/app/entities/hop-dosya/hop-dosya.component';
import { HopDosyaService } from '../../../../../../main/webapp/app/entities/hop-dosya/hop-dosya.service';
import { HopDosya } from '../../../../../../main/webapp/app/entities/hop-dosya/hop-dosya.model';

describe('Component Tests', () => {

    describe('HopDosya Management Component', () => {
        let comp: HopDosyaComponent;
        let fixture: ComponentFixture<HopDosyaComponent>;
        let service: HopDosyaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaComponent],
                providers: [
                    HopDosyaService
                ]
            })
            .overrideTemplate(HopDosyaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopDosya(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopDosyas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
