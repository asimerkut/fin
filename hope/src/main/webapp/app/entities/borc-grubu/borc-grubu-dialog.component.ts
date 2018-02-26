import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BorcGrubu } from './borc-grubu.model';
import { BorcGrubuPopupService } from './borc-grubu-popup.service';
import { BorcGrubuService } from './borc-grubu.service';

@Component({
    selector: 'jhi-borc-grubu-dialog',
    templateUrl: './borc-grubu-dialog.component.html'
})
export class BorcGrubuDialogComponent implements OnInit {

    borcGrubu: BorcGrubu;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private borcGrubuService: BorcGrubuService,
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
        if (this.borcGrubu.id !== undefined) {
            this.subscribeToSaveResponse(
                this.borcGrubuService.update(this.borcGrubu));
        } else {
            this.subscribeToSaveResponse(
                this.borcGrubuService.create(this.borcGrubu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<BorcGrubu>>) {
        result.subscribe((res: HttpResponse<BorcGrubu>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: BorcGrubu) {
        this.eventManager.broadcast({ name: 'borcGrubuListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-borc-grubu-popup',
    template: ''
})
export class BorcGrubuPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcGrubuPopupService: BorcGrubuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.borcGrubuPopupService
                    .open(BorcGrubuDialogComponent as Component, params['id']);
            } else {
                this.borcGrubuPopupService
                    .open(BorcGrubuDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
