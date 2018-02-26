import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MasrafTipi } from './masraf-tipi.model';
import { MasrafTipiPopupService } from './masraf-tipi-popup.service';
import { MasrafTipiService } from './masraf-tipi.service';

@Component({
    selector: 'jhi-masraf-tipi-delete-dialog',
    templateUrl: './masraf-tipi-delete-dialog.component.html'
})
export class MasrafTipiDeleteDialogComponent {

    masrafTipi: MasrafTipi;

    constructor(
        private masrafTipiService: MasrafTipiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.masrafTipiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'masrafTipiListModification',
                content: 'Deleted an masrafTipi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-masraf-tipi-delete-popup',
    template: ''
})
export class MasrafTipiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private masrafTipiPopupService: MasrafTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.masrafTipiPopupService
                .open(MasrafTipiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
