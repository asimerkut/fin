import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDosyaBorc } from './hop-dosya-borc.model';
import { HopDosyaBorcPopupService } from './hop-dosya-borc-popup.service';
import { HopDosyaBorcService } from './hop-dosya-borc.service';
import { HopDosya, HopDosyaService } from '../hop-dosya';

@Component({
    selector: 'jhi-hop-dosya-borc-dialog',
    templateUrl: './hop-dosya-borc-dialog.component.html'
})
export class HopDosyaBorcDialogComponent implements OnInit {

    hopDosyaBorc: HopDosyaBorc;
    isSaving: boolean;

    hopdosyas: HopDosya[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopDosyaBorcService: HopDosyaBorcService,
        private hopDosyaService: HopDosyaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopDosyaService.query()
            .subscribe((res: HttpResponse<HopDosya[]>) => { this.hopdosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hopDosyaBorc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopDosyaBorcService.update(this.hopDosyaBorc));
        } else {
            this.subscribeToSaveResponse(
                this.hopDosyaBorcService.create(this.hopDosyaBorc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopDosyaBorc>>) {
        result.subscribe((res: HttpResponse<HopDosyaBorc>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopDosyaBorc) {
        this.eventManager.broadcast({ name: 'hopDosyaBorcListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackHopDosyaById(index: number, item: HopDosya) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hop-dosya-borc-popup',
    template: ''
})
export class HopDosyaBorcPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDosyaBorcPopupService: HopDosyaBorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopDosyaBorcPopupService
                    .open(HopDosyaBorcDialogComponent as Component, params['id']);
            } else {
                this.hopDosyaBorcPopupService
                    .open(HopDosyaBorcDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
