import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DefRelation } from './def-relation.model';
import { DefRelationPopupService } from './def-relation-popup.service';
import { DefRelationService } from './def-relation.service';
import { DefType, DefTypeService } from '../def-type';

@Component({
    selector: 'jhi-def-relation-dialog',
    templateUrl: './def-relation-dialog.component.html'
})
export class DefRelationDialogComponent implements OnInit {

    defRelation: DefRelation;
    isSaving: boolean;

    deftypes: DefType[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private defRelationService: DefRelationService,
        private defTypeService: DefTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.defTypeService.query()
            .subscribe((res: HttpResponse<DefType[]>) => { this.deftypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.defRelation.id !== undefined) {
            this.subscribeToSaveResponse(
                this.defRelationService.update(this.defRelation));
        } else {
            this.subscribeToSaveResponse(
                this.defRelationService.create(this.defRelation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DefRelation>>) {
        result.subscribe((res: HttpResponse<DefRelation>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DefRelation) {
        this.eventManager.broadcast({ name: 'defRelationListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDefTypeById(index: number, item: DefType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-def-relation-popup',
    template: ''
})
export class DefRelationPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defRelationPopupService: DefRelationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.defRelationPopupService
                    .open(DefRelationDialogComponent as Component, params['id']);
            } else {
                this.defRelationPopupService
                    .open(DefRelationDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
