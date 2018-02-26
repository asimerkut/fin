import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopMasraf } from './hop-masraf.model';
import { HopMasrafPopupService } from './hop-masraf-popup.service';
import { HopMasrafService } from './hop-masraf.service';
import { HopDosya, HopDosyaService } from '../hop-dosya';

@Component({
    selector: 'jhi-hop-masraf-dialog',
    templateUrl: './hop-masraf-dialog.component.html'
})
export class HopMasrafDialogComponent implements OnInit {

    hopMasraf: HopMasraf;
    isSaving: boolean;

    hopdosyas: HopDosya[];
    tarihDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private hopMasrafService: HopMasrafService,
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
        if (this.hopMasraf.id !== undefined) {
            this.subscribeToSaveResponse(
                this.hopMasrafService.update(this.hopMasraf));
        } else {
            this.subscribeToSaveResponse(
                this.hopMasrafService.create(this.hopMasraf));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<HopMasraf>>) {
        result.subscribe((res: HttpResponse<HopMasraf>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: HopMasraf) {
        this.eventManager.broadcast({ name: 'hopMasrafListModification', content: 'OK'});
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
    selector: 'jhi-hop-masraf-popup',
    template: ''
})
export class HopMasrafPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopMasrafPopupService: HopMasrafPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.hopMasrafPopupService
                    .open(HopMasrafDialogComponent as Component, params['id']);
            } else {
                this.hopMasrafPopupService
                    .open(HopMasrafDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
