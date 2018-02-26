import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MasrafTipi } from './masraf-tipi.model';
import { MasrafTipiService } from './masraf-tipi.service';

@Component({
    selector: 'jhi-masraf-tipi-detail',
    templateUrl: './masraf-tipi-detail.component.html'
})
export class MasrafTipiDetailComponent implements OnInit, OnDestroy {

    masrafTipi: MasrafTipi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private masrafTipiService: MasrafTipiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMasrafTipis();
    }

    load(id) {
        this.masrafTipiService.find(id)
            .subscribe((masrafTipiResponse: HttpResponse<MasrafTipi>) => {
                this.masrafTipi = masrafTipiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMasrafTipis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'masrafTipiListModification',
            (response) => this.load(this.masrafTipi.id)
        );
    }
}
