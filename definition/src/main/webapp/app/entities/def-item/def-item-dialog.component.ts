import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { DefItem } from './def-item.model';
import { DefItemPopupService } from './def-item-popup.service';
import { DefItemService } from './def-item.service';
import { DefType, DefTypeService } from '../def-type';

@Component({
    selector: 'jhi-def-item-dialog',
    templateUrl: './def-item-dialog.component.html'
})
export class DefItemDialogComponent implements OnInit {

    defItem: DefItem;
    isSaving: boolean;

    deftypes: DefType[];

    defitems: DefItem[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private defItemService: DefItemService,
        private defTypeService: DefTypeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.defTypeService.query()
            .subscribe((res: HttpResponse<DefType[]>) => { this.deftypes = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.defItemService.query()
            .subscribe((res: HttpResponse<DefItem[]>) => { this.defitems = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.defItem.id !== undefined) {
            this.subscribeToSaveResponse(
                this.defItemService.update(this.defItem));
        } else {
            this.subscribeToSaveResponse(
                this.defItemService.create(this.defItem));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<DefItem>>) {
        result.subscribe((res: HttpResponse<DefItem>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: DefItem) {
        this.eventManager.broadcast({ name: 'defItemListModification', content: 'OK'});
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

    trackDefItemById(index: number, item: DefItem) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-def-item-popup',
    template: ''
})
export class DefItemPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defItemPopupService: DefItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.defItemPopupService
                    .open(DefItemDialogComponent as Component, params['id']);
            } else {
                this.defItemPopupService
                    .open(DefItemDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
