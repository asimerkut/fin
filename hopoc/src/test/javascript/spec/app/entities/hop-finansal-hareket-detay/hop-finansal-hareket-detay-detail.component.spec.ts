/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketDetayDetailComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay-detail.component';
import { HopFinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.service';
import { HopFinansalHareketDetay } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.model';

describe('Component Tests', () => {

    describe('HopFinansalHareketDetay Management Detail Component', () => {
        let comp: HopFinansalHareketDetayDetailComponent;
        let fixture: ComponentFixture<HopFinansalHareketDetayDetailComponent>;
        let service: HopFinansalHareketDetayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketDetayDetailComponent],
                providers: [
                    HopFinansalHareketDetayService
                ]
            })
            .overrideTemplate(HopFinansalHareketDetayDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketDetayDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketDetayService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopFinansalHareketDetay(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopFinansalHareketDetay).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
