import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopDosyaBorc } from './hop-dosya-borc.model';
import { HopDosyaBorcPopupService } from './hop-dosya-borc-popup.service';
import { HopDosyaBorcService } from './hop-dosya-borc.service';

@Component({
    selector: 'jhi-hop-dosya-borc-delete-dialog',
    templateUrl: './hop-dosya-borc-delete-dialog.component.html'
})
export class HopDosyaBorcDeleteDialogComponent {

    hopDosyaBorc: HopDosyaBorc;

    constructor(
        private hopDosyaBorcService: HopDosyaBorcService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopDosyaBorcService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopDosyaBorcListModification',
                content: 'Deleted an hopDosyaBorc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-dosya-borc-delete-popup',
    template: ''
})
export class HopDosyaBorcDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDosyaBorcPopupService: HopDosyaBorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopDosyaBorcPopupService
                .open(HopDosyaBorcDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
