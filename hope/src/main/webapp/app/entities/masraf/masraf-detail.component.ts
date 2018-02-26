import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Masraf } from './masraf.model';
import { MasrafService } from './masraf.service';

@Component({
    selector: 'jhi-masraf-detail',
    templateUrl: './masraf-detail.component.html'
})
export class MasrafDetailComponent implements OnInit, OnDestroy {

    masraf: Masraf;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private masrafService: MasrafService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMasrafs();
    }

    load(id) {
        this.masrafService.find(id)
            .subscribe((masrafResponse: HttpResponse<Masraf>) => {
                this.masraf = masrafResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMasrafs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'masrafListModification',
            (response) => this.load(this.masraf.id)
        );
    }
}
