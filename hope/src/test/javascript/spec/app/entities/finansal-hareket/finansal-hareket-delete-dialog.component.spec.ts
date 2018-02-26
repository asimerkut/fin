/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket-delete-dialog.component';
import { FinansalHareketService } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.service';

describe('Component Tests', () => {

    describe('FinansalHareket Management Delete Component', () => {
        let comp: FinansalHareketDeleteDialogComponent;
        let fixture: ComponentFixture<FinansalHareketDeleteDialogComponent>;
        let service: FinansalHareketService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDeleteDialogComponent],
                providers: [
                    FinansalHareketService
                ]
            })
            .overrideTemplate(FinansalHareketDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketService);
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
