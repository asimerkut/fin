import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BorcTipi } from './borc-tipi.model';
import { BorcTipiPopupService } from './borc-tipi-popup.service';
import { BorcTipiService } from './borc-tipi.service';

@Component({
    selector: 'jhi-borc-tipi-dialog',
    templateUrl: './borc-tipi-dialog.component.html'
})
export class BorcTipiDialogComponent implements OnInit {

    borcTipi: BorcTipi;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private borcTipiService: BorcTipiService,
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
        if (this.borcTipi.id !== undefined) {
            this.subscribeToSaveResponse(
                this.borcTipiService.update(this.borcTipi));
        } else {
            this.subscribeToSaveResponse(
                this.borcTipiService.create(this.borcTipi));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BorcTipi>>) {
        result.subscribe((res: HttpResponse<BorcTipi>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BorcTipi) {
        this.eventManager.broadcast({ name: 'borcTipiListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-borc-tipi-popup',
    template: ''
})
export class BorcTipiPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcTipiPopupService: BorcTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.borcTipiPopupService
                    .open(BorcTipiDialogComponent as Component, params['id']);
            } else {
                this.borcTipiPopupService
                    .open(BorcTipiDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
