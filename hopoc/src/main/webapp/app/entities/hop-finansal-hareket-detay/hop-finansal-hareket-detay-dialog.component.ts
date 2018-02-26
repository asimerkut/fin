import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopFinansalHareketDetay } from './hop-finansal-hareket-detay.model';
import { HopFinansalHareketDetayPopupService } from './hop-finansal-hareket-detay-popup.service';
import { HopFinansalHareketDetayService } from './hop-finansal-hareket-detay.service';
import { HopFinansalHareket, HopFinansalHareketService } from '../hop-finansal-hareket';
import { HopDosyaBorc, HopDosyaBorcService } from '../hop-dosya-borc';
import { HopDosyaBorcKalem, HopDosyaBorcKalemService } from '../hop-dosya-borc-kalem';

@Component({
    selector: 'jhi-hop-finansal-hareket-detay-dialog',
    templateUrl: './hop-finansal-hareket-detay-dialog.component.html'
})
export class HopFinansalHareketDetayDialogComponent implements OnInit {

    hopFinansalHareketDetay: HopFinansalHareketDetay;
    isSaving: boolean;

    hopfinansalharekets: HopFinansalHareket[];

    hopdosyaborcs: HopDosyaBorc[];

    hopdosyaborckalems: HopDosyaBorcKalem[];

    hopfinansalhareketdetays: HopFinansalHareketDetay[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopFinansalHareketDetayService: HopFinansalHareketDetayService,
        private hopFinansalHareketService: HopFinansalHareketService,
        private hopDosyaBorcService: HopDosyaBorcService,
        private hopDosyaBorcKalemService: HopDosyaBorcKalemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopFinansalHareketService.query()
            .subscribe((res: HttpResponse<HopFinansalHareket[]>) => { this.hopfinansalharekets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopDosyaBorcService.query()
            .subscribe((res: HttpResponse<HopDosyaBorc[]>) => { this.hopdosyaborcs = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopDosyaBorcKalemService.query()
            .subscribe((res: HttpResponse<HopDosyaBorcKalem[]>) => { this.hopdosyaborckalems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopFinansalHareketDetayService.query()
            .subscribe((res: HttpResponse<HopFinansalHareketDetay[]>) => { this.hopfinansalhareketdetays = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hopFinansalHareketDetay.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopFinansalHareketDetayService.update(this.hopFinansalHareketDetay));
        } else {
            this.subscribeToSaveResponse(
                this.hopFinansalHareketDetayService.create(this.hopFinansalHareketDetay));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopFinansalHareketDetay>>) {
        result.subscribe((res: HttpResponse<HopFinansalHareketDetay>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopFinansalHareketDetay) {
        this.eventManager.broadcast({ name: 'hopFinansalHareketDetayListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackHopFinansalHareketById(index: number, item: HopFinansalHareket) {
        return item.id;
    }

    trackHopDosyaBorcById(index: number, item: HopDosyaBorc) {
        return item.id;
    }

    trackHopDosyaBorcKalemById(index: number, item: HopDosyaBorcKalem) {
        return item.id;
    }

    trackHopFinansalHareketDetayById(index: number, item: HopFinansalHareketDetay) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hop-finansal-hareket-detay-popup',
    template: ''
})
export class HopFinansalHareketDetayPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopFinansalHareketDetayPopupService: HopFinansalHareketDetayPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopFinansalHareketDetayPopupService
                    .open(HopFinansalHareketDetayDialogComponent as Component, params['id']);
            } else {
                this.hopFinansalHareketDetayPopupService
                    .open(HopFinansalHareketDetayDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
