/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketDetayDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay-delete-dialog.component';
import { HopFinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.service';

describe('Component Tests', () => {

    describe('HopFinansalHareketDetay Management Delete Component', () => {
        let comp: HopFinansalHareketDetayDeleteDialogComponent;
        let fixture: ComponentFixture<HopFinansalHareketDetayDeleteDialogComponent>;
        let service: HopFinansalHareketDetayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketDetayDeleteDialogComponent],
                providers: [
                    HopFinansalHareketDetayService
                ]
            })
            .overrideTemplate(HopFinansalHareketDetayDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketDetayDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketDetayService);
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
