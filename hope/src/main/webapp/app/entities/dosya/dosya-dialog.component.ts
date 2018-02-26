import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Dosya } from './dosya.model';
import { DosyaPopupService } from './dosya-popup.service';
import { DosyaService } from './dosya.service';
import { DosyaTipi, DosyaTipiService } from '../dosya-tipi';

@Component({
    selector: 'jhi-dosya-dialog',
    templateUrl: './dosya-dialog.component.html'
})
export class DosyaDialogComponent implements OnInit {

    dosya: Dosya;
    isSaving: boolean;

    dosyas: Dosya[];

    dosyatipis: DosyaTipi[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private dosyaService: DosyaService,
        private dosyaTipiService: DosyaTipiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dosyaService.query()
            .subscribe((res: HttpResponse<Dosya[]>) => { this.dosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.dosyaTipiService.query()
            .subscribe((res: HttpResponse<DosyaTipi[]>) => { this.dosyatipis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dosya.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dosyaService.update(this.dosya));
        } else {
            this.subscribeToSaveResponse(
                this.dosyaService.create(this.dosya));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Dosya>>) {
        result.subscribe((res: HttpResponse<Dosya>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Dosya) {
        this.eventManager.broadcast({ name: 'dosyaListModification', content: 'OK'});
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

    trackDosyaTipiById(index: number, item: DosyaTipi) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-dosya-popup',
    template: ''
})
export class DosyaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaPopupService: DosyaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dosyaPopupService
                    .open(DosyaDialogComponent as Component, params['id']);
            } else {
                this.dosyaPopupService
                    .open(DosyaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
