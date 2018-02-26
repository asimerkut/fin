import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { DosyaTipi } from './dosya-tipi.model';
import { DosyaTipiService } from './dosya-tipi.service';

@Component({
    selector: 'jhi-dosya-tipi-detail',
    templateUrl: './dosya-tipi-detail.component.html'
})
export class DosyaTipiDetailComponent implements OnInit, OnDestroy {

    dosyaTipi: DosyaTipi;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dosyaTipiService: DosyaTipiService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDosyaTipis();
    }

    load(id) {
        this.dosyaTipiService.find(id)
            .subscribe((dosyaTipiResponse: HttpResponse<DosyaTipi>) => {
                this.dosyaTipi = dosyaTipiResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInDosyaTipis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'dosyaTipiListModification',
            (response) => this.load(this.dosyaTipi.id)
        );
    }
}
