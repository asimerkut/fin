import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDosyaBorcKalem } from './hop-dosya-borc-kalem.model';
import { HopDosyaBorcKalemPopupService } from './hop-dosya-borc-kalem-popup.service';
import { HopDosyaBorcKalemService } from './hop-dosya-borc-kalem.service';
import { HopDosyaBorc, HopDosyaBorcService } from '../hop-dosya-borc';
import { HopBorc, HopBorcService } from '../hop-borc';
import { HopMasraf, HopMasrafService } from '../hop-masraf';

@Component({
    selector: 'jhi-hop-dosya-borc-kalem-dialog',
    templateUrl: './hop-dosya-borc-kalem-dialog.component.html'
})
export class HopDosyaBorcKalemDialogComponent implements OnInit {

    hopDosyaBorcKalem: HopDosyaBorcKalem;
    isSaving: boolean;

    hopdosyaborcs: HopDosyaBorc[];

    hopborcs: HopBorc[];

    hopmasrafs: HopMasraf[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopDosyaBorcKalemService: HopDosyaBorcKalemService,
        private hopDosyaBorcService: HopDosyaBorcService,
        private hopBorcService: HopBorcService,
        private hopMasrafService: HopMasrafService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopDosyaBorcService.query()
            .subscribe((res: HttpResponse<HopDosyaBorc[]>) => { this.hopdosyaborcs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopBorcService.query()
            .subscribe((res: HttpResponse<HopBorc[]>) => { this.hopborcs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopMasrafService.query()
            .subscribe((res: HttpResponse<HopMasraf[]>) => { this.hopmasrafs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hopDosyaBorcKalem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopDosyaBorcKalemService.update(this.hopDosyaBorcKalem));
        } else {
            this.subscribeToSaveResponse(
                this.hopDosyaBorcKalemService.create(this.hopDosyaBorcKalem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopDosyaBorcKalem>>) {
        result.subscribe((res: HttpResponse<HopDosyaBorcKalem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopDosyaBorcKalem) {
        this.eventManager.broadcast({ name: 'hopDosyaBorcKalemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackHopDosyaBorcById(index: number, item: HopDosyaBorc) {
        return item.id;
    }

    trackHopBorcById(index: number, item: HopBorc) {
        return item.id;
    }

    trackHopMasrafById(index: number, item: HopMasraf) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hop-dosya-borc-kalem-popup',
    template: ''
})
export class HopDosyaBorcKalemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDosyaBorcKalemPopupService: HopDosyaBorcKalemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopDosyaBorcKalemPopupService
                    .open(HopDosyaBorcKalemDialogComponent as Component, params['id']);
            } else {
                this.hopDosyaBorcKalemPopupService
                    .open(HopDosyaBorcKalemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
