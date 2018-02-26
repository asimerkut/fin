/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcKalemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem-delete-dialog.component';
import { HopDosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.service';

describe('Component Tests', () => {

    describe('HopDosyaBorcKalem Management Delete Component', () => {
        let comp: HopDosyaBorcKalemDeleteDialogComponent;
        let fixture: ComponentFixture<HopDosyaBorcKalemDeleteDialogComponent>;
        let service: HopDosyaBorcKalemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcKalemDeleteDialogComponent],
                providers: [
                    HopDosyaBorcKalemService
                ]
            })
            .overrideTemplate(HopDosyaBorcKalemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcKalemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcKalemService);
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
