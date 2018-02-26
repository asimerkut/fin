import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaTipi } from './dosya-tipi.model';
import { DosyaTipiPopupService } from './dosya-tipi-popup.service';
import { DosyaTipiService } from './dosya-tipi.service';

@Component({
    selector: 'jhi-dosya-tipi-dialog',
    templateUrl: './dosya-tipi-dialog.component.html'
})
export class DosyaTipiDialogComponent implements OnInit {

    dosyaTipi: DosyaTipi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dosyaTipiService: DosyaTipiService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.dosyaTipi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.dosyaTipiService.update(this.dosyaTipi));
        } else {
            this.subscribeToSaveResponse(
                this.dosyaTipiService.create(this.dosyaTipi));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DosyaTipi>>) {
        result.subscribe((res: HttpResponse<DosyaTipi>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DosyaTipi) {
        this.eventManager.broadcast({ name: 'dosyaTipiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-dosya-tipi-popup',
    template: ''
})
export class DosyaTipiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaTipiPopupService: DosyaTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.dosyaTipiPopupService
                    .open(DosyaTipiDialogComponent as Component, params['id']);
            } else {
                this.dosyaTipiPopupService
                    .open(DosyaTipiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
