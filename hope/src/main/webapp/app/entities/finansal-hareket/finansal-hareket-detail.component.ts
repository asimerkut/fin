import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FinansalHareket } from './finansal-hareket.model';
import { FinansalHareketService } from './finansal-hareket.service';

@Component({
    selector: 'jhi-finansal-hareket-detail',
    templateUrl: './finansal-hareket-detail.component.html'
})
export class FinansalHareketDetailComponent implements OnInit, OnDestroy {

    finansalHareket: FinansalHareket;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private finansalHareketService: FinansalHareketService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFinansalHarekets();
    }

    load(id) {
        this.finansalHareketService.find(id)
            .subscribe((finansalHareketResponse: HttpResponse<FinansalHareket>) => {
                this.finansalHareket = finansalHareketResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFinansalHarekets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'finansalHareketListModification',
            (response) => this.load(this.finansalHareket.id)
        );
    }
}
