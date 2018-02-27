import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DefType } from './def-type.model';
import { DefTypePopupService } from './def-type-popup.service';
import { DefTypeService } from './def-type.service';

@Component({
    selector: 'jhi-def-type-delete-dialog',
    templateUrl: './def-type-delete-dialog.component.html'
})
export class DefTypeDeleteDialogComponent {

    defType: DefType;

    constructor(
        private defTypeService: DefTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.defTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'defTypeListModification',
                content: 'Deleted an defType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-def-type-delete-popup',
    template: ''
})
export class DefTypeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defTypePopupService: DefTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.defTypePopupService
                .open(DefTypeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
