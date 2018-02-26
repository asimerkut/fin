/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { DosyaTipiDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi-delete-dialog.component';
import { DosyaTipiService } from '../../../../../../main/webapp/app/entities/dosya-tipi/dosya-tipi.service';

describe('Component Tests', () => {

    describe('DosyaTipi Management Delete Component', () => {
        let comp: DosyaTipiDeleteDialogComponent;
        let fixture: ComponentFixture<DosyaTipiDeleteDialogComponent>;
        let service: DosyaTipiService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaTipiDeleteDialogComponent],
                providers: [
                    DosyaTipiService
                ]
            })
            .overrideTemplate(DosyaTipiDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaTipiDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaTipiService);
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
