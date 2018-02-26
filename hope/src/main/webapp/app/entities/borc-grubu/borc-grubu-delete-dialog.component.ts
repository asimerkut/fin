import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BorcGrubu } from './borc-grubu.model';
import { BorcGrubuPopupService } from './borc-grubu-popup.service';
import { BorcGrubuService } from './borc-grubu.service';

@Component({
    selector: 'jhi-borc-grubu-delete-dialog',
    templateUrl: './borc-grubu-delete-dialog.component.html'
})
export class BorcGrubuDeleteDialogComponent {

    borcGrubu: BorcGrubu;

    constructor(
        private borcGrubuService: BorcGrubuService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.borcGrubuService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'borcGrubuListModification',
                content: 'Deleted an borcGrubu'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-borc-grubu-delete-popup',
    template: ''
})
export class BorcGrubuDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private borcGrubuPopupService: BorcGrubuPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.borcGrubuPopupService
                .open(BorcGrubuDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
