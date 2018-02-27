import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { PerSubmit } from './per-submit.model';
import { PerSubmitService } from './per-submit.service';

@Component({
    selector: 'jhi-per-submit-detail',
    templateUrl: './per-submit-detail.component.html'
})
export class PerSubmitDetailComponent implements OnInit, OnDestroy {

    perSubmit: PerSubmit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private perSubmitService: PerSubmitService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPerSubmits();
    }

    load(id) {
        this.perSubmitService.find(id)
            .subscribe((perSubmitResponse: HttpResponse<PerSubmit>) => {
                this.perSubmit = perSubmitResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPerSubmits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'perSubmitListModification',
            (response) => this.load(this.perSubmit.id)
        );
    }
}
