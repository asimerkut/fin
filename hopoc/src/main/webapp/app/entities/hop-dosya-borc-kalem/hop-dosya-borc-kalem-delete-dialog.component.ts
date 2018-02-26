import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopDosyaBorcKalem } from './hop-dosya-borc-kalem.model';
import { HopDosyaBorcKalemPopupService } from './hop-dosya-borc-kalem-popup.service';
import { HopDosyaBorcKalemService } from './hop-dosya-borc-kalem.service';

@Component({
    selector: 'jhi-hop-dosya-borc-kalem-delete-dialog',
    templateUrl: './hop-dosya-borc-kalem-delete-dialog.component.html'
})
export class HopDosyaBorcKalemDeleteDialogComponent {

    hopDosyaBorcKalem: HopDosyaBorcKalem;

    constructor(
        private hopDosyaBorcKalemService: HopDosyaBorcKalemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopDosyaBorcKalemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopDosyaBorcKalemListModification',
                content: 'Deleted an hopDosyaBorcKalem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-dosya-borc-kalem-delete-popup',
    template: ''
})
export class HopDosyaBorcKalemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDosyaBorcKalemPopupService: HopDosyaBorcKalemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopDosyaBorcKalemPopupService
                .open(HopDosyaBorcKalemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
