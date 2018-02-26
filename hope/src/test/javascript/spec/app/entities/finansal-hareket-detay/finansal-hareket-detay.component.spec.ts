/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDetayComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.component';
import { FinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.service';
import { FinansalHareketDetay } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.model';

describe('Component Tests', () => {

    describe('FinansalHareketDetay Management Component', () => {
        let comp: FinansalHareketDetayComponent;
        let fixture: ComponentFixture<FinansalHareketDetayComponent>;
        let service: FinansalHareketDetayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDetayComponent],
                providers: [
                    FinansalHareketDetayService
                ]
            })
            .overrideTemplate(FinansalHareketDetayComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDetayComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketDetayService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FinansalHareketDetay(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.finansalHareketDetays[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
