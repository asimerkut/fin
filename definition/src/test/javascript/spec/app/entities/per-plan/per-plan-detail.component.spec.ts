/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { FinTestModule } from '../../../test.module';
import { PerPlanDetailComponent } from '../../../../../../main/webapp/app/entities/per-plan/per-plan-detail.component';
import { PerPlanService } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.service';
import { PerPlan } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.model';

describe('Component Tests', () => {

    describe('PerPlan Management Detail Component', () => {
        let comp: PerPlanDetailComponent;
        let fixture: ComponentFixture<PerPlanDetailComponent>;
        let service: PerPlanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerPlanDetailComponent],
                providers: [
                    PerPlanService
                ]
            })
            .overrideTemplate(PerPlanDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerPlanDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerPlanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new PerPlan(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.perPlan).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
