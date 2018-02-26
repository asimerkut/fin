import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BorcTipi } from './borc-tipi.model';
import { BorcTipiService } from './borc-tipi.service';

@Component({
    selector: 'jhi-borc-tipi-detail',
    templateUrl: './borc-tipi-detail.component.html'
})
export class BorcTipiDetailComponent implements OnInit, OnDestroy {

    borcTipi: BorcTipi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private borcTipiService: BorcTipiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBorcTipis();
    }

    load(id) {
        this.borcTipiService.find(id)
            .subscribe((borcTipiResponse: HttpResponse<BorcTipi>) => {
                this.borcTipi = borcTipiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBorcTipis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'borcTipiListModification',
            (response) => this.load(this.borcTipi.id)
        );
    }
}
