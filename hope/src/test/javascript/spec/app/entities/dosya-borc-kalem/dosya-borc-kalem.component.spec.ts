/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcKalemComponent } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.component';
import { DosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.service';
import { DosyaBorcKalem } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.model';

describe('Component Tests', () => {

    describe('DosyaBorcKalem Management Component', () => {
        let comp: DosyaBorcKalemComponent;
        let fixture: ComponentFixture<DosyaBorcKalemComponent>;
        let service: DosyaBorcKalemService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcKalemComponent],
                providers: [
                    DosyaBorcKalemService
                ]
            })
            .overrideTemplate(DosyaBorcKalemComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcKalemComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcKalemService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DosyaBorcKalem(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dosyaBorcKalems[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
