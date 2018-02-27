import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PerCompany } from './per-company.model';
import { PerCompanyPopupService } from './per-company-popup.service';
import { PerCompanyService } from './per-company.service';

@Component({
    selector: 'jhi-per-company-delete-dialog',
    templateUrl: './per-company-delete-dialog.component.html'
})
export class PerCompanyDeleteDialogComponent {

    perCompany: PerCompany;

    constructor(
        private perCompanyService: PerCompanyService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.perCompanyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'perCompanyListModification',
                content: 'Deleted an perCompany'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-per-company-delete-popup',
    template: ''
})
export class PerCompanyDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perCompanyPopupService: PerCompanyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.perCompanyPopupService
                .open(PerCompanyDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
