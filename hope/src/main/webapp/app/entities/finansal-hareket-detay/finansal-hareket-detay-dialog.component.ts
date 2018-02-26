import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FinansalHareketDetay } from './finansal-hareket-detay.model';
import { FinansalHareketDetayPopupService } from './finansal-hareket-detay-popup.service';
import { FinansalHareketDetayService } from './finansal-hareket-detay.service';
import { FinansalHareket, FinansalHareketService } from '../finansal-hareket';
import { DosyaBorc, DosyaBorcService } from '../dosya-borc';
import { DosyaBorcKalem, DosyaBorcKalemService } from '../dosya-borc-kalem';

@Component({
    selector: 'jhi-finansal-hareket-detay-dialog',
    templateUrl: './finansal-hareket-detay-dialog.component.html'
})
export class FinansalHareketDetayDialogComponent implements OnInit {

    finansalHareketDetay: FinansalHareketDetay;
    isSaving: boolean;

    finansalharekets: FinansalHareket[];

    dosyaborcs: DosyaBorc[];

    dosyaborckalems: DosyaBorcKalem[];

    finansalhareketdetays: FinansalHareketDetay[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private finansalHareketDetayService: FinansalHareketDetayService,
        private finansalHareketService: FinansalHareketService,
        private dosyaBorcService: DosyaBorcService,
        private dosyaBorcKalemService: DosyaBorcKalemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.finansalHareketService.query()
            .subscribe((res: HttpResponse<FinansalHareket[]>) => { this.finansalharekets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.dosyaBorcService.query()
            .subscribe((res: HttpResponse<DosyaBorc[]>) => { this.dosyaborcs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.dosyaBorcKalemService.query()
            .subscribe((res: HttpResponse<DosyaBorcKalem[]>) => { this.dosyaborckalems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.finansalHareketDetayService.query()
            .subscribe((res: HttpResponse<FinansalHareketDetay[]>) => { this.finansalhareketdetays = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.finansalHareketDetay.id !== undefined) {
            this.subscribeToSaveResponse(
                this.finansalHareketDetayService.update(this.finansalHareketDetay));
        } else {
            this.subscribeToSaveResponse(
                this.finansalHareketDetayService.create(this.finansalHareketDetay));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FinansalHareketDetay>>) {
        result.subscribe((res: HttpResponse<FinansalHareketDetay>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FinansalHareketDetay) {
        this.eventManager.broadcast({ name: 'finansalHareketDetayListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFinansalHareketById(index: number, item: FinansalHareket) {
        return item.id;
    }

    trackDosyaBorcById(index: number, item: DosyaBorc) {
        return item.id;
    }

    trackDosyaBorcKalemById(index: number, item: DosyaBorcKalem) {
        return item.id;
    }

    trackFinansalHareketDetayById(index: number, item: FinansalHareketDetay) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-finansal-hareket-detay-popup',
    template: ''
})
export class FinansalHareketDetayPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private finansalHareketDetayPopupService: FinansalHareketDetayPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.finansalHareketDetayPopupService
                    .open(FinansalHareketDetayDialogComponent as Component, params['id']);
            } else {
                this.finansalHareketDetayPopupService
                    .open(FinansalHareketDetayDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
