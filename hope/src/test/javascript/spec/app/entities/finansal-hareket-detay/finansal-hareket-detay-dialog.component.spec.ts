/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDetayDialogComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay-dialog.component';
import { FinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.service';
import { FinansalHareketDetay } from '../../../../../../main/webapp/app/entities/finansal-hareket-detay/finansal-hareket-detay.model';
import { FinansalHareketService } from '../../../../../../main/webapp/app/entities/finansal-hareket';
import { DosyaBorcService } from '../../../../../../main/webapp/app/entities/dosya-borc';
import { DosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem';

describe('Component Tests', () => {

    describe('FinansalHareketDetay Management Dialog Component', () => {
        let comp: FinansalHareketDetayDialogComponent;
        let fixture: ComponentFixture<FinansalHareketDetayDialogComponent>;
        let service: FinansalHareketDetayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDetayDialogComponent],
                providers: [
                    FinansalHareketService,
                    DosyaBorcService,
                    DosyaBorcKalemService,
                    FinansalHareketDetayService
                ]
            })
            .overrideTemplate(FinansalHareketDetayDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDetayDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketDetayService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FinansalHareketDetay(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.finansalHareketDetay = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'finansalHareketDetayListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FinansalHareketDetay();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.finansalHareketDetay = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'finansalHareketDetayListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
