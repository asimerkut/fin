import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FinansalHareketDetay } from './finansal-hareket-detay.model';
import { FinansalHareketDetayService } from './finansal-hareket-detay.service';

@Component({
    selector: 'jhi-finansal-hareket-detay-detail',
    templateUrl: './finansal-hareket-detay-detail.component.html'
})
export class FinansalHareketDetayDetailComponent implements OnInit, OnDestroy {

    finansalHareketDetay: FinansalHareketDetay;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private finansalHareketDetayService: FinansalHareketDetayService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFinansalHareketDetays();
    }

    load(id) {
        this.finansalHareketDetayService.find(id)
            .subscribe((finansalHareketDetayResponse: HttpResponse<FinansalHareketDetay>) => {
                this.finansalHareketDetay = finansalHareketDetayResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFinansalHareketDetays() {
        this.eventSubscriber = this.eventManager.subscribe(
            'finansalHareketDetayListModification',
            (response) => this.load(this.finansalHareketDetay.id)
        );
    }
}
