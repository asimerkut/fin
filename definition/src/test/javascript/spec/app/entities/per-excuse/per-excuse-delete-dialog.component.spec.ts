/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { PerExcuseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse-delete-dialog.component';
import { PerExcuseService } from '../../../../../../main/webapp/app/entities/per-excuse/per-excuse.service';

describe('Component Tests', () => {

    describe('PerExcuse Management Delete Component', () => {
        let comp: PerExcuseDeleteDialogComponent;
        let fixture: ComponentFixture<PerExcuseDeleteDialogComponent>;
        let service: PerExcuseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [PerExcuseDeleteDialogComponent],
                providers: [
                    PerExcuseService
                ]
            })
            .overrideTemplate(PerExcuseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PerExcuseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PerExcuseService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
