import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDosya } from './hop-dosya.model';
import { HopDosyaPopupService } from './hop-dosya-popup.service';
import { HopDosyaService } from './hop-dosya.service';

@Component({
    selector: 'jhi-hop-dosya-dialog',
    templateUrl: './hop-dosya-dialog.component.html'
})
export class HopDosyaDialogComponent implements OnInit {

    hopDosya: HopDosya;
    isSaving: boolean;

    hopdosyas: HopDosya[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
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
        if (this.hopDosya.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopDosyaService.update(this.hopDosya));
        } else {
            this.subscribeToSaveResponse(
                this.hopDosyaService.create(this.hopDosya));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopDosya>>) {
        result.subscribe((res: HttpResponse<HopDosya>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopDosya) {
        this.eventManager.broadcast({ name: 'hopDosyaListModification', content: 'OK'});
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
    selector: 'jhi-hop-dosya-popup',
    template: ''
})
export class HopDosyaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDosyaPopupService: HopDosyaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopDosyaPopupService
                    .open(HopDosyaDialogComponent as Component, params['id']);
            } else {
                this.hopDosyaPopupService
                    .open(HopDosyaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
