import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopBorc } from './hop-borc.model';
import { HopBorcPopupService } from './hop-borc-popup.service';
import { HopBorcService } from './hop-borc.service';
import { HopDosya, HopDosyaService } from '../hop-dosya';

@Component({
    selector: 'jhi-hop-borc-dialog',
    templateUrl: './hop-borc-dialog.component.html'
})
export class HopBorcDialogComponent implements OnInit {

    hopBorc: HopBorc;
    isSaving: boolean;

    hopdosyas: HopDosya[];
    tarihDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopBorcService: HopBorcService,
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
        if (this.hopBorc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopBorcService.update(this.hopBorc));
        } else {
            this.subscribeToSaveResponse(
                this.hopBorcService.create(this.hopBorc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopBorc>>) {
        result.subscribe((res: HttpResponse<HopBorc>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopBorc) {
        this.eventManager.broadcast({ name: 'hopBorcListModification', content: 'OK'});
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
    selector: 'jhi-hop-borc-popup',
    template: ''
})
export class HopBorcPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopBorcPopupService: HopBorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopBorcPopupService
                    .open(HopBorcDialogComponent as Component, params['id']);
            } else {
                this.hopBorcPopupService
                    .open(HopBorcDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
