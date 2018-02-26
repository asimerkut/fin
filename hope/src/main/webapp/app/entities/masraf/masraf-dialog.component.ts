import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Masraf } from './masraf.model';
import { MasrafPopupService } from './masraf-popup.service';
import { MasrafService } from './masraf.service';
import { Dosya, DosyaService } from '../dosya';
import { MasrafTipi, MasrafTipiService } from '../masraf-tipi';

@Component({
    selector: 'jhi-masraf-dialog',
    templateUrl: './masraf-dialog.component.html'
})
export class MasrafDialogComponent implements OnInit {

    masraf: Masraf;
    isSaving: boolean;

    dosyas: Dosya[];

    masraftipis: MasrafTipi[];
    masrafTarihiDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private masrafService: MasrafService,
        private dosyaService: DosyaService,
        private masrafTipiService: MasrafTipiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.dosyaService.query()
            .subscribe((res: HttpResponse<Dosya[]>) => { this.dosyas = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.masrafTipiService.query()
            .subscribe((res: HttpResponse<MasrafTipi[]>) => { this.masraftipis = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.masraf.id !== undefined) {
            this.subscribeToSaveResponse(
                this.masrafService.update(this.masraf));
        } else {
            this.subscribeToSaveResponse(
                this.masrafService.create(this.masraf));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Masraf>>) {
        result.subscribe((res: HttpResponse<Masraf>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Masraf) {
        this.eventManager.broadcast({ name: 'masrafListModification', content: 'OK'});
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

    trackMasrafTipiById(index: number, item: MasrafTipi) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-masraf-popup',
    template: ''
})
export class MasrafPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private masrafPopupService: MasrafPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.masrafPopupService
                    .open(MasrafDialogComponent as Component, params['id']);
            } else {
                this.masrafPopupService
                    .open(MasrafDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
