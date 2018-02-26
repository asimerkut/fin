import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopFinansalHareketDetay } from './hop-finansal-hareket-detay.model';
import { HopFinansalHareketDetayPopupService } from './hop-finansal-hareket-detay-popup.service';
import { HopFinansalHareketDetayService } from './hop-finansal-hareket-detay.service';

@Component({
    selector: 'jhi-hop-finansal-hareket-detay-delete-dialog',
    templateUrl: './hop-finansal-hareket-detay-delete-dialog.component.html'
})
export class HopFinansalHareketDetayDeleteDialogComponent {

    hopFinansalHareketDetay: HopFinansalHareketDetay;

    constructor(
        private hopFinansalHareketDetayService: HopFinansalHareketDetayService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopFinansalHareketDetayService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopFinansalHareketDetayListModification',
                content: 'Deleted an hopFinansalHareketDetay'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-finansal-hareket-detay-delete-popup',
    template: ''
})
export class HopFinansalHareketDetayDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopFinansalHareketDetayPopupService: HopFinansalHareketDetayPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopFinansalHareketDetayPopupService
                .open(HopFinansalHareketDetayDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
