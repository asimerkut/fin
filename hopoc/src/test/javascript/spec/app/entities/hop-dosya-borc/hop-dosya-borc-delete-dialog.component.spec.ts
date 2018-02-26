/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc-delete-dialog.component';
import { HopDosyaBorcService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc/hop-dosya-borc.service';

describe('Component Tests', () => {

    describe('HopDosyaBorc Management Delete Component', () => {
        let comp: HopDosyaBorcDeleteDialogComponent;
        let fixture: ComponentFixture<HopDosyaBorcDeleteDialogComponent>;
        let service: HopDosyaBorcService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcDeleteDialogComponent],
                providers: [
                    HopDosyaBorcService
                ]
            })
            .overrideTemplate(HopDosyaBorcDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcService);
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
