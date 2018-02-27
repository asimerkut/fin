import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { PerPlan } from './per-plan.model';
import { PerPlanPopupService } from './per-plan-popup.service';
import { PerPlanService } from './per-plan.service';

@Component({
    selector: 'jhi-per-plan-delete-dialog',
    templateUrl: './per-plan-delete-dialog.component.html'
})
export class PerPlanDeleteDialogComponent {

    perPlan: PerPlan;

    constructor(
        private perPlanService: PerPlanService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.perPlanService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'perPlanListModification',
                content: 'Deleted an perPlan'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-per-plan-delete-popup',
    template: ''
})
export class PerPlanDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private perPlanPopupService: PerPlanPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.perPlanPopupService
                .open(PerPlanDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
