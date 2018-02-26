import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopBorc } from './hop-borc.model';
import { HopBorcService } from './hop-borc.service';

@Component({
    selector: 'jhi-hop-borc-detail',
    templateUrl: './hop-borc-detail.component.html'
})
export class HopBorcDetailComponent implements OnInit, OnDestroy {

    hopBorc: HopBorc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopBorcService: HopBorcService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopBorcs();
    }

    load(id) {
        this.hopBorcService.find(id)
            .subscribe((hopBorcResponse: HttpResponse<HopBorc>) => {
                this.hopBorc = hopBorcResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopBorcs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopBorcListModification',
            (response) => this.load(this.hopBorc.id)
        );
    }
}
