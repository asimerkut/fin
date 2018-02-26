/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { FinansalHareketDialogComponent } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket-dialog.component';
import { FinansalHareketService } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.service';
import { FinansalHareket } from '../../../../../../main/webapp/app/entities/finansal-hareket/finansal-hareket.model';
import { DosyaService } from '../../../../../../main/webapp/app/entities/dosya';
import { IslemKoduService } from '../../../../../../main/webapp/app/entities/islem-kodu';

describe('Component Tests', () => {

    describe('FinansalHareket Management Dialog Component', () => {
        let comp: FinansalHareketDialogComponent;
        let fixture: ComponentFixture<FinansalHareketDialogComponent>;
        let service: FinansalHareketService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [FinansalHareketDialogComponent],
                providers: [
                    DosyaService,
                    IslemKoduService,
                    FinansalHareketService
                ]
            })
            .overrideTemplate(FinansalHareketDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FinansalHareketDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FinansalHareketService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FinansalHareket(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.finansalHareket = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'finansalHareketListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new FinansalHareket();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.finansalHareket = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'finansalHareketListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
