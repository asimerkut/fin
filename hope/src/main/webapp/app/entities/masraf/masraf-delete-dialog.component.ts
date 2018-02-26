import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Masraf } from './masraf.model';
import { MasrafPopupService } from './masraf-popup.service';
import { MasrafService } from './masraf.service';

@Component({
    selector: 'jhi-masraf-delete-dialog',
    templateUrl: './masraf-delete-dialog.component.html'
})
export class MasrafDeleteDialogComponent {

    masraf: Masraf;

    constructor(
        private masrafService: MasrafService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.masrafService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'masrafListModification',
                content: 'Deleted an masraf'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-masraf-delete-popup',
    template: ''
})
export class MasrafDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private masrafPopupService: MasrafPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.masrafPopupService
                .open(MasrafDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
