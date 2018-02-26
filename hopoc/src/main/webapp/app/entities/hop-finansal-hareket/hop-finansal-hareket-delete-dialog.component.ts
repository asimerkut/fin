import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopFinansalHareket } from './hop-finansal-hareket.model';
import { HopFinansalHareketPopupService } from './hop-finansal-hareket-popup.service';
import { HopFinansalHareketService } from './hop-finansal-hareket.service';

@Component({
    selector: 'jhi-hop-finansal-hareket-delete-dialog',
    templateUrl: './hop-finansal-hareket-delete-dialog.component.html'
})
export class HopFinansalHareketDeleteDialogComponent {

    hopFinansalHareket: HopFinansalHareket;

    constructor(
        private hopFinansalHareketService: HopFinansalHareketService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopFinansalHareketService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopFinansalHareketListModification',
                content: 'Deleted an hopFinansalHareket'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-finansal-hareket-delete-popup',
    template: ''
})
export class HopFinansalHareketDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopFinansalHareketPopupService: HopFinansalHareketPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopFinansalHareketPopupService
                .open(HopFinansalHareketDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
