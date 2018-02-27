import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PerExcuse } from './per-excuse.model';
import { PerExcusePopupService } from './per-excuse-popup.service';
import { PerExcuseService } from './per-excuse.service';
import { PerPerson, PerPersonService } from '../per-person';

@Component({
    selector: 'jhi-per-excuse-dialog',
    templateUrl: './per-excuse-dialog.component.html'
})
export class PerExcuseDialogComponent implements OnInit {

    perExcuse: PerExcuse;
    isSaving: boolean;

    perpeople: PerPerson[];
    startDateDp: any;
    finishDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private perExcuseService: PerExcuseService,
        private perPersonService: PerPersonService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.perPersonService.query()
            .subscribe((res: HttpResponse<PerPerson[]>) => { this.perpeople = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.perExcuse.id !== undefined) {
            this.subscribeToSaveResponse(
                this.perExcuseService.update(this.perExcuse));
        } else {
            this.subscribeToSaveResponse(
                this.perExcuseService.create(this.perExcuse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PerExcuse>>) {
        result.subscribe((res: HttpResponse<PerExcuse>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PerExcuse) {
        this.eventManager.broadcast({ name: 'perExcuseListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-per-excuse-popup',
    template: ''
})
export class PerExcusePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perExcusePopupService: PerExcusePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.perExcusePopupService
                    .open(PerExcuseDialogComponent as Component, params['id']);
            } else {
                this.perExcusePopupService
                    .open(PerExcuseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
