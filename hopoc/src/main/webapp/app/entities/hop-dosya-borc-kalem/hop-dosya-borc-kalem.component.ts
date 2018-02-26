import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { HopDosyaBorcKalem } from './hop-dosya-borc-kalem.model';
import { HopDosyaBorcKalemService } from './hop-dosya-borc-kalem.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-hop-dosya-borc-kalem',
    templateUrl: './hop-dosya-borc-kalem.component.html'
})
export class HopDosyaBorcKalemComponent implements OnInit, OnDestroy {
hopDosyaBorcKalems: HopDosyaBorcKalem[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private hopDosyaBorcKalemService: HopDosyaBorcKalemService,
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
            this.hopDosyaBorcKalemService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<HopDosyaBorcKalem[]>) => this.hopDosyaBorcKalems = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.hopDosyaBorcKalemService.query().subscribe(
            (res: HttpResponse<HopDosyaBorcKalem[]>) => {
                this.hopDosyaBorcKalems = res.body;
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
        this.registerChangeInHopDosyaBorcKalems();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: HopDosyaBorcKalem) {
        return item.id;
    }
    registerChangeInHopDosyaBorcKalems() {
        this.eventSubscriber = this.eventManager.subscribe('hopDosyaBorcKalemListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
