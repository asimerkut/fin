import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DosyaBorcKalem } from './dosya-borc-kalem.model';
import { DosyaBorcKalemPopupService } from './dosya-borc-kalem-popup.service';
import { DosyaBorcKalemService } from './dosya-borc-kalem.service';
import { DosyaBorc, DosyaBorcService } from '../dosya-borc';
import { BorcKalem, BorcKalemService } from '../borc-kalem';
import { Borc, BorcService } from '../borc';
import { Masraf, MasrafService } from '../masraf';

@Component({
    selector: 'jhi-dosya-borc-kalem-dialog',
    templateUrl: './dosya-borc-kalem-dialog.component.html'
})
export class DosyaBorcKalemDialogComponent implements OnInit {

    dosyaBorcKalem: DosyaBorcKalem;
    isSaving: boolean;

    dosyaborcs: DosyaBorc[];

    borckalems: BorcKalem[];

    borcs: Borc[];

    masrafs: Masraf[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dosyaBorcKalemService: DosyaBorcKalemService,
        private dosyaBorcService: DosyaBorcService,
        private borcKalemService: BorcKalemService,
        private borcService: BorcService,
        private masrafService: MasrafService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dosyaBorcService.query()
            .subscribe((res: HttpResponse<DosyaBorc[]>) => { this.dosyaborcs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.borcKalemService.query()
            .subscribe((res: HttpResponse<BorcKalem[]>) => { this.borckalems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.borcService.query()
            .subscribe((res: HttpResponse<Borc[]>) => { this.borcs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.masrafService.query()
            .subscribe((res: HttpResponse<Masraf[]>) => { this.masrafs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dosyaBorcKalem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dosyaBorcKalemService.update(this.dosyaBorcKalem));
        } else {
            this.subscribeToSaveResponse(
                this.dosyaBorcKalemService.create(this.dosyaBorcKalem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DosyaBorcKalem>>) {
        result.subscribe((res: HttpResponse<DosyaBorcKalem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DosyaBorcKalem) {
        this.eventManager.broadcast({ name: 'dosyaBorcKalemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDosyaBorcById(index: number, item: DosyaBorc) {
        return item.id;
    }

    trackBorcKalemById(index: number, item: BorcKalem) {
        return item.id;
    }

    trackBorcById(index: number, item: Borc) {
        return item.id;
    }

    trackMasrafById(index: number, item: Masraf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dosya-borc-kalem-popup',
    template: ''
})
export class DosyaBorcKalemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaBorcKalemPopupService: DosyaBorcKalemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dosyaBorcKalemPopupService
                    .open(DosyaBorcKalemDialogComponent as Component, params['id']);
            } else {
                this.dosyaBorcKalemPopupService
                    .open(DosyaBorcKalemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
