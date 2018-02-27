import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PerExcuse } from './per-excuse.model';
import { PerExcusePopupService } from './per-excuse-popup.service';
import { PerExcuseService } from './per-excuse.service';

@Component({
    selector: 'jhi-per-excuse-delete-dialog',
    templateUrl: './per-excuse-delete-dialog.component.html'
})
export class PerExcuseDeleteDialogComponent {

    perExcuse: PerExcuse;

    constructor(
        private perExcuseService: PerExcuseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.perExcuseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'perExcuseListModification',
                content: 'Deleted an perExcuse'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-per-excuse-delete-popup',
    template: ''
})
export class PerExcuseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perExcusePopupService: PerExcusePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.perExcusePopupService
                .open(PerExcuseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
