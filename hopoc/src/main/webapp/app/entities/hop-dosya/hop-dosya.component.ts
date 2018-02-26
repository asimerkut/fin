import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDosya } from './hop-dosya.model';
import { HopDosyaService } from './hop-dosya.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-hop-dosya',
    templateUrl: './hop-dosya.component.html'
})
export class HopDosyaComponent implements OnInit, OnDestroy {
hopDosyas: HopDosya[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private hopDosyaService: HopDosyaService,
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
            this.hopDosyaService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<HopDosya[]>) => this.hopDosyas = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.hopDosyaService.query().subscribe(
            (res: HttpResponse<HopDosya[]>) => {
                this.hopDosyas = res.body;
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
        this.registerChangeInHopDosyas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HopDosya) {
        return item.id;
    }
    registerChangeInHopDosyas() {
        this.eventSubscriber = this.eventManager.subscribe('hopDosyaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
