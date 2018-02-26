import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BorcTipi } from './borc-tipi.model';
import { BorcTipiPopupService } from './borc-tipi-popup.service';
import { BorcTipiService } from './borc-tipi.service';

@Component({
    selector: 'jhi-borc-tipi-delete-dialog',
    templateUrl: './borc-tipi-delete-dialog.component.html'
})
export class BorcTipiDeleteDialogComponent {

    borcTipi: BorcTipi;

    constructor(
        private borcTipiService: BorcTipiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.borcTipiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'borcTipiListModification',
                content: 'Deleted an borcTipi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-borc-tipi-delete-popup',
    template: ''
})
export class BorcTipiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcTipiPopupService: BorcTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.borcTipiPopupService
                .open(BorcTipiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
