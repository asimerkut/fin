import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopMasraf } from './hop-masraf.model';
import { HopMasrafService } from './hop-masraf.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-hop-masraf',
    templateUrl: './hop-masraf.component.html'
})
export class HopMasrafComponent implements OnInit, OnDestroy {
hopMasrafs: HopMasraf[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private hopMasrafService: HopMasrafService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.hopMasrafService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<HopMasraf[]>) => this.hopMasrafs = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.hopMasrafService.query().subscribe(
            (res: HttpResponse<HopMasraf[]>) => {
                this.hopMasrafs = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInHopMasrafs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HopMasraf) {
        return item.id;
    }
    registerChangeInHopMasrafs() {
        this.eventSubscriber = this.eventManager.subscribe('hopMasrafListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
