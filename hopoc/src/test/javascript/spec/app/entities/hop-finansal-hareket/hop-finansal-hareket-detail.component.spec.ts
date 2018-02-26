/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketDetailComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket-detail.component';
import { HopFinansalHareketService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket.service';
import { HopFinansalHareket } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket.model';

describe('Component Tests', () => {

    describe('HopFinansalHareket Management Detail Component', () => {
        let comp: HopFinansalHareketDetailComponent;
        let fixture: ComponentFixture<HopFinansalHareketDetailComponent>;
        let service: HopFinansalHareketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketDetailComponent],
                providers: [
                    HopFinansalHareketService
                ]
            })
            .overrideTemplate(HopFinansalHareketDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new HopFinansalHareket(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.hopFinansalHareket).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
