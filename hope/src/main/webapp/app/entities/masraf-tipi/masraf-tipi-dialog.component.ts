import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MasrafTipi } from './masraf-tipi.model';
import { MasrafTipiPopupService } from './masraf-tipi-popup.service';
import { MasrafTipiService } from './masraf-tipi.service';

@Component({
    selector: 'jhi-masraf-tipi-dialog',
    templateUrl: './masraf-tipi-dialog.component.html'
})
export class MasrafTipiDialogComponent implements OnInit {

    masrafTipi: MasrafTipi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private masrafTipiService: MasrafTipiService,
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
        if (this.masrafTipi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.masrafTipiService.update(this.masrafTipi));
        } else {
            this.subscribeToSaveResponse(
                this.masrafTipiService.create(this.masrafTipi));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<MasrafTipi>>) {
        result.subscribe((res: HttpResponse<MasrafTipi>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: MasrafTipi) {
        this.eventManager.broadcast({ name: 'masrafTipiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-masraf-tipi-popup',
    template: ''
})
export class MasrafTipiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private masrafTipiPopupService: MasrafTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.masrafTipiPopupService
                    .open(MasrafTipiDialogComponent as Component, params['id']);
            } else {
                this.masrafTipiPopupService
                    .open(MasrafTipiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
