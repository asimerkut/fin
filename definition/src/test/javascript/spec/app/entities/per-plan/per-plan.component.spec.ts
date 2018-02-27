/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { FinTestModule } from '../../../test.module';
import { PerPlanComponent } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.component';
import { PerPlanService } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.service';
import { PerPlan } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.model';

describe('Component Tests', () => {

    describe('PerPlan Management Component', () => {
        let comp: PerPlanComponent;
        let fixture: ComponentFixture<PerPlanComponent>;
        let service: PerPlanService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerPlanComponent],
                providers: [
                    PerPlanService
                ]
            })
            .overrideTemplate(PerPlanComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerPlanComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerPlanService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new PerPlan(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.perPlans[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
