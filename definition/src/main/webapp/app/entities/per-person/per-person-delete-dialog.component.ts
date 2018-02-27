import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PerPerson } from './per-person.model';
import { PerPersonPopupService } from './per-person-popup.service';
import { PerPersonService } from './per-person.service';

@Component({
    selector: 'jhi-per-person-delete-dialog',
    templateUrl: './per-person-delete-dialog.component.html'
})
export class PerPersonDeleteDialogComponent {

    perPerson: PerPerson;

    constructor(
        private perPersonService: PerPersonService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.perPersonService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'perPersonListModification',
                content: 'Deleted an perPerson'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-per-person-delete-popup',
    template: ''
})
export class PerPersonDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perPersonPopupService: PerPersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.perPersonPopupService
                .open(PerPersonDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
