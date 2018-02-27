import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PerPlan } from './per-plan.model';
import { PerPlanService } from './per-plan.service';

@Component({
    selector: 'jhi-per-plan-detail',
    templateUrl: './per-plan-detail.component.html'
})
export class PerPlanDetailComponent implements OnInit, OnDestroy {

    perPlan: PerPlan;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private perPlanService: PerPlanService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPerPlans();
    }

    load(id) {
        this.perPlanService.find(id)
            .subscribe((perPlanResponse: HttpResponse<PerPlan>) => {
                this.perPlan = perPlanResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPerPlans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'perPlanListModification',
            (response) => this.load(this.perPlan.id)
        );
    }
}
