import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IslemKodu } from './islem-kodu.model';
import { IslemKoduPopupService } from './islem-kodu-popup.service';
import { IslemKoduService } from './islem-kodu.service';

@Component({
    selector: 'jhi-islem-kodu-dialog',
    templateUrl: './islem-kodu-dialog.component.html'
})
export class IslemKoduDialogComponent implements OnInit {

    islemKodu: IslemKodu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private islemKoduService: IslemKoduService,
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
        if (this.islemKodu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.islemKoduService.update(this.islemKodu));
        } else {
            this.subscribeToSaveResponse(
                this.islemKoduService.create(this.islemKodu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IslemKodu>>) {
        result.subscribe((res: HttpResponse<IslemKodu>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: IslemKodu) {
        this.eventManager.broadcast({ name: 'islemKoduListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-islem-kodu-popup',
    template: ''
})
export class IslemKoduPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private islemKoduPopupService: IslemKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.islemKoduPopupService
                    .open(IslemKoduDialogComponent as Component, params['id']);
            } else {
                this.islemKoduPopupService
                    .open(IslemKoduDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
