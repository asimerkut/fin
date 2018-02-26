/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketDetayComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.component';
import { HopFinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.service';
import { HopFinansalHareketDetay } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.model';

describe('Component Tests', () => {

    describe('HopFinansalHareketDetay Management Component', () => {
        let comp: HopFinansalHareketDetayComponent;
        let fixture: ComponentFixture<HopFinansalHareketDetayComponent>;
        let service: HopFinansalHareketDetayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketDetayComponent],
                providers: [
                    HopFinansalHareketDetayService
                ]
            })
            .overrideTemplate(HopFinansalHareketDetayComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketDetayComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketDetayService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new HopFinansalHareketDetay(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.hopFinansalHareketDetays[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
