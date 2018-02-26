import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { HopDosyaBorc } from './hop-dosya-borc.model';
import { HopDosyaBorcService } from './hop-dosya-borc.service';

@Component({
    selector: 'jhi-hop-dosya-borc-detail',
    templateUrl: './hop-dosya-borc-detail.component.html'
})
export class HopDosyaBorcDetailComponent implements OnInit, OnDestroy {

    hopDosyaBorc: HopDosyaBorc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private hopDosyaBorcService: HopDosyaBorcService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHopDosyaBorcs();
    }

    load(id) {
        this.hopDosyaBorcService.find(id)
            .subscribe((hopDosyaBorcResponse: HttpResponse<HopDosyaBorc>) => {
                this.hopDosyaBorc = hopDosyaBorcResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInHopDosyaBorcs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'hopDosyaBorcListModification',
            (response) => this.load(this.hopDosyaBorc.id)
        );
    }
}
