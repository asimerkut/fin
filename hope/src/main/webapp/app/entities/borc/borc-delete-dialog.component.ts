import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Borc } from './borc.model';
import { BorcPopupService } from './borc-popup.service';
import { BorcService } from './borc.service';

@Component({
    selector: 'jhi-borc-delete-dialog',
    templateUrl: './borc-delete-dialog.component.html'
})
export class BorcDeleteDialogComponent {

    borc: Borc;

    constructor(
        private borcService: BorcService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.borcService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'borcListModification',
                content: 'Deleted an borc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-borc-delete-popup',
    template: ''
})
export class BorcDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcPopupService: BorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.borcPopupService
                .open(BorcDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
