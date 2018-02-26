import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaBorc } from './dosya-borc.model';
import { DosyaBorcService } from './dosya-borc.service';

@Component({
    selector: 'jhi-dosya-borc-detail',
    templateUrl: './dosya-borc-detail.component.html'
})
export class DosyaBorcDetailComponent implements OnInit, OnDestroy {

    dosyaBorc: DosyaBorc;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dosyaBorcService: DosyaBorcService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDosyaBorcs();
    }

    load(id) {
        this.dosyaBorcService.find(id)
            .subscribe((dosyaBorcResponse: HttpResponse<DosyaBorc>) => {
                this.dosyaBorc = dosyaBorcResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDosyaBorcs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dosyaBorcListModification',
            (response) => this.load(this.dosyaBorc.id)
        );
    }
}
