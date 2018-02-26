import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopDosyaBorcKalem } from './hop-dosya-borc-kalem.model';
import { HopDosyaBorcKalemService } from './hop-dosya-borc-kalem.service';

@Component({
    selector: 'jhi-hop-dosya-borc-kalem-detail',
    templateUrl: './hop-dosya-borc-kalem-detail.component.html'
})
export class HopDosyaBorcKalemDetailComponent implements OnInit, OnDestroy {

    hopDosyaBorcKalem: HopDosyaBorcKalem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopDosyaBorcKalemService: HopDosyaBorcKalemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopDosyaBorcKalems();
    }

    load(id) {
        this.hopDosyaBorcKalemService.find(id)
            .subscribe((hopDosyaBorcKalemResponse: HttpResponse<HopDosyaBorcKalem>) => {
                this.hopDosyaBorcKalem = hopDosyaBorcKalemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopDosyaBorcKalems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopDosyaBorcKalemListModification',
            (response) => this.load(this.hopDosyaBorcKalem.id)
        );
    }
}
