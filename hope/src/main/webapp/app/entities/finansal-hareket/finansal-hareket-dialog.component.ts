import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FinansalHareket } from './finansal-hareket.model';
import { FinansalHareketPopupService } from './finansal-hareket-popup.service';
import { FinansalHareketService } from './finansal-hareket.service';
import { Dosya, DosyaService } from '../dosya';
import { IslemKodu, IslemKoduService } from '../islem-kodu';

@Component({
    selector: 'jhi-finansal-hareket-dialog',
    templateUrl: './finansal-hareket-dialog.component.html'
})
export class FinansalHareketDialogComponent implements OnInit {

    finansalHareket: FinansalHareket;
    isSaving: boolean;

    dosyas: Dosya[];

    islemkodus: IslemKodu[];

    finansalharekets: FinansalHareket[];
    islemKabulTarihiDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private finansalHareketService: FinansalHareketService,
        private dosyaService: DosyaService,
        private islemKoduService: IslemKoduService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dosyaService.query()
            .subscribe((res: HttpResponse<Dosya[]>) => { this.dosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.islemKoduService.query()
            .subscribe((res: HttpResponse<IslemKodu[]>) => { this.islemkodus = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.finansalHareketService.query()
            .subscribe((res: HttpResponse<FinansalHareket[]>) => { this.finansalharekets = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.finansalHareket.id !== undefined) {
            this.subscribeToSaveResponse(
                this.finansalHareketService.update(this.finansalHareket));
        } else {
            this.subscribeToSaveResponse(
                this.finansalHareketService.create(this.finansalHareket));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FinansalHareket>>) {
        result.subscribe((res: HttpResponse<FinansalHareket>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FinansalHareket) {
        this.eventManager.broadcast({ name: 'finansalHareketListModification', content: 'OK'});
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

    trackIslemKoduById(index: number, item: IslemKodu) {
        return item.id;
    }

    trackFinansalHareketById(index: number, item: FinansalHareket) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-finansal-hareket-popup',
    template: ''
})
export class FinansalHareketPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private finansalHareketPopupService: FinansalHareketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.finansalHareketPopupService
                    .open(FinansalHareketDialogComponent as Component, params['id']);
            } else {
                this.finansalHareketPopupService
                    .open(FinansalHareketDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
