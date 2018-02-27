import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DefPivot } from './def-pivot.model';
import { DefPivotPopupService } from './def-pivot-popup.service';
import { DefPivotService } from './def-pivot.service';

@Component({
    selector: 'jhi-def-pivot-delete-dialog',
    templateUrl: './def-pivot-delete-dialog.component.html'
})
export class DefPivotDeleteDialogComponent {

    defPivot: DefPivot;

    constructor(
        private defPivotService: DefPivotService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.defPivotService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'defPivotListModification',
                content: 'Deleted an defPivot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-def-pivot-delete-popup',
    template: ''
})
export class DefPivotDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defPivotPopupService: DefPivotPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.defPivotPopupService
                .open(DefPivotDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
