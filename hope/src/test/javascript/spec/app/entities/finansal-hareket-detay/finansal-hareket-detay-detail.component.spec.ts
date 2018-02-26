/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDetayDetailComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay-detail.component';
import { FinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.service';
import { FinansalHareketDetay } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.model';

describe('Component Tests', () => {

    describe('FinansalHareketDetay Management Detail Component', () => {
        let comp: FinansalHareketDetayDetailComponent;
        let fixture: ComponentFixture<FinansalHareketDetayDetailComponent>;
        let service: FinansalHareketDetayService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDetayDetailComponent],
                providers: [
                    FinansalHareketDetayService
                ]
            })
            .overrideTemplate(FinansalHareketDetayDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDetayDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketDetayService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FinansalHareketDetay(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.finansalHareketDetay).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
