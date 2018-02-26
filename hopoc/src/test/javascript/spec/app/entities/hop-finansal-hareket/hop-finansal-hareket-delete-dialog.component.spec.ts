/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket-delete-dialog.component';
import { HopFinansalHareketService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket/hop-finansal-hareket.service';

describe('Component Tests', () => {

    describe('HopFinansalHareket Management Delete Component', () => {
        let comp: HopFinansalHareketDeleteDialogComponent;
        let fixture: ComponentFixture<HopFinansalHareketDeleteDialogComponent>;
        let service: HopFinansalHareketService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketDeleteDialogComponent],
                providers: [
                    HopFinansalHareketService
                ]
            })
            .overrideTemplate(HopFinansalHareketDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketService);
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
