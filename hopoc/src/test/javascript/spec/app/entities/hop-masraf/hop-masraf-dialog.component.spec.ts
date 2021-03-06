/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { HopMasrafDialogComponent } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf-dialog.component';
import { HopMasrafService } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.service';
import { HopMasraf } from '../../../../../../main/webapp/app/entities/hop-masraf/hop-masraf.model';
import { HopDosyaService } from '../../../../../../main/webapp/app/entities/hop-dosya';

describe('Component Tests', () => {

    describe('HopMasraf Management Dialog Component', () => {
        let comp: HopMasrafDialogComponent;
        let fixture: ComponentFixture<HopMasrafDialogComponent>;
        let service: HopMasrafService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [HopMasrafDialogComponent],
                providers: [
                    HopDosyaService,
                    HopMasrafService
                ]
            })
            .overrideTemplate(HopMasrafDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HopMasrafDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HopMasrafService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopMasraf(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopMasraf = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopMasrafListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new HopMasraf();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.hopMasraf = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'hopMasrafListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
