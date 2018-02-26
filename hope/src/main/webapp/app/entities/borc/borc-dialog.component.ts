import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Borc } from './borc.model';
import { BorcPopupService } from './borc-popup.service';
import { BorcService } from './borc.service';
import { Dosya, DosyaService } from '../dosya';
import { BorcTipi, BorcTipiService } from '../borc-tipi';

@Component({
    selector: 'jhi-borc-dialog',
    templateUrl: './borc-dialog.component.html'
})
export class BorcDialogComponent implements OnInit {

    borc: Borc;
    isSaving: boolean;

    dosyas: Dosya[];

    borctipis: BorcTipi[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private borcService: BorcService,
        private dosyaService: DosyaService,
        private borcTipiService: BorcTipiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dosyaService.query()
            .subscribe((res: HttpResponse<Dosya[]>) => { this.dosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.borcTipiService.query()
            .subscribe((res: HttpResponse<BorcTipi[]>) => { this.borctipis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.borc.id !== undefined) {
            this.subscribeToSaveResponse(
                this.borcService.update(this.borc));
        } else {
            this.subscribeToSaveResponse(
                this.borcService.create(this.borc));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Borc>>) {
        result.subscribe((res: HttpResponse<Borc>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Borc) {
        this.eventManager.broadcast({ name: 'borcListModification', content: 'OK'});
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

    trackBorcTipiById(index: number, item: BorcTipi) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-borc-popup',
    template: ''
})
export class BorcPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcPopupService: BorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.borcPopupService
                    .open(BorcDialogComponent as Component, params['id']);
            } else {
                this.borcPopupService
                    .open(BorcDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
