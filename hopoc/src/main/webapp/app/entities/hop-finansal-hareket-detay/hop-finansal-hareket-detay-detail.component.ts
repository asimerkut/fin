import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopFinansalHareketDetay } from './hop-finansal-hareket-detay.model';
import { HopFinansalHareketDetayService } from './hop-finansal-hareket-detay.service';

@Component({
    selector: 'jhi-hop-finansal-hareket-detay-detail',
    templateUrl: './hop-finansal-hareket-detay-detail.component.html'
})
export class HopFinansalHareketDetayDetailComponent implements OnInit, OnDestroy {

    hopFinansalHareketDetay: HopFinansalHareketDetay;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopFinansalHareketDetayService: HopFinansalHareketDetayService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopFinansalHareketDetays();
    }

    load(id) {
        this.hopFinansalHareketDetayService.find(id)
            .subscribe((hopFinansalHareketDetayResponse: HttpResponse<HopFinansalHareketDetay>) => {
                this.hopFinansalHareketDetay = hopFinansalHareketDetayResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopFinansalHareketDetays() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopFinansalHareketDetayListModification',
            (response) => this.load(this.hopFinansalHareketDetay.id)
        );
    }
}
