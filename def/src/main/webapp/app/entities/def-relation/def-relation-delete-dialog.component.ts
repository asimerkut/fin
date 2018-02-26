import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DefRelation } from './def-relation.model';
import { DefRelationPopupService } from './def-relation-popup.service';
import { DefRelationService } from './def-relation.service';

@Component({
    selector: 'jhi-def-relation-delete-dialog',
    templateUrl: './def-relation-delete-dialog.component.html'
})
export class DefRelationDeleteDialogComponent {

    defRelation: DefRelation;

    constructor(
        private defRelationService: DefRelationService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.defRelationService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'defRelationListModification',
                content: 'Deleted an defRelation'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-def-relation-delete-popup',
    template: ''
})
export class DefRelationDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defRelationPopupService: DefRelationPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.defRelationPopupService
                .open(DefRelationDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
