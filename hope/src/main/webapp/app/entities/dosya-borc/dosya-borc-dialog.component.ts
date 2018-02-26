import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DosyaBorc } from './dosya-borc.model';
import { DosyaBorcPopupService } from './dosya-borc-popup.service';
import { DosyaBorcService } from './dosya-borc.service';
import { Dosya, DosyaService } from '../dosya';
import { BorcGrubu, BorcGrubuService } from '../borc-grubu';

@Component({
    selector: 'jhi-dosya-borc-dialog',
    templateUrl: './dosya-borc-dialog.component.html'
})
export class DosyaBorcDialogComponent implements OnInit {

    dosyaBorc: DosyaBorc;
    isSaving: boolean;

    dosyas: Dosya[];

    borcgrubus: BorcGrubu[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dosyaBorcService: DosyaBorcService,
        private dosyaService: DosyaService,
        private borcGrubuService: BorcGrubuService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dosyaService.query()
            .subscribe((res: HttpResponse<Dosya[]>) => { this.dosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.borcGrubuService.query()
            .subscribe((res: HttpResponse<BorcGrubu[]>) => { this.borcgrubus = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dosyaBorc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dosyaBorcService.update(this.dosyaBorc));
        } else {
            this.subscribeToSaveResponse(
                this.dosyaBorcService.create(this.dosyaBorc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DosyaBorc>>) {
        result.subscribe((res: HttpResponse<DosyaBorc>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DosyaBorc) {
        this.eventManager.broadcast({ name: 'dosyaBorcListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDosyaById(index: number, item: Dosya) {
        return item.id;
    }

    trackBorcGrubuById(index: number, item: BorcGrubu) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dosya-borc-popup',
    template: ''
})
export class DosyaBorcPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaBorcPopupService: DosyaBorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dosyaBorcPopupService
                    .open(DosyaBorcDialogComponent as Component, params['id']);
            } else {
                this.dosyaBorcPopupService
                    .open(DosyaBorcDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
