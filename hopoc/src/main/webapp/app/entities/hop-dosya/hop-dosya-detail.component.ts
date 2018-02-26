import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopDosya } from './hop-dosya.model';
import { HopDosyaService } from './hop-dosya.service';

@Component({
    selector: 'jhi-hop-dosya-detail',
    templateUrl: './hop-dosya-detail.component.html'
})
export class HopDosyaDetailComponent implements OnInit, OnDestroy {

    hopDosya: HopDosya;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopDosyaService: HopDosyaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopDosyas();
    }

    load(id) {
        this.hopDosyaService.find(id)
            .subscribe((hopDosyaResponse: HttpResponse<HopDosya>) => {
                this.hopDosya = hopDosyaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopDosyas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopDosyaListModification',
            (response) => this.load(this.hopDosya.id)
        );
    }
}
