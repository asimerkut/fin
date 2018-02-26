/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { DosyaComponent } from '../../../../../../main/webapp/app/entities/dosya/dosya.component';
import { DosyaService } from '../../../../../../main/webapp/app/entities/dosya/dosya.service';
import { Dosya } from '../../../../../../main/webapp/app/entities/dosya/dosya.model';

describe('Component Tests', () => {

    describe('Dosya Management Component', () => {
        let comp: DosyaComponent;
        let fixture: ComponentFixture<DosyaComponent>;
        let service: DosyaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaComponent],
                providers: [
                    DosyaService
                ]
            })
            .overrideTemplate(DosyaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Dosya(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dosyas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
