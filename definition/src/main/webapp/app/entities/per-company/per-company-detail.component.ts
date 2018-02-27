import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PerCompany } from './per-company.model';
import { PerCompanyService } from './per-company.service';

@Component({
    selector: 'jhi-per-company-detail',
    templateUrl: './per-company-detail.component.html'
})
export class PerCompanyDetailComponent implements OnInit, OnDestroy {

    perCompany: PerCompany;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private perCompanyService: PerCompanyService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPerCompanies();
    }

    load(id) {
        this.perCompanyService.find(id)
            .subscribe((perCompanyResponse: HttpResponse<PerCompany>) => {
                this.perCompany = perCompanyResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPerCompanies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'perCompanyListModification',
            (response) => this.load(this.perCompany.id)
        );
    }
}
