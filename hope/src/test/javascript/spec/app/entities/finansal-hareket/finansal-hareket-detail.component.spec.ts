/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDetailComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket-detail.component';
import { FinansalHareketService } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.service';
import { FinansalHareket } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.model';

describe('Component Tests', () => {

    describe('FinansalHareket Management Detail Component', () => {
        let comp: FinansalHareketDetailComponent;
        let fixture: ComponentFixture<FinansalHareketDetailComponent>;
        let service: FinansalHareketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDetailComponent],
                providers: [
                    FinansalHareketService
                ]
            })
            .overrideTemplate(FinansalHareketDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FinansalHareket(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.finansalHareket).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
