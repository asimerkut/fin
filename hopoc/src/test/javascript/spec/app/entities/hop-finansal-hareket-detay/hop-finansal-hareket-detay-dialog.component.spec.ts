/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopFinansalHareketDetayDialogComponent } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay-dialog.component';
import { HopFinansalHareketDetayService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.service';
import { HopFinansalHareketDetay } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket-detay/hop-finansal-hareket-detay.model';
import { HopFinansalHareketService } from '../../../../../../main/webapp/app/entities/hop-finansal-hareket';
import { HopDosyaBorcService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc';
import { HopDosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem';

describe('Component Tests', () => {

    describe('HopFinansalHareketDetay Management Dialog Component', () => {
        let comp: HopFinansalHareketDetayDialogComponent;
        let fixture: ComponentFixture<HopFinansalHareketDetayDialogComponent>;
        let service: HopFinansalHareketDetayService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopFinansalHareketDetayDialogComponent],
                providers: [
                    HopFinansalHareketService,
                    HopDosyaBorcService,
                    HopDosyaBorcKalemService,
                    HopFinansalHareketDetayService
                ]
            })
            .overrideTemplate(HopFinansalHareketDetayDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopFinansalHareketDetayDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopFinansalHareketDetayService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopFinansalHareketDetay(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopFinansalHareketDetay = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopFinansalHareketDetayListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopFinansalHareketDetay();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopFinansalHareketDetay = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopFinansalHareketDetayListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
