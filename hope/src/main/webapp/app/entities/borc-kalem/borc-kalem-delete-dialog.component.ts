import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BorcKalem } from './borc-kalem.model';
import { BorcKalemPopupService } from './borc-kalem-popup.service';
import { BorcKalemService } from './borc-kalem.service';

@Component({
    selector: 'jhi-borc-kalem-delete-dialog',
    templateUrl: './borc-kalem-delete-dialog.component.html'
})
export class BorcKalemDeleteDialogComponent {

    borcKalem: BorcKalem;

    constructor(
        private borcKalemService: BorcKalemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.borcKalemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'borcKalemListModification',
                content: 'Deleted an borcKalem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-borc-kalem-delete-popup',
    template: ''
})
export class BorcKalemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcKalemPopupService: BorcKalemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.borcKalemPopupService
                .open(BorcKalemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
