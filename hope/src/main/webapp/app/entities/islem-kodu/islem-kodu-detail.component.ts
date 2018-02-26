import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { IslemKodu } from './islem-kodu.model';
import { IslemKoduService } from './islem-kodu.service';

@Component({
    selector: 'jhi-islem-kodu-detail',
    templateUrl: './islem-kodu-detail.component.html'
})
export class IslemKoduDetailComponent implements OnInit, OnDestroy {

    islemKodu: IslemKodu;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private islemKoduService: IslemKoduService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIslemKodus();
    }

    load(id) {
        this.islemKoduService.find(id)
            .subscribe((islemKoduResponse: HttpResponse<IslemKodu>) => {
                this.islemKodu = islemKoduResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIslemKodus() {
        this.eventSubscriber = this.eventManager.subscribe(
            'islemKoduListModification',
            (response) => this.load(this.islemKodu.id)
        );
    }
}
