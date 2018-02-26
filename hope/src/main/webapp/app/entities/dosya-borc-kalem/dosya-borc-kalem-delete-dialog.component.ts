import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaBorcKalem } from './dosya-borc-kalem.model';
import { DosyaBorcKalemPopupService } from './dosya-borc-kalem-popup.service';
import { DosyaBorcKalemService } from './dosya-borc-kalem.service';

@Component({
    selector: 'jhi-dosya-borc-kalem-delete-dialog',
    templateUrl: './dosya-borc-kalem-delete-dialog.component.html'
})
export class DosyaBorcKalemDeleteDialogComponent {

    dosyaBorcKalem: DosyaBorcKalem;

    constructor(
        private dosyaBorcKalemService: DosyaBorcKalemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dosyaBorcKalemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dosyaBorcKalemListModification',
                content: 'Deleted an dosyaBorcKalem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dosya-borc-kalem-delete-popup',
    template: ''
})
export class DosyaBorcKalemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaBorcKalemPopupService: DosyaBorcKalemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dosyaBorcKalemPopupService
                .open(DosyaBorcKalemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
