import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopMasraf } from './hop-masraf.model';
import { HopMasrafService } from './hop-masraf.service';

@Component({
    selector: 'jhi-hop-masraf-detail',
    templateUrl: './hop-masraf-detail.component.html'
})
export class HopMasrafDetailComponent implements OnInit, OnDestroy {

    hopMasraf: HopMasraf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopMasrafService: HopMasrafService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopMasrafs();
    }

    load(id) {
        this.hopMasrafService.find(id)
            .subscribe((hopMasrafResponse: HttpResponse<HopMasraf>) => {
                this.hopMasraf = hopMasrafResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopMasrafs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopMasrafListModification',
            (response) => this.load(this.hopMasraf.id)
        );
    }
}
