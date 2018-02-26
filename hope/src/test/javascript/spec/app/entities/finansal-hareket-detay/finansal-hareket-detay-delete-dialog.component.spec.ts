/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDetayDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay-delete-dialog.component';
import { FinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.service';

describe('Component Tests', () => {

    describe('FinansalHareketDetay Management Delete Component', () => {
        let comp: FinansalHareketDetayDeleteDialogComponent;
        let fixture: ComponentFixture<FinansalHareketDetayDeleteDialogComponent>;
        let service: FinansalHareketDetayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDetayDeleteDialogComponent],
                providers: [
                    FinansalHareketDetayService
                ]
            })
            .overrideTemplate(FinansalHareketDetayDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDetayDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketDetayService);
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
