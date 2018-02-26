import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FinansalHareketDetay } from './finansal-hareket-detay.model';
import { FinansalHareketDetayPopupService } from './finansal-hareket-detay-popup.service';
import { FinansalHareketDetayService } from './finansal-hareket-detay.service';

@Component({
    selector: 'jhi-finansal-hareket-detay-delete-dialog',
    templateUrl: './finansal-hareket-detay-delete-dialog.component.html'
})
export class FinansalHareketDetayDeleteDialogComponent {

    finansalHareketDetay: FinansalHareketDetay;

    constructor(
        private finansalHareketDetayService: FinansalHareketDetayService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.finansalHareketDetayService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'finansalHareketDetayListModification',
                content: 'Deleted an finansalHareketDetay'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-finansal-hareket-detay-delete-popup',
    template: ''
})
export class FinansalHareketDetayDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private finansalHareketDetayPopupService: FinansalHareketDetayPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.finansalHareketDetayPopupService
                .open(FinansalHareketDetayDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
