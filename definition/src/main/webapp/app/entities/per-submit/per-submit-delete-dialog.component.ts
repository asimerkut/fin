import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PerSubmit } from './per-submit.model';
import { PerSubmitPopupService } from './per-submit-popup.service';
import { PerSubmitService } from './per-submit.service';

@Component({
    selector: 'jhi-per-submit-delete-dialog',
    templateUrl: './per-submit-delete-dialog.component.html'
})
export class PerSubmitDeleteDialogComponent {

    perSubmit: PerSubmit;

    constructor(
        private perSubmitService: PerSubmitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.perSubmitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'perSubmitListModification',
                content: 'Deleted an perSubmit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-per-submit-delete-popup',
    template: ''
})
export class PerSubmitDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perSubmitPopupService: PerSubmitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.perSubmitPopupService
                .open(PerSubmitDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
