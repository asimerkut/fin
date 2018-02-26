/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FinTestModule } from '../../../test.module';
import { DosyaBorcKalemDialogComponent } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem-dialog.component';
import { DosyaBorcKalemService } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.service';
import { DosyaBorcKalem } from '../../../../../../main/webapp/app/entities/dosya-borc-kalem/dosya-borc-kalem.model';
import { DosyaBorcService } from '../../../../../../main/webapp/app/entities/dosya-borc';
import { BorcKalemService } from '../../../../../../main/webapp/app/entities/borc-kalem';
import { BorcService } from '../../../../../../main/webapp/app/entities/borc';
import { MasrafService } from '../../../../../../main/webapp/app/entities/masraf';

describe('Component Tests', () => {

    describe('DosyaBorcKalem Management Dialog Component', () => {
        let comp: DosyaBorcKalemDialogComponent;
        let fixture: ComponentFixture<DosyaBorcKalemDialogComponent>;
        let service: DosyaBorcKalemService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FinTestModule],
                declarations: [DosyaBorcKalemDialogComponent],
                providers: [
                    DosyaBorcService,
                    BorcKalemService,
                    BorcService,
                    MasrafService,
                    DosyaBorcKalemService
                ]
            })
            .overrideTemplate(DosyaBorcKalemDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DosyaBorcKalemDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DosyaBorcKalemService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DosyaBorcKalem(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dosyaBorcKalem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dosyaBorcKalemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DosyaBorcKalem();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.dosyaBorcKalem = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'dosyaBorcKalemListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
