import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Dosya } from './dosya.model';
import { DosyaPopupService } from './dosya-popup.service';
import { DosyaService } from './dosya.service';

@Component({
    selector: 'jhi-dosya-delete-dialog',
    templateUrl: './dosya-delete-dialog.component.html'
})
export class DosyaDeleteDialogComponent {

    dosya: Dosya;

    constructor(
        private dosyaService: DosyaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dosyaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dosyaListModification',
                content: 'Deleted an dosya'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dosya-delete-popup',
    template: ''
})
export class DosyaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaPopupService: DosyaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dosyaPopupService
                .open(DosyaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
