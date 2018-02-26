/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopDosyaBorcKalemDialogComponent } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem-dialog.component';
import { HopDosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.service';
import { HopDosyaBorcKalem } from '../../../../../../main/webapp/app/entities/hop-dosya-borc-kalem/hop-dosya-borc-kalem.model';
import { HopDosyaBorcService } from '../../../../../../main/webapp/app/entities/hop-dosya-borc';
import { HopBorcService } from '../../../../../../main/webapp/app/entities/hop-borc';
import { HopMasrafService } from '../../../../../../main/webapp/app/entities/hop-masraf';

describe('Component Tests', () => {

    describe('HopDosyaBorcKalem Management Dialog Component', () => {
        let comp: HopDosyaBorcKalemDialogComponent;
        let fixture: ComponentFixture<HopDosyaBorcKalemDialogComponent>;
        let service: HopDosyaBorcKalemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopDosyaBorcKalemDialogComponent],
                providers: [
                    HopDosyaBorcService,
                    HopBorcService,
                    HopMasrafService,
                    HopDosyaBorcKalemService
                ]
            })
            .overrideTemplate(HopDosyaBorcKalemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopDosyaBorcKalemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopDosyaBorcKalemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopDosyaBorcKalem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopDosyaBorcKalem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopDosyaBorcKalemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopDosyaBorcKalem();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopDosyaBorcKalem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopDosyaBorcKalemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
