import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaBorc } from './dosya-borc.model';
import { DosyaBorcPopupService } from './dosya-borc-popup.service';
import { DosyaBorcService } from './dosya-borc.service';

@Component({
    selector: 'jhi-dosya-borc-delete-dialog',
    templateUrl: './dosya-borc-delete-dialog.component.html'
})
export class DosyaBorcDeleteDialogComponent {

    dosyaBorc: DosyaBorc;

    constructor(
        private dosyaBorcService: DosyaBorcService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dosyaBorcService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dosyaBorcListModification',
                content: 'Deleted an dosyaBorc'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dosya-borc-delete-popup',
    template: ''
})
export class DosyaBorcDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaBorcPopupService: DosyaBorcPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dosyaBorcPopupService
                .open(DosyaBorcDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
