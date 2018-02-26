import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { DefPivot } from './def-pivot.model';
import { DefPivotPopupService } from './def-pivot-popup.service';
import { DefPivotService } from './def-pivot.service';

@Component({
    selector: 'jhi-def-pivot-dialog',
    templateUrl: './def-pivot-dialog.component.html'
})
export class DefPivotDialogComponent implements OnInit {

    defPivot: DefPivot;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private defPivotService: DefPivotService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.defPivot.id !== undefined) {
            this.subscribeToSaveResponse(
                this.defPivotService.update(this.defPivot));
        } else {
            this.subscribeToSaveResponse(
                this.defPivotService.create(this.defPivot));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DefPivot>>) {
        result.subscribe((res: HttpResponse<DefPivot>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DefPivot) {
        this.eventManager.broadcast({ name: 'defPivotListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-def-pivot-popup',
    template: ''
})
export class DefPivotPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defPivotPopupService: DefPivotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.defPivotPopupService
                    .open(DefPivotDialogComponent as Component, params['id']);
            } else {
                this.defPivotPopupService
                    .open(DefPivotDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
