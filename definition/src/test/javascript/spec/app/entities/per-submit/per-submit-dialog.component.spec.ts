/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { PerSubmitDialogComponent } from '../../../../../../main/webapp/app/entities/per-submit/per-submit-dialog.component';
import { PerSubmitService } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.service';
import { PerSubmit } from '../../../../../../main/webapp/app/entities/per-submit/per-submit.model';
import { PerPersonService } from '../../../../../../main/webapp/app/entities/per-person';
import { DefItemService } from '../../../../../../main/webapp/app/entities/def-item';
import { PerExcuseService } from '../../../../../../main/webapp/app/entities/per-excuse';

describe('Component Tests', () => {

    describe('PerSubmit Management Dialog Component', () => {
        let comp: PerSubmitDialogComponent;
        let fixture: ComponentFixture<PerSubmitDialogComponent>;
        let service: PerSubmitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerSubmitDialogComponent],
                providers: [
                    PerPersonService,
                    DefItemService,
                    PerExcuseService,
                    PerSubmitService
                ]
            })
            .overrideTemplate(PerSubmitDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerSubmitDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerSubmitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PerSubmit(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.perSubmit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'perSubmitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PerSubmit();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.perSubmit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'perSubmitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
