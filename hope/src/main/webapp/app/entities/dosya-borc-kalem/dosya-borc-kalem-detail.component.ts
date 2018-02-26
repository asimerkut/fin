import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaBorcKalem } from './dosya-borc-kalem.model';
import { DosyaBorcKalemService } from './dosya-borc-kalem.service';

@Component({
    selector: 'jhi-dosya-borc-kalem-detail',
    templateUrl: './dosya-borc-kalem-detail.component.html'
})
export class DosyaBorcKalemDetailComponent implements OnInit, OnDestroy {

    dosyaBorcKalem: DosyaBorcKalem;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dosyaBorcKalemService: DosyaBorcKalemService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDosyaBorcKalems();
    }

    load(id) {
        this.dosyaBorcKalemService.find(id)
            .subscribe((dosyaBorcKalemResponse: HttpResponse<DosyaBorcKalem>) => {
                this.dosyaBorcKalem = dosyaBorcKalemResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDosyaBorcKalems() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dosyaBorcKalemListModification',
            (response) => this.load(this.dosyaBorcKalem.id)
        );
    }
}
