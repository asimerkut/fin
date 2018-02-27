import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { PerCompany } from './per-company.model';
import { PerCompanyPopupService } from './per-company-popup.service';
import { PerCompanyService } from './per-company.service';
import { DefItem, DefItemService } from '../def-item';

@Component({
    selector: 'jhi-per-company-dialog',
    templateUrl: './per-company-dialog.component.html'
})
export class PerCompanyDialogComponent implements OnInit {

    perCompany: PerCompany;
    isSaving: boolean;

    defitems: DefItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private perCompanyService: PerCompanyService,
        private defItemService: DefItemService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.defItemService.query()
            .subscribe((res: HttpResponse<DefItem[]>) => { this.defitems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.perCompany.id !== undefined) {
            this.subscribeToSaveResponse(
                this.perCompanyService.update(this.perCompany));
        } else {
            this.subscribeToSaveResponse(
                this.perCompanyService.create(this.perCompany));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<PerCompany>>) {
        result.subscribe((res: HttpResponse<PerCompany>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: PerCompany) {
        this.eventManager.broadcast({ name: 'perCompanyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackDefItemById(index: number, item: DefItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-per-company-popup',
    template: ''
})
export class PerCompanyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perCompanyPopupService: PerCompanyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.perCompanyPopupService
                    .open(PerCompanyDialogComponent as Component, params['id']);
            } else {
                this.perCompanyPopupService
                    .open(PerCompanyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
