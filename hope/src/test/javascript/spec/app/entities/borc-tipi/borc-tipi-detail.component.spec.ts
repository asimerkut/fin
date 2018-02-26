/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { BorcTipiDetailComponent } from '../../../../../../main/webapp/app/entities/borc-tipi/borc-tipi-detail.component';
import { BorcTipiService } from '../../../../../../main/webapp/app/entities/borc-tipi/borc-tipi.service';
import { BorcTipi } from '../../../../../../main/webapp/app/entities/borc-tipi/borc-tipi.model';

describe('Component Tests', () => {

    describe('BorcTipi Management Detail Component', () => {
        let comp: BorcTipiDetailComponent;
        let fixture: ComponentFixture<BorcTipiDetailComponent>;
        let service: BorcTipiService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [BorcTipiDetailComponent],
                providers: [
                    BorcTipiService
                ]
            })
            .overrideTemplate(BorcTipiDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BorcTipiDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BorcTipiService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new BorcTipi(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.borcTipi).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
