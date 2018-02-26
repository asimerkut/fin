import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IslemKodu } from './islem-kodu.model';
import { IslemKoduPopupService } from './islem-kodu-popup.service';
import { IslemKoduService } from './islem-kodu.service';

@Component({
    selector: 'jhi-islem-kodu-delete-dialog',
    templateUrl: './islem-kodu-delete-dialog.component.html'
})
export class IslemKoduDeleteDialogComponent {

    islemKodu: IslemKodu;

    constructor(
        private islemKoduService: IslemKoduService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.islemKoduService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'islemKoduListModification',
                content: 'Deleted an islemKodu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-islem-kodu-delete-popup',
    template: ''
})
export class IslemKoduDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private islemKoduPopupService: IslemKoduPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.islemKoduPopupService
                .open(IslemKoduDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
