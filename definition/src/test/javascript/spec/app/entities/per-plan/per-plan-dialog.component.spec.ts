/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { PerPlanDialogComponent } from '../../../../../../main/webapp/app/entities/per-plan/per-plan-dialog.component';
import { PerPlanService } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.service';
import { PerPlan } from '../../../../../../main/webapp/app/entities/per-plan/per-plan.model';
import { PerPersonService } from '../../../../../../main/webapp/app/entities/per-person';
import { DefItemService } from '../../../../../../main/webapp/app/entities/def-item';

describe('Component Tests', () => {

    describe('PerPlan Management Dialog Component', () => {
        let comp: PerPlanDialogComponent;
        let fixture: ComponentFixture<PerPlanDialogComponent>;
        let service: PerPlanService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerPlanDialogComponent],
                providers: [
                    PerPersonService,
                    DefItemService,
                    PerPlanService
                ]
            })
            .overrideTemplate(PerPlanDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerPlanDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerPlanService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PerPlan(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.perPlan = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'perPlanListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PerPlan();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.perPlan = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'perPlanListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
