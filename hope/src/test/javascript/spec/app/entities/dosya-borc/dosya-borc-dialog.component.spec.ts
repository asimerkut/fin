/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcDialogComponent } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc-dialog.component';
import { DosyaBorcService } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.service';
import { DosyaBorc } from '../../../../../../main/webapp/app/entities/dosya-borc/dosya-borc.model';
import { DosyaService } from '../../../../../../main/webapp/app/entities/dosya';
import { BorcGrubuService } from '../../../../../../main/webapp/app/entities/borc-grubu';

describe('Component Tests', () => {

    describe('DosyaBorc Management Dialog Component', () => {
        let comp: DosyaBorcDialogComponent;
        let fixture: ComponentFixture<DosyaBorcDialogComponent>;
        let service: DosyaBorcService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcDialogComponent],
                providers: [
                    DosyaService,
                    BorcGrubuService,
                    DosyaBorcService
                ]
            })
            .overrideTemplate(DosyaBorcDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DosyaBorc(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dosyaBorc = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dosyaBorcListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DosyaBorc();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dosyaBorc = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dosyaBorcListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
