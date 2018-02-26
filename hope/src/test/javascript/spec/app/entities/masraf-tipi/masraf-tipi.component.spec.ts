/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { MasrafTipiComponent } from '../../../../../../main/webapp/app/entities/masraf-tipi/masraf-tipi.component';
import { MasrafTipiService } from '../../../../../../main/webapp/app/entities/masraf-tipi/masraf-tipi.service';
import { MasrafTipi } from '../../../../../../main/webapp/app/entities/masraf-tipi/masraf-tipi.model';

describe('Component Tests', () => {

    describe('MasrafTipi Management Component', () => {
        let comp: MasrafTipiComponent;
        let fixture: ComponentFixture<MasrafTipiComponent>;
        let service: MasrafTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [MasrafTipiComponent],
                providers: [
                    MasrafTipiService
                ]
            })
            .overrideTemplate(MasrafTipiComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MasrafTipiComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MasrafTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MasrafTipi(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.masrafTipis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
