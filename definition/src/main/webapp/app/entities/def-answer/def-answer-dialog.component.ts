import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DefAnswer } from './def-answer.model';
import { DefAnswerPopupService } from './def-answer-popup.service';
import { DefAnswerService } from './def-answer.service';
import { DefRelation, DefRelationService } from '../def-relation';
import { DefItem, DefItemService } from '../def-item';

@Component({
    selector: 'jhi-def-answer-dialog',
    templateUrl: './def-answer-dialog.component.html'
})
export class DefAnswerDialogComponent implements OnInit {

    defAnswer: DefAnswer;
    isSaving: boolean;

    defrelations: DefRelation[];

    defitems: DefItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private defAnswerService: DefAnswerService,
        private defRelationService: DefRelationService,
        private defItemService: DefItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.defRelationService.query()
            .subscribe((res: HttpResponse<DefRelation[]>) => { this.defrelations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.defItemService.query()
            .subscribe((res: HttpResponse<DefItem[]>) => { this.defitems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.defAnswer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.defAnswerService.update(this.defAnswer));
        } else {
            this.subscribeToSaveResponse(
                this.defAnswerService.create(this.defAnswer));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DefAnswer>>) {
        result.subscribe((res: HttpResponse<DefAnswer>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DefAnswer) {
        this.eventManager.broadcast({ name: 'defAnswerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDefRelationById(index: number, item: DefRelation) {
        return item.id;
    }

    trackDefItemById(index: number, item: DefItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-def-answer-popup',
    template: ''
})
export class DefAnswerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defAnswerPopupService: DefAnswerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.defAnswerPopupService
                    .open(DefAnswerDialogComponent as Component, params['id']);
            } else {
                this.defAnswerPopupService
                    .open(DefAnswerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
