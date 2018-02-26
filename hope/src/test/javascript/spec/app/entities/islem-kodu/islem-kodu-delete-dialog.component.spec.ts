/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { IslemKoduDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu-delete-dialog.component';
import { IslemKoduService } from '../../../../../../main/webapp/app/entities/islem-kodu/islem-kodu.service';

describe('Component Tests', () => {

    describe('IslemKodu Management Delete Component', () => {
        let comp: IslemKoduDeleteDialogComponent;
        let fixture: ComponentFixture<IslemKoduDeleteDialogComponent>;
        let service: IslemKoduService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [IslemKoduDeleteDialogComponent],
                providers: [
                    IslemKoduService
                ]
            })
            .overrideTemplate(IslemKoduDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IslemKoduDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IslemKoduService);
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
