import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopFinansalHareket } from './hop-finansal-hareket.model';
import { HopFinansalHareketPopupService } from './hop-finansal-hareket-popup.service';
import { HopFinansalHareketService } from './hop-finansal-hareket.service';
import { HopDosya, HopDosyaService } from '../hop-dosya';

@Component({
    selector: 'jhi-hop-finansal-hareket-dialog',
    templateUrl: './hop-finansal-hareket-dialog.component.html'
})
export class HopFinansalHareketDialogComponent implements OnInit {

    hopFinansalHareket: HopFinansalHareket;
    isSaving: boolean;

    hopdosyas: HopDosya[];

    hopfinansalharekets: HopFinansalHareket[];
    tarihDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopFinansalHareketService: HopFinansalHareketService,
        private hopDosyaService: HopDosyaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.hopDosyaService.query()
            .subscribe((res: HttpResponse<HopDosya[]>) => { this.hopdosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.hopFinansalHareketService.query()
            .subscribe((res: HttpResponse<HopFinansalHareket[]>) => { this.hopfinansalharekets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.hopFinansalHareket.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopFinansalHareketService.update(this.hopFinansalHareket));
        } else {
            this.subscribeToSaveResponse(
                this.hopFinansalHareketService.create(this.hopFinansalHareket));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopFinansalHareket>>) {
        result.subscribe((res: HttpResponse<HopFinansalHareket>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopFinansalHareket) {
        this.eventManager.broadcast({ name: 'hopFinansalHareketListModification', content: 'OK'});
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

    trackHopFinansalHareketById(index: number, item: HopFinansalHareket) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-hop-finansal-hareket-popup',
    template: ''
})
export class HopFinansalHareketPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopFinansalHareketPopupService: HopFinansalHareketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopFinansalHareketPopupService
                    .open(HopFinansalHareketDialogComponent as Component, params['id']);
            } else {
                this.hopFinansalHareketPopupService
                    .open(HopFinansalHareketDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
