import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Borc } from './borc.model';
import { BorcService } from './borc.service';

@Component({
    selector: 'jhi-borc-detail',
    templateUrl: './borc-detail.component.html'
})
export class BorcDetailComponent implements OnInit, OnDestroy {

    borc: Borc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private borcService: BorcService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBorcs();
    }

    load(id) {
        this.borcService.find(id)
            .subscribe((borcResponse: HttpResponse<Borc>) => {
                this.borc = borcResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBorcs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'borcListModification',
            (response) => this.load(this.borc.id)
        );
    }
}
