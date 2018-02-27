import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PerSubmit } from './per-submit.model';
import { PerSubmitPopupService } from './per-submit-popup.service';
import { PerSubmitService } from './per-submit.service';
import { PerPerson, PerPersonService } from '../per-person';
import { DefItem, DefItemService } from '../def-item';
import { PerExcuse, PerExcuseService } from '../per-excuse';

@Component({
    selector: 'jhi-per-submit-dialog',
    templateUrl: './per-submit-dialog.component.html'
})
export class PerSubmitDialogComponent implements OnInit {

    perSubmit: PerSubmit;
    isSaving: boolean;

    perpeople: PerPerson[];

    defitems: DefItem[];

    perexcuses: PerExcuse[];
    submitDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private perSubmitService: PerSubmitService,
        private perPersonService: PerPersonService,
        private defItemService: DefItemService,
        private perExcuseService: PerExcuseService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.perPersonService.query()
            .subscribe((res: HttpResponse<PerPerson[]>) => { this.perpeople = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.defItemService.query()
            .subscribe((res: HttpResponse<DefItem[]>) => { this.defitems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.perExcuseService.query()
            .subscribe((res: HttpResponse<PerExcuse[]>) => { this.perexcuses = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.perSubmit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.perSubmitService.update(this.perSubmit));
        } else {
            this.subscribeToSaveResponse(
                this.perSubmitService.create(this.perSubmit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PerSubmit>>) {
        result.subscribe((res: HttpResponse<PerSubmit>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PerSubmit) {
        this.eventManager.broadcast({ name: 'perSubmitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPerPersonById(index: number, item: PerPerson) {
        return item.id;
    }

    trackDefItemById(index: number, item: DefItem) {
        return item.id;
    }

    trackPerExcuseById(index: number, item: PerExcuse) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-per-submit-popup',
    template: ''
})
export class PerSubmitPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perSubmitPopupService: PerSubmitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.perSubmitPopupService
                    .open(PerSubmitDialogComponent as Component, params['id']);
            } else {
                this.perSubmitPopupService
                    .open(PerSubmitDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
