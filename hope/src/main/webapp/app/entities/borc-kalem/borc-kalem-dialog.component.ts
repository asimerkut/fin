import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BorcKalem } from './borc-kalem.model';
import { BorcKalemPopupService } from './borc-kalem-popup.service';
import { BorcKalemService } from './borc-kalem.service';

@Component({
    selector: 'jhi-borc-kalem-dialog',
    templateUrl: './borc-kalem-dialog.component.html'
})
export class BorcKalemDialogComponent implements OnInit {

    borcKalem: BorcKalem;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private borcKalemService: BorcKalemService,
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
        if (this.borcKalem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.borcKalemService.update(this.borcKalem));
        } else {
            this.subscribeToSaveResponse(
                this.borcKalemService.create(this.borcKalem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BorcKalem>>) {
        result.subscribe((res: HttpResponse<BorcKalem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BorcKalem) {
        this.eventManager.broadcast({ name: 'borcKalemListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-borc-kalem-popup',
    template: ''
})
export class BorcKalemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcKalemPopupService: BorcKalemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.borcKalemPopupService
                    .open(BorcKalemDialogComponent as Component, params['id']);
            } else {
                this.borcKalemPopupService
                    .open(BorcKalemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
