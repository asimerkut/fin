import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaTipi } from './dosya-tipi.model';
import { DosyaTipiPopupService } from './dosya-tipi-popup.service';
import { DosyaTipiService } from './dosya-tipi.service';

@Component({
    selector: 'jhi-dosya-tipi-delete-dialog',
    templateUrl: './dosya-tipi-delete-dialog.component.html'
})
export class DosyaTipiDeleteDialogComponent {

    dosyaTipi: DosyaTipi;

    constructor(
        private dosyaTipiService: DosyaTipiService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.dosyaTipiService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'dosyaTipiListModification',
                content: 'Deleted an dosyaTipi'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-dosya-tipi-delete-popup',
    template: ''
})
export class DosyaTipiDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private dosyaTipiPopupService: DosyaTipiPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.dosyaTipiPopupService
                .open(DosyaTipiDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
