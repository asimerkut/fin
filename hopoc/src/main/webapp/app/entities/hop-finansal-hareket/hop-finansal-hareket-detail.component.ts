import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopFinansalHareket } from './hop-finansal-hareket.model';
import { HopFinansalHareketService } from './hop-finansal-hareket.service';

@Component({
    selector: 'jhi-hop-finansal-hareket-detail',
    templateUrl: './hop-finansal-hareket-detail.component.html'
})
export class HopFinansalHareketDetailComponent implements OnInit, OnDestroy {

    hopFinansalHareket: HopFinansalHareket;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopFinansalHareketService: HopFinansalHareketService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopFinansalHarekets();
    }

    load(id) {
        this.hopFinansalHareketService.find(id)
            .subscribe((hopFinansalHareketResponse: HttpResponse<HopFinansalHareket>) => {
                this.hopFinansalHareket = hopFinansalHareketResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopFinansalHarekets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopFinansalHareketListModification',
            (response) => this.load(this.hopFinansalHareket.id)
        );
    }
}
