/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { PerPersonDialogComponent } from '../../../../../../main/webapp/app/entities/per-person/per-person-dialog.component';
import { PerPersonService } from '../../../../../../main/webapp/app/entities/per-person/per-person.service';
import { PerPerson } from '../../../../../../main/webapp/app/entities/per-person/per-person.model';
import { PerCompanyService } from '../../../../../../main/webapp/app/entities/per-company';
import { DefItemService } from '../../../../../../main/webapp/app/entities/def-item';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('PerPerson Management Dialog Component', () => {
        let comp: PerPersonDialogComponent;
        let fixture: ComponentFixture<PerPersonDialogComponent>;
        let service: PerPersonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerPersonDialogComponent],
                providers: [
                    PerCompanyService,
                    DefItemService,
                    UserService,
                    PerPersonService
                ]
            })
            .overrideTemplate(PerPersonDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerPersonDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerPersonService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PerPerson(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.perPerson = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'perPersonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PerPerson();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.perPerson = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'perPersonListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
