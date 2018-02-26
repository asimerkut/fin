import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { DefItem } from './def-item.model';
import { DefItemPopupService } from './def-item-popup.service';
import { DefItemService } from './def-item.service';

@Component({
    selector: 'jhi-def-item-delete-dialog',
    templateUrl: './def-item-delete-dialog.component.html'
})
export class DefItemDeleteDialogComponent {

    defItem: DefItem;

    constructor(
        private defItemService: DefItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.defItemService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'defItemListModification',
                content: 'Deleted an defItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-def-item-delete-popup',
    template: ''
})
export class DefItemDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private defItemPopupService: DefItemPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.defItemPopupService
                .open(DefItemDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
