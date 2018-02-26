import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopMasraf } from './hop-masraf.model';
import { HopMasrafPopupService } from './hop-masraf-popup.service';
import { HopMasrafService } from './hop-masraf.service';

@Component({
    selector: 'jhi-hop-masraf-delete-dialog',
    templateUrl: './hop-masraf-delete-dialog.component.html'
})
export class HopMasrafDeleteDialogComponent {

    hopMasraf: HopMasraf;

    constructor(
        private hopMasrafService: HopMasrafService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopMasrafService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopMasrafListModification',
                content: 'Deleted an hopMasraf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-masraf-delete-popup',
    template: ''
})
export class HopMasrafDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopMasrafPopupService: HopMasrafPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopMasrafPopupService
                .open(HopMasrafDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
