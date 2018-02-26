import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinansalHareket } from './finansal-hareket.model';
import { FinansalHareketPopupService } from './finansal-hareket-popup.service';
import { FinansalHareketService } from './finansal-hareket.service';

@Component({
    selector: 'jhi-finansal-hareket-delete-dialog',
    templateUrl: './finansal-hareket-delete-dialog.component.html'
})
export class FinansalHareketDeleteDialogComponent {

    finansalHareket: FinansalHareket;

    constructor(
        private finansalHareketService: FinansalHareketService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.finansalHareketService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'finansalHareketListModification',
                content: 'Deleted an finansalHareket'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-finansal-hareket-delete-popup',
    template: ''
})
export class FinansalHareketDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private finansalHareketPopupService: FinansalHareketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.finansalHareketPopupService
                .open(FinansalHareketDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
