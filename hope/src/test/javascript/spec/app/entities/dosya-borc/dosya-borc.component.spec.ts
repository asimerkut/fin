/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcComponent } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.component';
import { DosyaBorcService } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.service';
import { DosyaBorc } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.model';

describe('Component Tests', () => {

    describe('DosyaBorc Management Component', () => {
        let comp: DosyaBorcComponent;
        let fixture: ComponentFixture<DosyaBorcComponent>;
        let service: DosyaBorcService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcComponent],
                providers: [
                    DosyaBorcService
                ]
            })
            .overrideTemplate(DosyaBorcComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DosyaBorc(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dosyaBorcs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
