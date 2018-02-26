import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Dosya } from './dosya.model';
import { DosyaService } from './dosya.service';

@Component({
    selector: 'jhi-dosya-detail',
    templateUrl: './dosya-detail.component.html'
})
export class DosyaDetailComponent implements OnInit, OnDestroy {

    dosya: Dosya;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dosyaService: DosyaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDosyas();
    }

    load(id) {
        this.dosyaService.find(id)
            .subscribe((dosyaResponse: HttpResponse<Dosya>) => {
                this.dosya = dosyaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDosyas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dosyaListModification',
            (response) => this.load(this.dosya.id)
        );
    }
}
