import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { BorcKalem } from './borc-kalem.model';
import { BorcKalemService } from './borc-kalem.service';

@Component({
    selector: 'jhi-borc-kalem-detail',
    templateUrl: './borc-kalem-detail.component.html'
})
export class BorcKalemDetailComponent implements OnInit, OnDestroy {

    borcKalem: BorcKalem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private borcKalemService: BorcKalemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBorcKalems();
    }

    load(id) {
        this.borcKalemService.find(id)
            .subscribe((borcKalemResponse: HttpResponse<BorcKalem>) => {
                this.borcKalem = borcKalemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBorcKalems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'borcKalemListModification',
            (response) => this.load(this.borcKalem.id)
        );
    }
}
