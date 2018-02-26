import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopBorc } from './hop-borc.model';
import { HopBorcPopupService } from './hop-borc-popup.service';
import { HopBorcService } from './hop-borc.service';

@Component({
    selector: 'jhi-hop-borc-delete-dialog',
    templateUrl: './hop-borc-delete-dialog.component.html'
})
export class HopBorcDeleteDialogComponent {

    hopBorc: HopBorc;

    constructor(
        private hopBorcService: HopBorcService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopBorcService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopBorcListModification',
                content: 'Deleted an hopBorc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-borc-delete-popup',
    template: ''
})
export class HopBorcDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopBorcPopupService: HopBorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopBorcPopupService
                .open(HopBorcDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
