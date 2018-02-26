/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcKalemDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem-delete-dialog.component';
import { DosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.service';

describe('Component Tests', () => {

    describe('DosyaBorcKalem Management Delete Component', () => {
        let comp: DosyaBorcKalemDeleteDialogComponent;
        let fixture: ComponentFixture<DosyaBorcKalemDeleteDialogComponent>;
        let service: DosyaBorcKalemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcKalemDeleteDialogComponent],
                providers: [
                    DosyaBorcKalemService
                ]
            })
            .overrideTemplate(DosyaBorcKalemDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcKalemDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcKalemService);
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
