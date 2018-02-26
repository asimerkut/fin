import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { HopDosya } from './hop-dosya.model';
import { HopDosyaPopupService } from './hop-dosya-popup.service';
import { HopDosyaService } from './hop-dosya.service';

@Component({
    selector: 'jhi-hop-dosya-delete-dialog',
    templateUrl: './hop-dosya-delete-dialog.component.html'
})
export class HopDosyaDeleteDialogComponent {

    hopDosya: HopDosya;

    constructor(
        private hopDosyaService: HopDosyaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.hopDosyaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'hopDosyaListModification',
                content: 'Deleted an hopDosya'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-hop-dosya-delete-popup',
    template: ''
})
export class HopDosyaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private hopDosyaPopupService: HopDosyaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.hopDosyaPopupService
                .open(HopDosyaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
